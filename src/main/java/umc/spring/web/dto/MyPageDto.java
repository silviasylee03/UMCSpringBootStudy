package umc.spring.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyPageDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Boolean phoneVerified;
    private Integer point;
}
