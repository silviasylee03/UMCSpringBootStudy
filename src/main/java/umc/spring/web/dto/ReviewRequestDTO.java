package umc.spring.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotaion.StoreExists;

public class ReviewRequestDTO {
    @Getter
    public static class ReviewCreateDTO {
        @StoreExists  // ✅ 이제 유효성 검증 가능!
        @NotNull
        private Long storeId;

        @NotNull
        private Long memberId;

        @NotNull
        String title;

        @NotBlank
        String content;

        @NotNull
        @Min(1)
        @Max(5)
        Integer rating;
    }
}
