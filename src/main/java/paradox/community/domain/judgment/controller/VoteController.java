package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.request.VoteRequest;
import paradox.community.domain.judgment.service.VoteService;
import paradox.community.global.dto.ApiResponse;
import paradox.community.domain.judgment.dto.response.VoteCreateResponse;
import paradox.community.global.util.HttpUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities/judgments")
@Slf4j
public class VoteController {
    private final VoteService voteService;

    // 투표 생성 또는 변경
    @PostMapping("/{judgmentId}/votes")
    public ResponseEntity<VoteCreateResponse> recordVote(
            @RequestHeader("user-id") String userId,
            @RequestBody VoteRequest voteRequest,
            @PathVariable Long judgmentId) {

        VoteCreateResponse response = voteService.recordVote(userId, judgmentId, voteRequest.isHeaven());
        return ResponseEntity.status(201).body(HttpUtil.success("voted: ", + response));
    }

    // 투표 조회
    @GetMapping("/{judgmentId}/votes")
    public ResponseEntity<VoteCreateResponse> getVote(
            @RequestHeader("user-id") String userId,
            @PathVariable Long judgmentId) {

        VoteCreateResponse response = voteService.getVote(userId, judgmentId, );
        return ResponseEntity.ok(HttpUtil.success("투표 결과를 조회했습니다: " + response));
    }
}
