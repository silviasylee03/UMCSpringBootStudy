package umc.spring.repository.ReviewImageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
