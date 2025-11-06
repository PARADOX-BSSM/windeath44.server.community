package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class JudgmentNotFoundException extends GlobalException {
    public JudgmentNotFoundException() {
        super(ErrorCode.JUDGMENT_NOT_FOUND);
    }

    private static class Holder {
        private static final JudgmentNotFoundException INSTANCE = new JudgmentNotFoundException();
    }

    public static JudgmentNotFoundException getInstance() {
        return Holder.INSTANCE;
    }
}
