package paradox.community.domain.community.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.request.PostCreateRequest;
import paradox.community.domain.community.dto.request.PostSearchRequest;
import paradox.community.domain.community.dto.request.PostUpdateRequest;
import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.service.PostService;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequestMapping("/communities/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 목록 조회
    @PostMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getPosts(@RequestBody PostSearchRequest request, Pageable pageable) {
        Page<PostResponse> posts = postService.getPosts(request, pageable);
        return ResponseEntity.ok(HttpUtil.success("success post search", posts));
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId, Pageable pageable) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(HttpUtil.success("success post search", post));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestHeader("user-id") String userId, @Valid @RequestBody PostCreateRequest request) {
        PostResponse post = postService.createPost(userId, request);
        return ResponseEntity.ok(HttpUtil.success("success post create", post));
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long postId, @RequestHeader("user-id") String userId, @Valid @RequestBody PostUpdateRequest request) {
        PostResponse post = postService.updatePost(postId, userId, request);
        return ResponseEntity.ok(HttpUtil.success("success post update", post));
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId, @RequestHeader("user-id") String userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.ok(HttpUtil.success("success post delete"));
    }
}
