package umc.spring.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final EntityManager em;

    @Override
    public void addReview(Long storeId, Long memberId, String title, String content, Integer score) {
        if (title != null || score != null || memberId != null || storeId != null) {
            Review review = Review.builder()
                    .title(title)
                    .content(content)
                    .score(score)
                    .member(em.getReference(Member.class, memberId))
                    .store(em.getReference(Store.class, storeId))
                    .build();

            em.persist(review);
        }
    }
}
