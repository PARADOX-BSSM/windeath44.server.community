package paradox.community.domain.judgment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "judgment_comment_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "judgment_comment_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JudgmentCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId; // 좋아요 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 좋아요를 누른 사용자

    @Column(name = "comment_id", nullable = false)
    private Long commentId; // 댓글 아이디

    @Column(name = "judgment_comment_id", nullable = false)
    private Long judgmentCommentId; // 좋아요가 달린 댓글
}