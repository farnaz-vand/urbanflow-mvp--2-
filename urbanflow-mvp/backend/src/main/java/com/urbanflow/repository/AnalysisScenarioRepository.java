package com.urbanflow.repository;

import com.urbanflow.entity.AnalysisScenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisScenarioRepository extends JpaRepository<AnalysisScenario, Long> {

    // این متد لیست تمام سناریوهای ذخیره شده را برای پردازش توزیع برمی‌گرداند
    // اگر AnalyticsService از findAll() استفاده می‌کند، این متد را برای استفاده‌های خاص یا کوئری‌های بهینه نگه می‌داریم
}
