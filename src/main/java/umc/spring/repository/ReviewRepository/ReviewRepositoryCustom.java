package umc.spring.repository.ReviewRepository;

public interface ReviewRepositoryCustom {
    void addReview(String title, Float score, Long member_id, Long store_id);
}
