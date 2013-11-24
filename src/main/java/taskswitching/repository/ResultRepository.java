package taskswitching.repository;

import java.util.List;
import taskswitching.dto.Participant;
import taskswitching.dto.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> findByParticipantAndTestTypeAndInfo(Participant participant, String testType, String info);
}
