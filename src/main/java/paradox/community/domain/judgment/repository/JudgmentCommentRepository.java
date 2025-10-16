package paradox.community.domain.judgment.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentComment;

import java.util.List;

@Repository
public interface JudgmentCommentRepository extends JpaRepository<JudgmentComment, Long> {

    List<JudgmentComment> findByJudgmentId(Integer judgmentId);

    List<JudgmentComment> findByUserId(String userId);

    List<JudgmentComment> findByParentCommentId(Integer parentCommentId);

    List<JudgmentComment> findByJudgmentIdAndParentCommentIdIsNull(Integer judgmentId);

    @Query("SELECT COUNT(jc) FROM JudgmentComment jc WHERE jc.judgmentId = :judgmentId")
    Long countByJudgmentId(@Param("judgmentId") Integer judgmentId);
}
