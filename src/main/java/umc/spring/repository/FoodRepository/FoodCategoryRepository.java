package umc.spring.repository.FoodRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.FoodCategory;

import java.util.List;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    Long countByIdIn(List<Long> ids);
}