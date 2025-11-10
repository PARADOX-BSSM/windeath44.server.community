package paradox.community.domain.judgment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import paradox.community.global.constclass.ColumnDefaults;

import java.time.LocalDateTime;

@Table(name = "judgments")
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = false)
public class Judgment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long judgmentId; // 재판 고유 식별자

    @Column(name = "character_id", nullable = false)
    private Long characterId; // 재판 대상 캐릭터(characters.id) 참조

    @Column(name = "character_name", nullable = false)
    private String characterName;

    @Column(name = "anime_id", nullable = false)
    private Long animeId;

    @Column(name = "anime_name", nullable = false)
    private String animeName;

    @Column(nullable = false)
    private String title; // 재판 이벤트의 제목

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JudgmentInstance instance; // 재판 (1심, 2심, 3심)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JudgmentStatus status; // 재판의 상태 (

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt; // 재판 시작 시간

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt; // 재판 종료 시간

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 재판 생성 시간

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 재판 수정 시간

    @Builder.Default
    @Column(name = "heaven_count", nullable = false, columnDefinition = ColumnDefaults.ZERO_DEFAULT)
    private Long heavenCount = 0L; // 천국 투표 수

    @Builder.Default
    @Column(name = "hell_count", nullable = false)
    private Long hellCount = 0L; // 지옥 투표 수

    @Builder.Default
    @Column(name = "likes_count", nullable = false)
    private Long likesCount = 0L; // 좋아요 수

    public void update(String title, LocalDateTime startAt, LocalDateTime endAt) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Title cannot be null or empty");
        if (startAt == null || endAt == null) throw new IllegalArgumentException("startAt and endAt cannot be null");
        if (endAt.isBefore(startAt)) throw new IllegalArgumentException("endAt must be after startAt");
        this.title = title.trim();
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void end() {
        this.status = JudgmentStatus.Ended;
    }

    public void decrementLikesCount() {
        if (this.likesCount == null) this.likesCount = 0L;
        if (this.likesCount > 0) this.likesCount--;
    }

    public void incrementLikesCount() {
        if (this.likesCount == null) this.likesCount = 0L;
        this.likesCount++;
    }

    @PrePersist
    private void prePersistDefaults() {
        if (this.heavenCount == null) this.heavenCount = 0L;
        if (this.hellCount == null) this.hellCount = 0L;
        if (this.likesCount == null) this.likesCount = 0L;
        if (this.status == null) this.status = JudgmentStatus.InProgress;
    }
}
