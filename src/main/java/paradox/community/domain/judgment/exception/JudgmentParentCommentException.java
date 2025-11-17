package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class JudgmentParentCommentException extends GlobalException {
    public JudgmentParentCommentException() {
        super(ErrorCode.JUDGMENT_PARENT_COMMENT_NOT_FOUND);
    }

    private static class Holder {
        private static final JudgmentParentCommentException INSTANCE = new JudgmentParentCommentException();
    }

    public static JudgmentParentCommentException getInstance() {
        return Holder.INSTANCE;
    }
}
