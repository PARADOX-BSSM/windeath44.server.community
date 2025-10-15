package paradox.community.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String userId;
    private Long characterId;
    private String title;
    private String body;
    private String status;
    private Boolean isBlind;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
