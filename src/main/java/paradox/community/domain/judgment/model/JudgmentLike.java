package paradox.community.domain.judgment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "judgment_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "judgment_id"})
)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"judgment"}, callSuper = false)
public class JudgmentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long likeId; // 좋아요 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 좋아요를 누른 사용자

    @Column(name = "judgment_id", nullable = false)
    private Long judgmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judgment_id", insertable = false, updatable = false)
    private Judgment judgment; // 좋아요가 달린 재판
}
