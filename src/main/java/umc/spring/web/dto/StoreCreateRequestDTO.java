package umc.spring.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class StoreCreateRequestDTO {
    @Getter
    public static class StoreCreateDTO{
        @NotNull
        String name;

        @NotNull
        String address;
    }
}
