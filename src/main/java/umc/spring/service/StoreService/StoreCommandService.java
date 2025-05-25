package umc.spring.service.StoreService;

import umc.spring.domain.Store;
import umc.spring.web.dto.StoreCreateRequestDTO;

import java.util.List;

public interface StoreCommandService {
    Store addStore(StoreCreateRequestDTO.StoreCreateDTO storeCreateDTO, Long regionId);
}
