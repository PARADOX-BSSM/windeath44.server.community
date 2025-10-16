package paradox.community.domain.community.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(String userId, PostRequest request) {
        Post post = Post.builder()
                .userId(userId)
                .characterId(request.getCharacterId())
                .title(request.getTitle())
                .body(request.getBody())
                .isBlind(false)
                .status(request.getStatus())
                .likesCount(0)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    @Transactional
    public PostDto.Response publishPost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시글 작성자만 발행할 수 있습니다.");
        }

        post.publish();
        return PostDto.Response.from(post);
    }

    @Transactional
    public PostDto.Response updatePost(Long postId, String userId, PostDto.UpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.");
        }

        post.update(request.getTitle(), request.getBody());
        return PostDto.Response.from(post);
    }

    @Transactional
    public void deletePost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시글 작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    public PostDto.Response getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        return PostDto.Response.from(post);
    }

    public Page<PostDto.Response> getPosts(Long characterId, Pageable pageable) {
        Page<Post> posts = characterId != null
                ? postRepository.findByCharacterIdAndStatus(characterId, Post.Status.PUBLISHED, pageable)
                : postRepository.findByStatus(Post.Status.PUBLISHED, pageable);

        return posts.map(PostDto.Response::from);
    }

    @Transactional
    public void toggleLike(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 좋아요 토글 로직은 PostLikeService에서 처리
    }
}