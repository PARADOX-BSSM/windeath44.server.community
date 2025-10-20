package paradox.community.domain.judgment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import paradox.community.global.constclass.ColumnDefaults;

import java.time.LocalDateTime;

@Table(name = "judgments")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Judgment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long judgmentId; // 재판 고유 식별자

    @Column(name = "character_id", nullable = false)
    private Long characterId; // 재판 대상 캐릭터(characters.id) 참조

    @Column(nullable = false)
    private String title; // 재판 이벤트의 제목

    @Column(name = "is_end", nullable = false)
    private Boolean isEnd; // 재판이 끝났는지의 여부 (true or false)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JudgmentStatus instance; // 재판 (1심, 2심, 3심)

    @Column(name = "start_at", nullable = false, updatable = false)
    private LocalDateTime startAt; // 재판 시간 시간

    @Column(name = "end_at", nullable = false, updatable = false)
    private LocalDateTime endAt; // 재판 종료 시간

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 재판 생성 시간

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
