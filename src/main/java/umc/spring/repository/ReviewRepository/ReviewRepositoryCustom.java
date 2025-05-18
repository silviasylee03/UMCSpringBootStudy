package umc.spring.repository.ReviewRepository;

public interface ReviewRepositoryCustom {
    void addReview(Long store_id, Long member_id, String title, String content, Integer score);
}
