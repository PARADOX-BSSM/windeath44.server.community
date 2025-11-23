package paradox.community.domain.judgment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentInstance;
import paradox.community.domain.judgment.model.JudgmentStatus;

import java.util.List;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {
    List<Judgment> findByTitleContaining(String title);
    Page<Judgment> findByStatus(JudgmentStatus status, Pageable pageable);
    Page<Judgment> findByInstance(JudgmentInstance instanceId, Pageable pageable);
    Page<Judgment> findByStatusAndInstance(JudgmentStatus status, JudgmentInstance instance, Pageable pageable);
}
