package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paradox.community.domain.judgment.dto.request.JudgmentCreateRequest;
import paradox.community.domain.judgment.dto.request.JudgmentListRequest;
import paradox.community.domain.judgment.dto.request.JudgmentUpdateRequest;
import paradox.community.domain.judgment.dto.request.VoteRequest;
import paradox.community.domain.judgment.dto.response.JudgmentResponse;
import paradox.community.domain.judgment.dto.response.VoteResponse;
import paradox.community.domain.judgment.service.JudgmentService;
import jakarta.validation.Valid;
import paradox.community.domain.judgment.service.VoteService;
import paradox.community.global.dto.ApiResponse;
import paradox.community.global.util.HttpUtil;


@RestController
@RequestMapping("/communities/judgments")
@RequiredArgsConstructor
public class JudgmentController {

    private final JudgmentService judgmentService;

    // 제판 목록 조회
    @PostMapping("/list")
    public ResponseEntity<ApiResponse<Page<JudgmentResponse>>> getJudgments(JudgmentListRequest request, Pageable pageable) {
        Page<JudgmentResponse> judgments = judgmentService.getJudgments(request.characterId(), request.isEnd(), request.instance(), pageable);
        return ResponseEntity.ok(HttpUtil.success("success judgment list search", judgments));
    }

    // 제판 상세 조회
    @GetMapping("/{judgmentId}")
    public ResponseEntity<ApiResponse<JudgmentResponse>> getJudgment(@PathVariable Long judgmentId) {
        JudgmentResponse judgment = judgmentService.getJudgment(judgmentId);
        return ResponseEntity.ok(HttpUtil.success("success judgment search", judgment));
    }

    // 재판 생성 (관리자용)
    @PostMapping
    public ResponseEntity<ApiResponse<JudgmentResponse>> createJudgment(@Valid @RequestBody JudgmentCreateRequest request, @RequestHeader("role") String role) {
        JudgmentResponse judgment = judgmentService.createJudgment(request, role);

        if (judgment == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(201).body(HttpUtil.success("success judgment create response", judgment));
    }

    // 재판 수정 (관리자용)
    @PostMapping("/{judgmentId}")
    public ResponseEntity<ApiResponse<JudgmentResponse>> updateJudgment(@PathVariable Long judgmentId, @Valid @RequestBody JudgmentUpdateRequest request, @RequestHeader("role") String role) {
        JudgmentResponse judgment = judgmentService.updateJudgment(judgmentId, request, role);

        if (judgment == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok(HttpUtil.success("success judgment cancel", judgment));
    }

    // 재판 종료 (관리자용)
    @PatchMapping("/{judgmentId}")
    public ResponseEntity<ApiResponse<JudgmentResponse>> endJudgment(@PathVariable Long judgmentId, @RequestHeader("role") String role) {
        JudgmentResponse judgment = judgmentService.endJudgment(judgmentId, role);

        if (judgment == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok(HttpUtil.success("success judgment end", judgment));
    }
}
