package taskswitching.repository;

import taskswitching.dto.AuthenticationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationInformationRepository extends JpaRepository<AuthenticationInformation, Long> {
}
