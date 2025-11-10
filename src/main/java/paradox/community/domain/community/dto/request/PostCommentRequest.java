package paradox.community.domain.community.dto.request;

public record PostCommentRequest(
    Long parentCommentId,
    String body
) {
    public PostCommentRequest {
        if (body == null) throw new IllegalArgumentException("body cannot be null");
    }

}
