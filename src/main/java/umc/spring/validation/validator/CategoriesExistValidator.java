package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.service.FoodCategoryService.FoodCategoryService;
import umc.spring.validation.annotation.ExistCategories;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesExistValidator implements ConstraintValidator<ExistCategories, List<Long>> {

//    private final FoodCategoryRepository foodCategoryRepository;
    private final FoodCategoryService foodCategoryService;

    @Override
    public void initialize(ExistCategories constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

//    @Override
//    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
//        boolean isValid = values.stream()
//                .allMatch(value -> foodCategoryRepository.existsById(value));
//
//        if (!isValid) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate(ErrorStatus.FOOD_CATEGORY_NOT_FOUND.toString()).addConstraintViolation();
//        }
//
//        return isValid;
//
//    }
    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return false;
        return foodCategoryService.existsAllById(value);
    }
}
