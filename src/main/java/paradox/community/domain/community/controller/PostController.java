package paradox.community.domain.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.request.PostCreateRequest;
import paradox.community.domain.community.dto.request.PostSearchRequest;
import paradox.community.domain.community.dto.request.PostUpdateRequest;
import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.service.PostService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequestMapping(Path.PATH + "/communities/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 목록 조회
    @PostMapping("/list")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getPosts(
            @RequestHeader(name = "user-id", required = false) String userId,
            @RequestBody PostSearchRequest request,
            Pageable pageable) {
        int page = (request.page() != null && request.page() >= 0) ? request.page() : pageable.getPageNumber();
        int size = (request.size() != null && request.size() > 0) ? request.size() : pageable.getPageSize();
        Pageable pageRequest = PageRequest.of(page, size, pageable.getSort());

        Page<PostResponse> posts = postService.getPosts(request, pageRequest, userId);
        return ResponseEntity.ok(HttpUtil.success("success post search", posts));
    }

    // 게시글 상세 조회
    @GetMapping("/{post-id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable(name = "post-id") Long postId) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(HttpUtil.success("success post search", post));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestHeader(name = "user-id") String userId, @Valid @RequestBody PostCreateRequest request) {
        PostResponse post = postService.createPost(userId, request);
        return ResponseEntity.status(201).body(HttpUtil.success("success post create", post));
    }

    // 게시글 수정
    @PatchMapping("/{post-id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable(name = "post-id") Long postId, @RequestHeader(name = "user-id") String userId, @RequestHeader(name = "role") String role, @Valid @RequestBody PostUpdateRequest request) {
        PostResponse post = postService.updatePost(postId, userId, request, role);
        return ResponseEntity.ok(HttpUtil.success("success post update", post));
    }

    // 게시글 삭제
    @DeleteMapping("/{post-id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable(name = "post-id") Long postId, @RequestHeader(name = "user-id") String userId, @RequestHeader(name = "role") String role) {
        postService.deletePost(postId, userId, role);
        return ResponseEntity.ok(HttpUtil.success("success post delete"));
    }
}
