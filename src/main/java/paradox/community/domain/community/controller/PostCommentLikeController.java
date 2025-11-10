package paradox.community.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.response.PostCommentLikeResponse;
import paradox.community.domain.community.service.PostCommentLikeService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.PATH + "/communities/posts/comments/{comment-id}/likes")
public class PostCommentLikeController {
    private final PostCommentLikeService postCommentLikeService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostCommentLikeResponse>> registerPostCommentLike(
            @PathVariable(name = "comment-id") Long commentId,
            @RequestHeader(name = "user-id") String userId
    ) {
    return postCommentLikeService.addPostCommentLike(commentId, userId)
        .map(body -> ResponseEntity.status(201).body(new ApiResponse<>("like registered successfully", body)))
        .orElseGet(() -> ResponseEntity.badRequest().body(new ApiResponse<PostCommentLikeResponse>("already liked or invalid request", null)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deletePostCommentLike(
            @PathVariable(name = "comment-id") Long commentId,
            @RequestHeader(name = "user-id") String userId
    ) {
        if (Boolean.TRUE.equals(postCommentLikeService.isLiked(commentId, userId))) {
            postCommentLikeService.removePostCommentLike(commentId, userId);

            return ResponseEntity.ok(HttpUtil.success("like deleted successfully"));
        }

        return ResponseEntity.badRequest().build();
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyPostCommentLiked(@PathVariable(name = "comment-id") Long commentId, @RequestHeader(name = "user-id") String userId) {
        boolean liked = Boolean.TRUE.equals(postCommentLikeService.isLiked(commentId, userId));
        return ResponseEntity.ok(HttpUtil.success("success check post comment liked", liked));
    }
}
