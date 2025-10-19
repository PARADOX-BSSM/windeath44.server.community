package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.request.PostCreateRequest;
import paradox.community.domain.community.dto.request.PostUpdateRequest;
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
                .isBlind(request.isBlind())
                .status(request.status())
                .likesCount(0L)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    @Transactional
    public PostResponse publishPost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist!"));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Post can only be published by authors!");
        }

        post.publish();
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse draftPost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist!"));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Post can only be drafted by authors!");
        }

        post.draft();
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, String userId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist!"));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Post can only be updated by authors!");
        }

        post.update(request.title(), request.body());
        return PostResponse.from(post);
    }
}