package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import paradox.community.domain.community.model.PostStatus;
import paradox.community.domain.community.repository.PostCommentRepository;
import paradox.community.domain.community.repository.PostRepository;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    private Post getActivePost(Long postId) {
        return postRepository.findByPostIdAndIsDeletedFalse(postId)
                .orElseThrow(PostNotFoundException::getInstance);
    }

    @Transactional
    public PostResponse createPost(String userId, PostCreateRequest request) {
        Post post = Post.builder()
                .userId(userId)
                .title(request.title())
                .body(request.body())
                .isBlind(request.isBlind())
                .status(request.status())
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost, 0L);
    }

    @Transactional
    public PostResponse publishPost(Long postId, String userId) {
        Post post = getActivePost(postId);

        if (!post.getUserId().equals(userId)) {
            throw PostUpdateForbiddenException.getInstance();
        }

        post.publish();
        Long postCommentsCount = postCommentRepository.countByPostId(postId);
        return PostResponse.from(post, postCommentsCount);
    }

    @Transactional
    public PostResponse draftPost(Long postId, String userId) {
        Post post = getActivePost(postId);

        if (!post.getUserId().equals(userId)) {
            throw PostDraftForbiddenException.getInstance();
        }

        post.draft();
        Long postCommentsCount = postCommentRepository.countByPostId(postId);
        return PostResponse.from(post, postCommentsCount);
    }

    @Transactional
    public PostResponse updatePost(Long postId, String userId, PostUpdateRequest request, String role) {
        Post post = getActivePost(postId);

        if (!role.equals("ADMIN") && !post.getUserId().equals(userId)) {
            throw PostUpdateForbiddenException.getInstance();
        }

        post.update(request.title(), request.body());
        post.changeStatus(request.status());
        post.changeBlind(request.isBlind());

        Long postCommentsCount = postCommentRepository.countByPostId(postId);
        return PostResponse.from(post, postCommentsCount);
    }

    @Transactional
    public void deletePost(Long postId, String userId, String role) {
        Post post = getActivePost(postId);

        if (!role.equals("ADMIN") && !post.getUserId().equals(userId)) {
            throw PostDeleteForbiddenException.getInstance();
        }

        post.delete();
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = getActivePost(postId);

        Long postCommentCount = postCommentRepository.countByPostId(postId);
        return PostResponse.from(post, postCommentCount);
    }

    @Transactional
    public Page<PostResponse> getPosts(PostSearchRequest request, Pageable pageable, String userId) {

        // --- mode 기반 정렬 처리 (HEAD 기능) ---
        String mode = request.mode();
        if (mode == null || mode.isBlank()) {
            mode = "normal";
        }

        Sort sort = switch (mode) {
            case "popularity" -> Sort.by(Sort.Direction.DESC, "viewsCount");
            case "latest"     -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "normal"     -> pageable.getSort();
            default           -> pageable.getSort();
        };

        Pageable pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        String draftOwnerId = request.status() == PostStatus.DRAFT ? userId : null;

        Page<Post> posts = postRepository.searchPosts(
                request.status(),
                request.isBlind(),
                request.title(),
                draftOwnerId,
                pageRequest
        );

        return posts.map(post -> {
            Long postCommentCount = postCommentRepository.countByPostId(post.getPostId());
            return PostResponse.from(post, postCommentCount);
        });
    }
}
