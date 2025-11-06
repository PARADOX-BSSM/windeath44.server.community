package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.request.JudgmentCreateRequest;
import paradox.community.domain.judgment.dto.request.JudgmentUpdateRequest;
import paradox.community.domain.judgment.dto.response.JudgmentResponse;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentInstance;
import paradox.community.domain.judgment.model.JudgmentStatus;
import paradox.community.domain.judgment.repository.JudgmentRepository;
import paradox.community.domain.judgment.repository.VoteRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JudgmentService {

    private final JudgmentRepository judgmentRepository;

    @Transactional
    public JudgmentResponse createJudgment(JudgmentCreateRequest request, String role) {
        if (!role.equals("ADMIN")) return null;
        Judgment judgment = Judgment.builder()
                .characterId(request.characterId())
                .title(request.title())
                .instance(request.instance())
                .status(JudgmentStatus.InProgress)
                .startAt(request.startAt())
                .endAt(request.endAt())
                .build();

        Judgment savedJudgment = judgmentRepository.save(judgment);
        return JudgmentResponse.from(savedJudgment);
    }

    @Transactional
    public JudgmentResponse updateJudgment(Long judgmentId, JudgmentUpdateRequest request, String role) {
        if (!role.equals("ADMIN")) return null;

        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid judgmentId: " + judgmentId));

        judgment.update(request.title(), request.startAt(), request.endAt());
        return JudgmentResponse.from(judgment);
    }

    @Transactional
    public JudgmentResponse endJudgment(Long judgmentId, String role) {
        if (!role.equals("ADMIN")) return null;

        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid judgmentId: " + judgmentId));

        judgment.end();
        return JudgmentResponse.from(judgment);
    }

    public JudgmentResponse getJudgment(Long judgmentId) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid judgmentId: " + judgmentId));

        return JudgmentResponse.from(judgment);
    }

    public Page<JudgmentResponse> getJudgments(Long characterId, JudgmentStatus status, JudgmentInstance instance, Pageable pageable) {
        Page<Judgment> judgments;

        if (characterId != null && status != null && instance != null) {
            judgments = judgmentRepository.findByCharacterIdAndStatusAndInstance(characterId, status, instance, pageable);
        }else if (characterId != null && status != null) {
            judgments = judgmentRepository.findByCharacterIdAndStatus(characterId, status, pageable);
        }else  if (characterId != null && instance != null) {
            judgments = judgmentRepository.findByCharacterIdAndInstance(characterId, instance, pageable);
        }else if (status != null && instance != null) {
            judgments = judgmentRepository.findByStatusAndInstance(status, instance, pageable);
        }else if (characterId != null) {
            judgments = judgmentRepository.findByCharacterId(characterId, pageable);
        }else if (status != null) {
            judgments = judgmentRepository.findByStatus(status, pageable);
        }else if (instance != null) {
            judgments = judgmentRepository.findByInstance(instance, pageable);
        }else {
            judgments = judgmentRepository.findAll(pageable);
        }

        return judgments.map(JudgmentResponse::from);
    }
}
