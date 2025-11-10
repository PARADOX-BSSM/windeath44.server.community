package paradox.community.domain.community.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@Table(name = "post_comments")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // 게시글 댓글 고유 식별자

    @Column(name = "user_id", nullable = false)
    private String userId; // 댓글 작성자(users.id) 참조

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "profile")
    private String profile;

    @Column(name = "post_id", nullable = false)
    private Long postId; // 소속 게시글(posts.id) 참조

    @Column(name = "parent_comment_id")
    private Long parentCommentId; // 대댓글의 부모 댓글 (post_comments.id) 참조

    @Column(nullable = false)
    private String body; // 댓글의 내용

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 게시글 댓글의 작성 시간

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 게시글 댓글의 수정 시간

    public void updateBody(String body) {
        if (body == null) throw new IllegalArgumentException("Comment body cannot be null");
        this.body = body;
    }
}
