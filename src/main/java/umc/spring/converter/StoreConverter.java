package umc.spring.converter;

import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.web.dto.StoreCreateRequestDTO;
import umc.spring.web.dto.StoreCreateResponseDTO;

import java.time.LocalDateTime;

public class StoreConverter {
    public static StoreCreateResponseDTO.StoreCreateResultDTO toAddResultDTO(Store store) {
        return StoreCreateResponseDTO.StoreCreateResultDTO.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .address(store.getAddress())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreCreateRequestDTO.StoreCreateDTO request, Region region) {
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .region(region)
                .build();
    }
}
