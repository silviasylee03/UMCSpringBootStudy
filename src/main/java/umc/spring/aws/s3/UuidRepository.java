package umc.spring.aws.s3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Uuid;

@Repository
public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
