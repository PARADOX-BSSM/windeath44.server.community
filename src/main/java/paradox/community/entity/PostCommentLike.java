package paradox.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "post_comment_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_comment_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좋아요 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 좋아요를 누른 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id", nullable = false)
    private PostComment postComment; // 좋아요가 달린 게시글 댓글
}
