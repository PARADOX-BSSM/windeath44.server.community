package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class JudgmentComment2DepthOverForbiddenException extends GlobalException {
    public JudgmentComment2DepthOverForbiddenException() {
        super(ErrorCode.JUDGMENT_COMMENT_2DEPTH_OVER_FORBIDDEN);
    }

    static class Holder {
        private static final JudgmentComment2DepthOverForbiddenException INSTANCE = new JudgmentComment2DepthOverForbiddenException();
    }

    public static JudgmentComment2DepthOverForbiddenException getInstance() {return Holder.INSTANCE;}
}
