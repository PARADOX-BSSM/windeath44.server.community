package paradox.community.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    POST_NOT_FOUND(404, "Post not found"),
    POST_PARENT_COMMENT_NOT_FOUND(404, "Post parent comment not found"),
    POST_COMMENT_NOT_FOUND(404, "Post comment not found"),
    POST_ALREADY_DELETED(400, "Post already deleted"),
    POST_COMMENT_ALREADY_DELETED(400, "Post comment already deleted"),
    POST_COMMENT_DELETE_FORBIDDEN(403, "Post comment delete forbidden"),

    JUDGMENT_NOT_FOUND(404, "Judgment not found"),
    JUDGMENT_PARENT_COMMENT_NOT_FOUND(404, "Judgment parent comment not found"),
    JUDGMENT_COMMENT_NOT_FOUND(404, "Judgment comment not found"),
    JUDGMENT_ALREADY_DELETED(400, "Judgment already deleted"),
    JUDGMENT_COMMENT_ALREADY_DELETED(400, "Post comment already deleted"),
    JUDGMENT_COMMENT_DELETE_FORBIDDEN(403, "Judgment comment delete forbidden"),

    VOTE_HISTORY_NOT_FOUND(404, "Vote history not found"),

    USER_NOT_FOUND(404, "User not found");
    private int status;
    private String message;
}