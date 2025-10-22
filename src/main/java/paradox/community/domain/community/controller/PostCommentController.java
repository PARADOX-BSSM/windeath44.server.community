package paradox.community.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.community.dto.request.PostCommentRequest;
import paradox.community.domain.community.dto.response.PostCommentResponse;
import paradox.community.domain.community.repository.PostRepository;
import paradox.community.domain.community.service.PostCommentService;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities/posts")
public class PostCommentController {
    private final PostCommentService commentService;
    private final PostRepository postRepository;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<PostCommentResponse>> createComment(
            @RequestHeader("user-id") String userId,
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
            @RequestHeader("user-id")
    )
}
