package umc.spring.web.controller;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Store;
import umc.spring.service.StoreService.StoreCommandService;
import umc.spring.web.dto.StoreCreateRequestDTO;
import umc.spring.web.dto.StoreCreateResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {
    private final StoreCommandService storeCommandService;

    @PostMapping("/{regionId}/stores")
    public ApiResponse<StoreCreateResponseDTO.StoreCreateResultDTO> addStore(@RequestBody @Valid StoreCreateRequestDTO.StoreCreateDTO request, @PathVariable Long regionId) {
        Store store = storeCommandService.addStore(request, regionId);
        return ApiResponse.onSuccess(StoreConverter.toAddResultDTO(store));
    }
}
