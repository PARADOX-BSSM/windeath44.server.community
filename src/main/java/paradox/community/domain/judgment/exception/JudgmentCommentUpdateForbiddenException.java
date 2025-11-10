package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class JudgmentCommentUpdateForbiddenException extends GlobalException {
    public JudgmentCommentUpdateForbiddenException() {
        super(ErrorCode.JUDGMENT_COMMENT_UPDATE_FORBIDDEN);
    }

    static class Holder {
        private static final JudgmentCommentUpdateForbiddenException INSTANCE = new JudgmentCommentUpdateForbiddenException();
    }

    public static JudgmentCommentUpdateForbiddenException getInstance() {return Holder.INSTANCE;}
}
