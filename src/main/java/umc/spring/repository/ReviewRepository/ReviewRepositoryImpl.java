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
    public void addReview(String title, Float score, Long member_id, Long store_id) {
        if (title != null || score != null || member_id != null || store_id != null) {
            Review review = Review.builder()
                    .title(title)
                    .score(score) // DB가 float이면 변환
                    .member(em.getReference(Member.class, member_id))
                    .store(em.getReference(Store.class, store_id))
                    .build();

            em.persist(review);
        }
    }
}
