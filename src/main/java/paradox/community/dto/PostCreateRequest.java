package paradox.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paradox.community.enumclass.PostStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    private Long characterId;
    private String title;
    private String body;
    private PostStatus status;
    private Boolean isBlind;
}
