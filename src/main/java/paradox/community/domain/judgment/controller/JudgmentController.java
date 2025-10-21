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
import paradox.community.domain.judgment.dto.response.JudgmentCreateResponse;
import paradox.community.domain.judgment.dto.response.JudgmentResponse;
import paradox.community.domain.judgment.dto.response.VoteResponse;
import paradox.community.domain.judgment.service.JudgmentService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/communities/judgments")
@RequiredArgsConstructor
public class JudgmentController {

    private final JudgmentService judgmentService;

    // 제판 목록 조회
    @PostMapping
    public ResponseEntity<Page<JudgmentResponse>> getJudgments(JudgmentListRequest request, Pageable pageable) {
        Page<JudgmentResponse> judgments = judgmentService.getJudgments(request.characterId(), request.isEnd(), request.instance(), pageable);
        return ResponseEntity.ok(judgments);
    }

    // 제판 상세 조회
    @GetMapping("/{judgmentId}")
    public ResponseEntity<JudgmentResponse> getJudgment(@PathVariable Long judgmentId) {
        JudgmentResponse judgment = judgmentService.getJudgment(judgmentId);
        return ResponseEntity.ok(judgment);
    }

    // 재판 생성 (관리자용)
    @PostMapping
    public ResponseEntity<JudgmentCreateResponse> createJudgment(@Valid @RequestBody JudgmentCreateRequest request) {
        JudgmentCreateResponse judgment = judgmentService.createJudgment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(judgment);
    }

    // 재판 수정 (관리자용)
    @PostMapping("/{judgmentId}")
    public ResponseEntity<JudgmentResponse> updateJudgment(@PathVariable Long judgmentId, @Valid @RequestBody JudgmentUpdateRequest request) {
        JudgmentResponse judgment = judgmentService.updateJudgment(judgmentId, request);
        return ResponseEntity.ok(judgment);
    }

    // 재판 종료 (관리자용)
    @PatchMapping("/{judgmentId}")
    public ResponseEntity<JudgmentResponse> endJudgment(@PathVariable Long judgmentId) {
        JudgmentResponse judgment = judgmentService.endJudgment(judgmentId);
        return ResponseEntity.ok(judgment);
    }

    // 투표
    @PostMapping("/{judgment-id}/votes")
    public ResponseEntity<VoteResponse> vote(@PathVariable Long judgmentId, @RequestBody) {
        
    }
}
