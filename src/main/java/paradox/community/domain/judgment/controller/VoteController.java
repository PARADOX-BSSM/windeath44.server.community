package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.request.VoteRequest;
import paradox.community.domain.judgment.dto.response.VoteHistoryResponse;
import paradox.community.domain.judgment.dto.response.VoteResponse;
import paradox.community.domain.judgment.service.VoteService;
import paradox.community.global.dto.ApiResponse;
import paradox.community.domain.judgment.dto.response.VoteCreateResponse;
import paradox.community.global.util.HttpUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
@Slf4j
@CrossOrigin(
        origins = {
                "http://localhost:5173",       // 테스트 1
                "http://localhost:5174",      // 테스트 2
                "https://windeath44.wiki" // 배포용 URL
        },
        allowCredentials = "true" // 필요하면 쿠키 허용
)
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/judgments/{judgmentId}/votes")
    public ResponseEntity<ApiResponse<VoteCreateResponse>> recordVote(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId,
            @RequestBody VoteRequest voteRequest) {
        VoteCreateResponse response = voteService.recordVote(
                userId,
                judgmentId,
                voteRequest.isHeaven()
        );

        return ResponseEntity.status(201).body(
                HttpUtil.success(
                        "User id: " + userId + " successfully voted for judgment id: " + judgmentId, response
                )
        );
    }

    @GetMapping("/judgments/{judgmentId}/votes")
    public ResponseEntity<ApiResponse<VoteResponse>> getVotingStats(
            @PathVariable Long judgmentId) {
        VoteResponse response = voteService.getVotingStats(judgmentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "Voting statistics for judgment id: " + judgmentId, response
                )
        );
    }

    @GetMapping("/users/votes")
    public ResponseEntity<ApiResponse<List<VoteHistoryResponse>>> getMyVoteHistory(
            @RequestHeader("user-id") String userId) {
        List<VoteHistoryResponse> history = voteService.getMyVoteHistory(userId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "User Id: " + userId + " vote history: " + history.size(), history
                )
        );
    }

    @DeleteMapping("/judgments/{judgmentId}/votes")
    public ResponseEntity<ApiResponse<Void>> deleteVote(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId) {

        voteService.deleteVote(userId, judgmentId);

        return ResponseEntity.ok(
                HttpUtil.success(
                        "User id: " + userId + " successfully deleted vote for judgment id: " + judgmentId
                )
        );
    }

    @GetMapping("/judgments/{judgmentId}/votes/me")
    public ResponseEntity<ApiResponse<Boolean>> getMyVote(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId) {

        Optional<Boolean> voteDirection = voteService.getUserVoteDirection(userId, judgmentId);

        if (voteDirection.isEmpty()) {
            return ResponseEntity.ok(
                    HttpUtil.success(
                            "User Id: " + userId + " has not voted for judgment id: " + judgmentId, null
                    )
            );
        }

        String direction = voteDirection.get() ? "HEAVEN" : "HELL";
        return ResponseEntity.ok(
                HttpUtil.success(
                        "User id: " + userId + " voted " + direction + " for judgment id: " + judgmentId, voteDirection.get()
                )
        );
    }
}
