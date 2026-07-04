package com.urbanflow.repository;

import com.urbanflow.entity.DecisionFeedback;
import com.urbanflow.entity.UserDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisionFeedbackRepository extends JpaRepository<DecisionFeedback, Long> {

    // شمارش تعداد فیدبک‌ها بر اساس تصمیم کاربر (AGREE / DISAGREE)
    long countByUserDecision(UserDecision userDecision);
}
