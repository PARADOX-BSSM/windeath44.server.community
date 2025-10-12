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

public class PostResponse {
    @JsonProperty
    private Long postId;

    @JsonProperty
    private String userId;

    @JsonProperty
    private Long characterId;

    @JsonProperty
    private String title;

    @JsonProperty
    private String body;

    @JsonProperty
    private String status;

    @JsonProperty
    private Boolean isBlind;

    @JsonProperty
    private String createdAt;

    @JsonProperty
    private String updatedAt;
}
