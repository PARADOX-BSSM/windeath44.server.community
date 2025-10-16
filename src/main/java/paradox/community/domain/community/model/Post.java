package paradox.community.domain.community.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import paradox.community.global.constclass.ColumnDefaults;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // 게시글 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 게시글 작성자(users.id) 참조

    @Column(name = "character_id")
    private Long characterId; // 추모 대상 캐릭(characters.id) 참조

    @Column(nullable = false)
    private String title; // 게시글 제목

    private String body; // 게시글의 내용 (이미지 URL 등 포함)

    @Column(name = "is_blind", nullable = false)
    private boolean blind; // 스포일러 방지로 흐림 처리 (true or false)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status; // 게시글 상태 (DRAFT, PUBLISHED)

    @Column(name = "likes_count", nullable = false, columnDefinition = ColumnDefaults.ZERO_DEFAULT)
    private Integer likesCount = 0; // 게시글의 좋아요 수

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 게시글 작성 시간

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 게시글 수정 시간

    @Column(name = "view_count", nullable = false, columnDefinition =  ColumnDefaults.ZERO_DEFAULT)
    private Integer viewsCount = 0; // 조회수
}
