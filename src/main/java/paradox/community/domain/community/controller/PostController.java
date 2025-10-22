package paradox.community.domain.community.controller;

import com.google.api.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.request.PostSearchRequest;
import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.service.PostService;
import paradox.community.domain.judgment.model.Judgment;

@RestController
@RequestMapping("/communities/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 목록 조회
    @PostMapping
    public ResponseEntity<Page<PostResponse>> getPosts(@RequestBody PostSearchRequest request) {
        PostResponse post = postService.getPosts()
    }
}
