package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.response.PostCommentCreateResponse;
import paradox.community.domain.community.model.PostComment;
import paradox.community.domain.community.repository.PostCommentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentService {
    private final PostCommentRepository commentRepository;

    @Transactional
    public PostCommentCreateResponse createComment(String userId, Long postId, ) {}
}
