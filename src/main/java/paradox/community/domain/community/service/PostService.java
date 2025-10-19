package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.request.PostCreateRequest;
import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createResponse(String userId, PostCreateRequest request) {
        Post post = Post.builder()
                .userId(userId)
                .characterId(request.characterId())
                .title(request.title())
                .body(request.body())
                .blind(request.isBlind())
                .status(request.status())
                .likesCount(0L)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }
}