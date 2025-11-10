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
            @PathVariable(name = "post-id") Long postId,
            @RequestHeader(name = "user-id") String userId
    ) {
    return postLikeService.addPostLike(postId, userId)
        .map(body -> ResponseEntity.status(201).body(new ApiResponse<>("like registered successfully", body)))
        .orElseGet(() -> ResponseEntity.badRequest().body(new ApiResponse<PostLikeResponse>("already liked or invalid request", null)));
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deletePostLike(
            @PathVariable(name = "post-id") Long postId,
            @RequestHeader(name = "user-id") String userId
    ) {
        if (!Boolean.TRUE.equals(postLikeService.isLiked(userId, postId))) {
            ApiResponse<Void> err = new ApiResponse<>("not liked yet", null);
            return ResponseEntity.badRequest().body(err);
        }

        postLikeService.removePostLike(postId, userId);
        return ResponseEntity.ok(HttpUtil.success("success cancel post like"));
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyPostLiked(@PathVariable(name = "post-id") Long postId, @RequestHeader(name = "user-id") String userId) {
        return ResponseEntity.ok(HttpUtil.success("success check post liked", postLikeService.isLiked(userId, postId)));
    }

}