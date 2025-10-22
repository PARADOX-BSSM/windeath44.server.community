package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paradox.community.domain.judgment.service.JudgmentLikeService;

@RestController
@RequestMapping("/communities/judgments/{judgment-id}/likes")
@RequiredArgsConstructor
public class JudgmentLikeController {

    private final JudgmentLikeService judgmentLikeService;

    // 좋아요

}
