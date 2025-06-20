package umc.spring.service.ReviewService;

import org.springframework.web.multipart.MultipartFile;
import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;

public interface ReviewCommandService {
    Review addReview(ReviewRequestDTO.ReviewCreateDTO request);
    Review createReview(Long memberId, Long storeId, ReviewRequestDTO.ReviewCreateDTO request,
                        MultipartFile reviewImage);
}
