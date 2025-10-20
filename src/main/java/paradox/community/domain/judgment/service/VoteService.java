package paradox.community.domain.judgment.service;

import lombok.extern.slf4j.Slf4j;
import paradox.community.domain.judgment.dto.response.VoteCreateResponse;
import org.springframework.stereotype.Service;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.Vote;
import paradox.community.domain.judgment.repository.JudgmentRepository;
import paradox.community.domain.judgment.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class VoteService {
    private VoteRepository voteRepository;
    private JudgmentRepository judgmentRepository;

    public VoteCreateResponse recordVote(String userId, Long judgmentId, Boolean isHeaven) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + judgmentId));

        if (judgment.getIsEnd()) {
            throw new IllegalArgumentException("종료된 재판에는 투표할 수 없습니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(judgment.getStartAt())) {
            throw new IllegalArgumentException("아직 투표 시작 전 입니다.");
        }
        if (now.isAfter(judgment.getEndAt())) {
            throw new IllegalArgumentException("투표 기간이 종료되었습니다.");
        }
        Optional<Vote> existingVote = voteRepository.findByUserIdAndJudgmentId(userId, judgmentId);

        Vote vote;
        if (existingVote.isPresent()) {
            vote = existingVote.get();
            Boolean previousDirection = vote.getIsHeaven();

            if (previousDirection.equals(isHeaven)) {
                log.info("Same vote direction - userId: {}, judgmentId: {}, direction: {}",
                        userId, judgmentId, isHeaven);
            }
        }
    }
    public VoteCreateResponse getVote(String userId, Long judgmentId) {
        Boolean isHeaven = VoteRepository.findByUserIdAndJudgmentId(userId, judgmentId).getIsheaven();
        return new VoteCreateResponse(judgmentId, userId, isHeaven);
    }
}
