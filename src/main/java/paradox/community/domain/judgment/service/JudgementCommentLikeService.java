package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.model.JudgmentComment;
import paradox.community.domain.judgment.model.JudgmentCommentLike;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentCommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JudgementCommentLikeService {

    private final JudgmentCommentLikeRepository judgmentCommentLikeRepository;
    private final JudgmentCommentRepository judgmentCommentRepository;

    @Transactional
    public boolean toggleLike(String userId, Long commentId) {
        JudgmentComment judgmentComment = judgmentCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Judgment not found"));

        return judgmentCommentLikeRepository.findByUserIdAndCommentId(userId, commentId)
                .map(like -> {
                    judgmentCommentLikeRepository.delete(like);
                    judgmentComment.decrementLikesCount();
                    return false;
                })
                .orElseGet(() -> {
                    JudgmentCommentLike newlike = JudgmentCommentLike.builder()
                            .userId(userId)
                            .commentId(commentId)
                            .build();
                    judgmentCommentLikeRepository.save(newlike);
                    judgmentComment.incrementLikesCount();
                    return true;
                });
    }
}
