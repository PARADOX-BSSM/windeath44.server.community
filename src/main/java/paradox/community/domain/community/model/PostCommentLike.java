package paradox.community.domain.community.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "post_comment_likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_comment_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class PostCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId; // 좋아요 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 좋아요를 누른 사용자

    @Column(name = "post_comment_id", nullable = false)
    private Long postCommentId; // 좋아요가 달린 게시글 댓글
}
