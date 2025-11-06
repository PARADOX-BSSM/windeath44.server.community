package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class JudgmentCommentDeleteForbiddenException extends GlobalException {
    public JudgmentCommentDeleteForbiddenException() {
        super(ErrorCode.JUDGMENT_COMMENT_DELETE_FORBIDDEN);
    }

    static class Holder {
        private static final JudgmentCommentDeleteForbiddenException INSTANCE = new JudgmentCommentDeleteForbiddenException();
    }

    public static JudgmentCommentDeleteForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
