package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.request.JudgmentCommentRequest;
import paradox.community.domain.judgment.dto.response.JudgmentCommentResponse;
import paradox.community.domain.judgment.service.JudgmentCommentService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.PATH + "/communities/judgments")
@Slf4j
public class JudgmentCommentController {
    private final JudgmentCommentService commentService;

    @PostMapping("/{judgment-id}/comments")
    public ResponseEntity<ApiResponse<JudgmentCommentResponse>> createComment(
            @RequestHeader("user-id") String userId,
            @PathVariable("judgment-id") Long judgmentId,
            @RequestBody JudgmentCommentRequest request) {
        JudgmentCommentResponse response = commentService.createComment(userId, judgmentId, request);

        String messageType = request.parentCommentId() == null ? "comment" : "reply";
        return ResponseEntity.status(201).body(
                HttpUtil.success(
                        "User id: " + userId + " successfully created " + messageType + " on judgment id: " + judgmentId,
                        response
                )
        );
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity<ApiResponse<JudgmentCommentResponse>> updateComment(
            @RequestHeader("user-id") String userId,
            @PathVariable("comment-id") Long commentId,
            @RequestBody JudgmentCommentRequest request) {
        JudgmentCommentResponse response = commentService.updateComment(userId, commentId, request);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "successfully updated comment id: " + commentId,
                        response
                )
        );
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestHeader("user-id") String userId,
            @PathVariable("comment-id") Long commentId) {
        commentService.deleteComment(userId, commentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "successfully deleted comment id: " + commentId
                )
        );
    }

    @GetMapping("/{judgment-id}/comments")
    public ResponseEntity<ApiResponse<List<JudgmentCommentResponse>>> getComments(
            @RequestHeader("user-id") String userId,
            @PathVariable("judgment-id") Long judgmentId) {
        List<JudgmentCommentResponse> comments = commentService.getCommentsByJudgmentId(userId, judgmentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Judgment id: " + judgmentId +" comments count: " + comments.size(),
                        comments
                )
        );
    }

    @GetMapping("/comments/{parent-comment-id}/replies")
    public ResponseEntity<ApiResponse<List<JudgmentCommentResponse>>> getReplies(
            @RequestHeader("user-id") String userId,
            @PathVariable("parent-comment-id") Long parentCommentId) {
        List<JudgmentCommentResponse> replies = commentService.getRepliesByParentCommentId(userId, parentCommentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Comment id: " + parentCommentId + " replies count: " + replies.size(),
                        replies
                )
        );
    }

    @GetMapping("/{judgment-id}/comments/count")
    public ResponseEntity<ApiResponse<Long>> getCommentsCount(
            @PathVariable("judgment-id") Long judgmentId) {
        Long count = commentService.getCommentsCount(judgmentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Judgment id: " + judgmentId + " total comments count: " + count,
                        count
                )
        );
    }
}
