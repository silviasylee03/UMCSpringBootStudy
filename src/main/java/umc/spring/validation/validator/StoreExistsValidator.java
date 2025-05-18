package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.validation.annotaion.ExistCategories;
import umc.spring.validation.annotaion.StoreExists;

@Component
@RequiredArgsConstructor
public class StoreExistsValidator implements ConstraintValidator<StoreExists, Long> {

    private final StoreRepository storeRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return storeRepository.existsById(value);
    }
}