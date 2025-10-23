package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.response.JudgmentLikeResponse;
import paradox.community.domain.judgment.service.JudgmentLikeService;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequestMapping("/communities/judgments/{judgment-id}/likes")
@RequiredArgsConstructor
public class JudgmentLikeController {

    private final JudgmentLikeService judgmentLikeService;

    // 좋아요
    @PostMapping
    public ResponseEntity<ApiResponse<JudgmentLikeResponse>> registerJudgmentLike(@PathVariable Long judgmentId, @RequestHeader String userId) {
        JudgmentLikeResponse judgmentLikeResponse = judgmentLikeService.addJudgmentLike(userId, judgmentId);

        if (judgmentLikeResponse == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.status(201).body(HttpUtil.success("Success register judgment like",  judgmentLikeResponse));
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> cancelJudgmentLike(@PathVariable Long judgmentId, @RequestHeader String userId) {
        if (judgmentLikeService.isLiked(userId, judgmentId)) {
            judgmentLikeService.removeJudgmentLike(userId, judgmentId);
        }

        return ResponseEntity.ok(HttpUtil.success("Success cancel judgment like"));
    }

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> alreadyJudgmentLiked(@PathVariable Long judgmentId, @RequestHeader String userId) {
        return ResponseEntity.ok(HttpUtil.success("success check judgment liked", judgmentLikeService.isLiked(userId, judgmentId)));
    }
}
