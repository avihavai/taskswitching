
package taskswitching.repository;

import taskswitching.dto.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByUsernameAndContextPath(String username, String contextPath);
}
