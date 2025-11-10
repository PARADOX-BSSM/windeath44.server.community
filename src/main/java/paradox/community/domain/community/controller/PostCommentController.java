package paradox.community.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import paradox.community.domain.community.dto.request.PostCommentRequest;
import paradox.community.domain.community.dto.response.PostCommentResponse;
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

    @PostMapping("/{post-id}/comments")
    public ResponseEntity<ApiResponse<PostCommentResponse>> createComment(
            @RequestHeader(name = "user-id") String userId,
            @PathVariable(name = "post-id") Long postId,
            @Valid @RequestBody PostCommentRequest request) {
        PostCommentResponse response = commentService.createComment(userId, postId, request);

        return ResponseEntity.status(201).body(
                HttpUtil.success(
                        "successfully created", response
                )
        );
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity<ApiResponse<PostCommentResponse>> updateComment(
            @RequestHeader(name = "user-id") String userId,
            @RequestHeader(name = "role") String role,
            @PathVariable(name = "comment-id") Long commentId,
            @Valid @RequestBody PostCommentRequest request
    ) {
        PostCommentResponse response = commentService.updateComment(userId, commentId, request, role);

        return ResponseEntity.ok(
                HttpUtil.success("successfully updated", response)
        );
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestHeader(name = "user-id") String userId,
            @RequestHeader(name = "role") String role,
            @PathVariable(name = "comment-id") Long commentId) {
        commentService.deleteComment(userId, commentId, role);

        return ResponseEntity.ok(
                HttpUtil.success("successfully deleted")
        );
    }

    @GetMapping("/{post-id}/comments")
    public ResponseEntity<ApiResponse<List<PostCommentResponse>>> getComments(
            @PathVariable(name = "post-id") Long postId) {
        List<PostCommentResponse> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(
                HttpUtil.success("successfully searched", comments)
        );
    }

    @GetMapping("/comments/{comment-id}")
    public ResponseEntity<ApiResponse<PostCommentResponse>> getCommentById(
            @PathVariable(name = "comment-id") Long commentId) {
        PostCommentResponse comment = commentService.getCommentById(commentId);

        return ResponseEntity.ok(
                HttpUtil.success("Successfully searched", comment)
        );
    }

    @GetMapping("/comments/{parent-comment-id}/replies")
    public ResponseEntity<ApiResponse<List<PostCommentResponse>>> getReplies(
            @PathVariable(name = "parent-comment-id") Long parentCommentId) {
        List<PostCommentResponse> replies = commentService.getRepliesByParentCommentId(parentCommentId);

        return ResponseEntity.ok(
                HttpUtil.success("Comment id: " + parentCommentId + " replies count: " + replies.size(), replies)
        );
    }
}