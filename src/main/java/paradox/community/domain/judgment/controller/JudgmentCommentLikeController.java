package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.response.JudgmentCommentLikeResponse;
import paradox.community.domain.judgment.service.JudgmentCommentLikeService;
import paradox.community.global.Path;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.PATH + "/communities/judgments/comments/{comment-id}/likes")
public class JudgmentCommentLikeController {

    private final JudgmentCommentLikeService judgmentCommentLikeService;

    // 좋아요
    @PostMapping
    public ResponseEntity<ApiResponse<JudgmentCommentLikeResponse>> registerJudgmentCommentLike(@PathVariable("comment-id") Long judgmentCommentId, @RequestHeader("user-id") String userId) {
        JudgmentCommentLikeResponse judgmentCommentLikeResponse = judgmentCommentLikeService.addJudgmentCommentLike(userId, judgmentCommentId);
        return ResponseEntity.status(201).body(HttpUtil.success("Success register judgment comment",  judgmentCommentLikeResponse));
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> cancelJudgmentCommentLike(@PathVariable("comment-id") Long judgmentCommentId, @RequestHeader("user-id") String userId) {
        judgmentCommentLikeService.removeJudgmentCommentLike(userId, judgmentCommentId);
        return ResponseEntity.ok(HttpUtil.success("Success cancel judgment comment",  null));
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyJudgmentCommentLiked(@PathVariable("comment-id") Long  judgmentCommentId, @RequestHeader("user-id") String userId) {
        return ResponseEntity.ok(HttpUtil.success("success check judgment comment liked", judgmentCommentLikeService.isLiked(userId, judgmentCommentId)));
    }
}
