package paradox.community.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.response.PostLikeResponse;
import paradox.community.domain.community.service.PostLikeService;
import paradox.community.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities/posts/{post-id}/likes")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostLikeResponse>> registerPostLike(
            @PathVariable("post-id") Long postId,
            @RequestHeader("user-id") String userId
    ) {
        PostLikeResponse postLikeResponse =
                postLikeService.addPostLike(postId, userId);

        if (postLikeResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        ApiResponse<PostLikeResponse> response = new ApiResponse<>(
                "like registered successfully",
                postLikeResponse
        );
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<PostLikeResponse>> deletePostLike(
            @PathVariable("post-id") Long postId,
            @RequestHeader("user-id") String userId
    ) {
        PostLikeResponse postLikeResponse =
                postLikeService.removePostLike(postId, userId);

        if (postLikeResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        ApiResponse<PostLikeResponse> response = new ApiResponse<>(
                "like deleted successfully",
                postLikeResponse
        );
        return ResponseEntity.ok(response);
    }
}
