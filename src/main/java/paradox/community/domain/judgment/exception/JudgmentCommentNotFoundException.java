package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class JudgmentCommentNotFoundException extends GlobalException {
    public JudgmentCommentNotFoundException() {
        super(ErrorCode.JUDGMENT_COMMENT_NOT_FOUND);
    }

    static class Holder {
        private static final JudgmentCommentNotFoundException INSTANCE = new JudgmentCommentNotFoundException();
    }

    public static JudgmentCommentNotFoundException getInstance() {
        return Holder.INSTANCE;
    }

}
