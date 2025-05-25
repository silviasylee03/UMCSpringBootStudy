package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Region;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.StoreCreateRequestDTO;
import umc.spring.web.dto.StoreCreateResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static StoreCreateResponseDTO.ReviewPreviewDTO ReviewPreviewDTO(Review review) {
        return StoreCreateResponseDTO.ReviewPreviewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .rating(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getContent())
                .build();
    }

    public static StoreCreateResponseDTO.ReviewPreviewListDTO reviewPreviewListDTO(Page<Review> reviewList) {
        List<StoreCreateResponseDTO.ReviewPreviewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(StoreConverter::ReviewPreviewDTO).collect(Collectors.toList());

        return StoreCreateResponseDTO.ReviewPreviewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }
}
