package paradox.community.entity;

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
    private Long id; // 좋아요 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 좋아요를 누른 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judgment_comment_id", nullable = false)
    private JudgmentComment judgmentComment; // 좋아요가 달린 댓글
}