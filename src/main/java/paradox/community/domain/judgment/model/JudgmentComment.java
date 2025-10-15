package paradox.community.domain.judgment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import paradox.community.global.constclass.ColumnDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "judgment_comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JudgmentComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 댓글 작성자(users.id) 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judgment_id", nullable = false)
    private Judgment judgment; // 소속 재판

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private JudgmentComment parentComment; // 부모 댓글 (대댓글인 경우)

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JudgmentComment> childComments; // 대댓글 리스트

    @Column(nullable = false)
    private String body; // 댓글 내용

    @Column(name = "likes_count", nullable = false, columnDefinition = ColumnDefaults.ZERO_DEFAULT)
    private Integer likesCount = 0; // 댓글 좋아요 수

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 댓글 작성 시간

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 댓글 수정 시간
}
