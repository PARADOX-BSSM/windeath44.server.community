package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.response.VoteCreateResponse;
import org.springframework.stereotype.Service;
import paradox.community.domain.judgment.dto.response.VoteHistoryResponse;
import paradox.community.domain.judgment.dto.response.VoteResponse;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentStatus;
import paradox.community.domain.judgment.model.Vote;
import paradox.community.domain.judgment.repository.JudgmentRepository;
import paradox.community.domain.judgment.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final JudgmentRepository judgmentRepository;

    public VoteCreateResponse recordVote(String userId, Long judgmentId, Boolean isHeaven) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + judgmentId));

        if (judgment.getStatus() == JudgmentStatus.Ended) {
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
            } else {
                vote.setIsHeaven(isHeaven);
                vote = voteRepository.save(vote);
                log.info("Vote changed - userId: {}, judgmentId: {}, previous: {}, current: {}",
                        userId, judgmentId, previousDirection, isHeaven);
            }
        } else {
            vote = Vote.builder()
                    .userId(userId)
                    .judgmentId(judgmentId)
                    .isHeaven(isHeaven)
                    .build();
            vote = voteRepository.save(vote);

            log.info("New vote recorded - userId: {}, judgmentId: {}, direction: {}",
                    userId, judgmentId, isHeaven);
        }

        Long heavenCount = voteRepository.countHeavenVotes(judgmentId);
        Long hellCount = voteRepository.countHellVotes(judgmentId);

        return new VoteCreateResponse (
                vote.getVoteId(),
                vote.getUserId(),
                vote.getIsHeaven(),
                vote.getVotedAt(),
                heavenCount,
                hellCount
        );
    }
    @Transactional(readOnly = true)
    public List<VoteHistoryResponse> getMyVoteHistory(String userId) {
        return voteRepository.findByUserId(userId)
                .stream()
                .map(vote -> new VoteHistoryResponse(
                        vote.getVoteId(),
                        vote.getJudgmentId(),
                        vote.getIsHeaven(),
                        vote.getVotedAt()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VoteResponse getVotingStats(Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + judgmentId));

        Long heavenCount = voteRepository.countHeavenVotes(judgmentId);
        Long hellCount = voteRepository.countHellVotes(judgmentId);
        Long totalVotes = heavenCount + hellCount;

        double heavenRatio = totalVotes > 0 ? (heavenCount * 100.0) / totalVotes : 0;
        double hellRatio = totalVotes > 0 ? (hellCount * 100.0) / totalVotes : 0;

        return new VoteResponse(
                judgmentId,
                heavenCount,
                hellCount,
                totalVotes,
                heavenRatio,
                hellRatio
        );
    }

    public void deleteVote(String userId, Long judgmentId) {
        if (!voteRepository.existsByUserIdAndJudgmentId(userId, judgmentId)) {
            throw new IllegalArgumentException("투표 내역을 찾을 수 없습니다.");
        }
        voteRepository.deleteByUserIdAndJudgmentId(userId, judgmentId);
        log.info("Vote deleted - userId: {}, judgmentId: {}", userId, judgmentId);
    }

    public Optional<Boolean> getUserVoteDirection(String userId, Long judgmentId) {
        return voteRepository.findByUserIdAndJudgmentId(userId, judgmentId)
                .map(Vote::getIsHeaven);
    }
}