package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.response.JudgmentCommentLikeResponse;
import paradox.community.domain.judgment.service.JudgmentCommentLikeService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.PATH + "/communities/judgments/comments/{comment-id}/likes")
public class JudgmentCommentLikeController {

    private final JudgmentCommentLikeService judgmentCommentLikeService;

    // 좋아요
    @PostMapping
    public ResponseEntity<ApiResponse<JudgmentCommentLikeResponse>> registerJudgmentCommentLike(@PathVariable("comment-id") Long judgmentCommentId, @RequestHeader("user-id") String userId) {
        return judgmentCommentLikeService.addJudgmentCommentLike(userId, judgmentCommentId)
                .map(body -> ResponseEntity.status(201).body(new ApiResponse<>("like registered successfully", body)))
                .orElseGet(() -> ResponseEntity.badRequest().body(new ApiResponse<JudgmentCommentLikeResponse>("already liked or invalid request", null)));
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> cancelJudgmentCommentLike(@PathVariable("comment-id") Long judgmentCommentId, @RequestHeader("user-id") String userId) {
        judgmentCommentLikeService.removeJudgmentCommentLike(userId, judgmentCommentId);
        return ResponseEntity.ok(new ApiResponse<>("Success cancel judgment comment", null));
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyJudgmentCommentLiked(@PathVariable("comment-id") Long  judgmentCommentId, @RequestHeader("user-id") String userId) {
        boolean liked = Boolean.TRUE.equals(judgmentCommentLikeService.isLiked(userId, judgmentCommentId));
        return ResponseEntity.ok(new ApiResponse<>("success check judgment comment liked", liked));
    }
}
