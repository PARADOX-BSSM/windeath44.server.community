package paradox.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentResponse {

    @JsonProperty("commentId")
    private Long commentId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("parentCommentId")
    private Long parentCommentId;

    @JsonProperty("body")
    private String body;

    @JsonProperty("createdAt")
    private String createdAt;
}
