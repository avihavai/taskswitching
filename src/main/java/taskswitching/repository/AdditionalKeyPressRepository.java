package taskswitching.repository;

import taskswitching.dto.AdditionalKeyPress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalKeyPressRepository extends JpaRepository<AdditionalKeyPress, Long> {
}
