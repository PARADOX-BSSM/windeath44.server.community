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

    @PostMapping("/{judgmentId}/comments")
    public ResponseEntity<ApiResponse<JudgmentCommentResponse>> createComment(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId,
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

    @PatchMapping("/{judgmentId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<JudgmentCommentResponse>> updateComment(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId,
            @PathVariable Long commentId,
            @RequestBody JudgmentCommentRequest request) {
        JudgmentCommentResponse response = commentService.updateComment(userId, commentId, request);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "User id: " + userId + " successfully updated comment id: " + commentId,
                        response
                )
        );
    }

    @DeleteMapping("/{judgmentId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId,
            @PathVariable Long commentId) {
        commentService.deleteComment(userId, commentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "User id: " + userId + " successfully deleted comment id: " + commentId
                )
        );
    }

    @GetMapping("/{judgmentId}/comments")
    public ResponseEntity<ApiResponse<List<JudgmentCommentResponse>>> getComments(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId) {
        List<JudgmentCommentResponse> comments = commentService.getCommentsByJudgmentId(userId, judgmentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Judgment id: " + judgmentId +" comments count: " + comments.size(),
                        comments
                )
        );
    }

    @GetMapping("/{judgmentId}/comments/{parentCommentId}/replies")
    public ResponseEntity<ApiResponse<List<JudgmentCommentResponse>>> getReplies(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId,
            @PathVariable Long parentCommentId) {
        List<JudgmentCommentResponse> replies = commentService.getRepliesByParentCommentId(userId, parentCommentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Comment id: " + parentCommentId + " replies count: " + replies.size(),
                        replies
                )
        );
    }

    @GetMapping("/{judgmentId}/comments/count")
    public ResponseEntity<ApiResponse<Long>> getCommentsCount(
            @PathVariable Long judgmentId) {
        Long count = commentService.getCommentsCount(judgmentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Judgment id: " + judgmentId + " total comments count: " + count,
                        count
                )
        );
    }
}
