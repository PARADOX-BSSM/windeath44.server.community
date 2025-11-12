package paradox.community.domain.community.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import paradox.community.global.constclass.ColumnDefaults;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId; // 게시글 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 게시글 작성자(users.id) 참조

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "profile")
    private String profile;

    @Column(nullable = false)
    private String title; // 게시글 제목

    private String body; // 게시글의 내용 (이미지 URL 등 포함)

    @Column(name = "is_blind", nullable = false)
    private Boolean isBlind; // 스포일러 방지로 흐림 처리 (true or false)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status; // 게시글 상태 (DRAFT, PUBLISHED)

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 게시글 작성 시간

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 게시글 수정 시간

    @Builder.Default
    @Column(name = "views_count", nullable = false, columnDefinition =  ColumnDefaults.ZERO_DEFAULT)
    private Long viewsCount = (Long) 0L; // 조회수

    @Builder.Default
    @Column(name = "likes_count", nullable = false, columnDefinition = ColumnDefaults.ZERO_DEFAULT)
    private Long likesCount = (Long) 0L; // 좋아요

    @Builder.Default
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted = false; // 논리 삭제

    public void publish() {
        if (this.status == PostStatus.PUBLISHED) {
            throw new IllegalStateException("Post is already published");
        }
        this.status = PostStatus.PUBLISHED;
    }

    public void draft() {
        if (this.status == PostStatus.DRAFT) {
            throw new IllegalStateException("Post is already drafted");
        }
        this.status = PostStatus.DRAFT;
    }

    public void update(String title, String body) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be null or blank");
        this.title = title;
        this.body = body;
    }

    public void delete() {
        if (this.isDeleted) {
            throw new IllegalStateException("Post is already deleted");
        }
        this.isDeleted = true;
    }

    // Domain helper methods
    public void incrementViews() {
        this.viewsCount = (this.viewsCount == null ? 0L : this.viewsCount) + 1L;
    }

    public void increaseLikes() {
        this.likesCount = (this.likesCount == null ? 0L : this.likesCount) + 1L;
    }

    public void decreaseLikes() {
        if (this.likesCount == null || this.likesCount <= 0L) {
            return;
        }
        this.likesCount = this.likesCount - 1L;
    }
}
