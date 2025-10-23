package paradox.community.domain.judgment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paradox.community.domain.judgment.service.JudgmentCommentLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities/judgments/{judgement-id}/comments/{comment-id}/likes")
public class JudgmentCommentLikeController {

    private final JudgmentCommentLikeService judgmentCommentLikeService;

    @
}
