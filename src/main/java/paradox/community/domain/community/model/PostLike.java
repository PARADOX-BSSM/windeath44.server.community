package paradox.community.domain.community.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "post_likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId; // 좋아요 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 좋아요를 누른 사용자

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post; // 좋아요가 달린 게시글
}
