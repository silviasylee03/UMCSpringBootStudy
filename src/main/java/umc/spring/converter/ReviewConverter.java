package umc.spring.converter;

import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

public class ReviewConverter {
    public static ReviewResponseDTO.ReviewCreateResultDTO toAddReviewDTO(Review review) {
        return ReviewResponseDTO.ReviewCreateResultDTO.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .author(review.getMember().getName())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static Review toReview(ReviewRequestDTO.ReviewCreateDTO request, Store store, Member member) {

        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .score(request.getRating())
                .member(member)
                .store(store)
                .build();
    }
}
