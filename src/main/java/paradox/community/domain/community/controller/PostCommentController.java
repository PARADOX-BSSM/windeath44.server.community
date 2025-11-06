package paradox.community.domain.community.controller;

import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.request.PostCommentRequest;
import paradox.community.domain.community.dto.response.PostCommentResponse;
import paradox.community.domain.community.model.PostComment;
import paradox.community.domain.community.repository.PostRepository;
import paradox.community.domain.community.service.PostCommentService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.PATH + "/communities/posts")
public class PostCommentController {
    private final PostCommentService commentService;
    private final PostRepository postRepository;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<PostCommentResponse>> createComment(
            @RequestHeader("user-id") String userId,
            @RequestHeader("role")
            @PathVariable Long postId,
            @RequestBody PostCommentRequest request) {
        PostCommentResponse response = commentService.createComment(userId, postId, request);

        String messageType = request.parentCommentId() == null ? "comment" : "reply";
        return ResponseEntity.status(201).body(
                HttpUtil.success(
                        "User id: " + userId + " successfully created " + messageType + " on post id: " + postId,
                        response
                )
        );
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<PostCommentResponse>> updateComment(
            @RequestHeader("user-id") String userId,
            @RequestHeader("role") String role,
            @PathVariable Long postId,
            @PathVariable Long commnetId,
            @RequestBody PostCommentRequest request
    ) {
        PostCommentResponse response = commentService.updateComment(userId, postId, request, role);

        return ResponseEntity.ok(
                HttpUtil.success("User id: " + userId + " successfully updated comment id: " + commnetId, response)
        );
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@RequestHeader("user-id") String userId, @RequestHeader("role") String role, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(userId, commentId, role);

        return ResponseEntity.ok(HttpUtil.success("User id: " + userId + " successfully deleted comment id: " + commentId));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<PostCommentResponse>>> getComments(@RequestHeader("user-id") String userId, @PathVariable Long postId) {
        List<PostCommentResponse> comments = commentService.getCommentsByPostId(userId, postId);

        return ResponseEntity.ok(HttpUtil.success("Post id: " + postId + " comments count: " + comments.size(), comments));
    }

    @GetMapping("/{postId}/comments/{commentId}/replies")
    public ResponseEntity<ApiResponse<List<PostCommentResponse>>> getReplies(@RequestHeader("user-id") String userId, @PathVariable Long postId, @PathVariable Long parentCommentId) {
        List<PostCommentResponse> replies = commentService.getRepliesByParentCommentId(userId, parentCommentId);

        return ResponseEntity.ok(HttpUtil.success("Commet id: " +  parentCommentId + " replies count: " + replies.size(), replies));
    }

    @GetMapping("/{postId}/comments/count")
    public ResponseEntity<ApiResponse<Long>> getCommentsCount(@PathVariable Long postId) {
        Long count = commentService.getCommentsCount(postId);
        return ResponseEntity.ok(HttpUtil.success(  "post id: " + postId + "total comment count: " + count, count));
    }
}
