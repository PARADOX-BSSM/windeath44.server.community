package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.request.PostCreateRequest;
import paradox.community.domain.community.dto.request.PostSearchRequest;
import paradox.community.domain.community.dto.request.PostUpdateRequest;
import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.exception.PostDeleteForbiddenException;
import paradox.community.domain.community.exception.PostDraftForbiddenException;
import paradox.community.domain.community.exception.PostNotFoundException;
import paradox.community.domain.community.exception.PostUpdateForbiddenException;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(String userId, PostCreateRequest request) {
        Post post = Post.builder()
                .userId(userId)
                .title(request.title())
                .body(request.body())
                .isBlind(request.isBlind())
                .status(request.status())
                .likesCount((Long) 0L)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    @Transactional
    public PostResponse publishPost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        if (!post.getUserId().equals(userId)) {
            throw PostNotFoundException.getInstance();
        }

        post.publish();
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse draftPost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        if (!post.getUserId().equals(userId)) {
            throw PostDraftForbiddenException.getInstance();
        }

        post.draft();
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, String userId, PostUpdateRequest request, String role) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        if (!role.equals("ADMIN") && !post.getUserId().equals(userId)) {
            throw PostUpdateForbiddenException.getInstance();
        }

        post.update(request.title(), request.body());
        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId, String userId, String role) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        if (!role.equals("ADMIN") && !post.getUserId().equals(userId)) {
            throw PostDeleteForbiddenException.getInstance();
        }

        post.delete();
    }

    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        return  PostResponse.from(post);
    }

    @Transactional
    public Page<PostResponse> getPosts(PostSearchRequest request, Pageable pageable) {
        Page<Post> posts = postRepository.searchPosts(
                request.status(),
                request.isBlind(),
                request.title(),
                pageable
        );
        return posts.map(PostResponse::from);
    }
}