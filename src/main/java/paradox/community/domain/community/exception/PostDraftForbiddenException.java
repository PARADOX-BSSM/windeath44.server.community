package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostDraftForbiddenException extends GlobalException {
    public PostDraftForbiddenException() {
        super(ErrorCode.POST_DRAFT_FORBIDDEN);
    }

    private static class Holder {
        private static final PostDraftForbiddenException INSTANCE = new PostDraftForbiddenException();
    }

    public static PostDraftForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
