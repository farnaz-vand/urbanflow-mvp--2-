package com.urbanflow.repository;

import com.urbanflow.entity.DecisionFeedback;
import com.urbanflow.entity.UserDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisionFeedbackRepository extends JpaRepository<DecisionFeedback, Long> {
    
    // این متد توسط Spring Data JPA به صورت خودکار پیاده‌سازی می‌شود
    long countByUserDecision(UserDecision userDecision);
    
}