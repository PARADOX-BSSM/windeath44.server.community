package paradox.community.domain.judgment.controller;

import org.springframework.data.repository.Repository;
import paradox.community.domain.judgment.model.Judgment;

interface JudgmentRepository extends Repository<Judgment, Long> {
}
