package paradox.community.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.response.PostLikeResponse;
import paradox.community.domain.community.service.PostLikeService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.PATH + "/communities/posts/{post-id}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 좋아요
    @PostMapping
    public ResponseEntity<ApiResponse<PostLikeResponse>> registerPostLike(
            @PathVariable("post-id") Long postId,
            @RequestHeader("user-id") String userId
    ) {
        PostLikeResponse postLikeResponse = postLikeService.addPostLike(postId, userId);

        if (postLikeResponse == null) return ResponseEntity.badRequest().build();

        ApiResponse<PostLikeResponse> response = new ApiResponse<>(
                "like registered successfully",
                postLikeResponse
        );
        return ResponseEntity.status(201).body(response);
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deletePostLike(
            @PathVariable("post-id") Long postId,
            @RequestHeader("user-id") String userId
    ) {
        if (postLikeService.isLiked(userId, postId)) {
            postLikeService.removePostLike(postId, userId);

            return ResponseEntity.ok(HttpUtil.success("success canceal post like"));
        }

        return ResponseEntity.badRequest().build();
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyPostLiked(@PathVariable Long postId, @RequestHeader String userId) {
        return ResponseEntity.ok(HttpUtil.success("succes check post liked", postLikeService.isLiked(userId, postId)));
    }

}