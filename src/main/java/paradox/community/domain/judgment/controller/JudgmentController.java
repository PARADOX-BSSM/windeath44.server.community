package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paradox.community.domain.judgment.dto.request.JudgmentListRequest;
import paradox.community.domain.judgment.dto.response.JudgmentResponse;
import paradox.community.domain.judgment.service.JudgmentService;

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
}
