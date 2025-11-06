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
    public PostResponse updatePost(Long postId, String userId, PostUpdateRequest request, String role) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist!"));

        if (!role.equals("ADMIN") && !post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Post can only be updated by authors!");
        }

        post.update(request.title(), request.body());
        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId, String userId, String role) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist!"));

        if (!role.equals("ADMIN") && !post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Post can only be deleted by authors!");
        }

        post.delete();
    }

    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist!"));

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