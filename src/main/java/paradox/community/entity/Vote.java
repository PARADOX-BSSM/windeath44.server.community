package paradox.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "votes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "judgment_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 투표 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 투표한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judgment_id", nullable = false)
    private Judgment judgment; // 소속 재판

    @Column(name = "is_heaven", nullable = false)
    private Boolean isHeaven; // 투표 방향 (HEAVEN or HELL)

    @CreationTimestamp
    @Column(name = "voted_at", nullable = false, updatable = false)
    private LocalDateTime votedAt; // 투표 기록 시간
}
