package paradox.community.domain.judgment.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByJudgmentId(Long judgmentId);

    List<Vote> findByUserId(String userId);

    boolean existsByUserIdAndJudgmentId(String userId, Long judgmentId);

    Optional<Vote> findByUserIdAndJudgmentId(String userId, Long judgmentId);

    void deleteByUserIdAndJudgmentId(String userId, Long judgmentId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.judgmentId = :judgmentId AND v.isHeaven = true")
    Long countHeavenVotes(@Param("judgmentId") Long judgmentId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.judgmentId = :judgmentId AND v.isHeaven = false")
    Long countHellVotes(@Param("judgmentId") Long judgmentId);
}
