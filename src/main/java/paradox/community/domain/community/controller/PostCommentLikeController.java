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
            @PathVariable("comment-id") Long commentId,
            @RequestHeader("user-id") String userId
    ) {
        PostCommentLikeResponse postCommentLikeResponse =
                postCommentLikeService.addPostCommentLike(commentId, userId);

        if (postCommentLikeResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        ApiResponse<PostCommentLikeResponse> response = new ApiResponse<>(
                "like registered successfully",
                postCommentLikeResponse
        );
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deletePostCommentLike(
            @PathVariable("comment-id") Long commentId,
            @RequestHeader("user-id") String userId
    ) {
        if (postCommentLikeService.isLiked(commentId, userId)) {
            postCommentLikeService.removePostCommentLike(commentId, userId);

            return ResponseEntity.ok(HttpUtil.success("like deleted successfully"));
        }

        return ResponseEntity.badRequest().build();
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyPostCommentLiked(@PathVariable Long commentId, @RequestHeader String userId) {
        return ResponseEntity.ok(HttpUtil.success("success check post comment liked", postCommentLikeService.isLiked(commentId, userId)));
    }
}
