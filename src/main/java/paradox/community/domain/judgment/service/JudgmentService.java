package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.request.JudgmentCreateRequest;
import paradox.community.domain.judgment.dto.response.JudgmentCreateResponse;
import paradox.community.domain.judgment.dto.response.JudgmentResponse;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.repository.JudgmentRepository;
import paradox.community.domain.judgment.repository.VoteRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JudgmentService {

    private final JudgmentRepository judgmentRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public JudgmentResponse createJudgment(JudgmentCreateRequest request) {
        Judgment judgment = Judgment.builder()
                .characterId(request.characterId())
                .title(request.title())
                .status(request.instance())
                .isEnd(false)
                .startAt(request.startAt())
                .endAt(request.endAt())
                .build();

        Judgment savedJudgment = judgmentRepository.save(judgment);
        return JudgmentResponse.from(savedJudgment);
    }
}
