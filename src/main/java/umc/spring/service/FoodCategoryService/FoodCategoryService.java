package umc.spring.service.FoodCategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.repository.FoodRepository.FoodCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryRepository categoryRepository;

    public boolean existsAllById(List<Long> ids) {
        return categoryRepository.countByIdIn(ids) == ids.size();
    }
}