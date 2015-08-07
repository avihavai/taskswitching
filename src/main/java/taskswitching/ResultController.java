package taskswitching;

import taskswitching.service.ResultService;
import taskswitching.view.DataTablesOutputVO;
import taskswitching.dto.TestResult;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import taskswitching.repository.ParticipantRepository;
import taskswitching.service.AggregateResultService;
import taskswitching.view.AggregateResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultController {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ResultService resultService;
    @Autowired
    private AggregateResultService aggregateResultService;
    
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(method = RequestMethod.POST, value = "result", consumes = "application/json")
    @ResponseBody
    public AggregateResultVO postResult(@RequestBody TestResult result) {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username != null) {
                result.getParticipant().setUsername(username);
            }
        }

        result.setParticipant(participantRepository.findByUsernameAndContextPath(result.getParticipant().getUsername(), servletContext.getContextPath()));

        resultService.save(result);

        return aggregateResultService.calculateResult(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "result", produces = "application/json")
    @ResponseBody
    public DataTablesOutputVO getResults() {
        DataTablesOutputVO output = new DataTablesOutputVO();
        List<AggregateResultVO> aggResults = new ArrayList<AggregateResultVO>();
        for (TestResult testResult : resultService.list()) {
            aggResults.add(aggregateResultService.calculateResult(testResult));
        }

        output.setAaData(aggResults);
        return output;
    }
}
