package taskswitching.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import taskswitching.dto.AdditionalKeyPress;
import taskswitching.dto.Participant;
import taskswitching.dto.Reaction;
import taskswitching.dto.TestResult;
import taskswitching.repository.AdditionalKeyPressRepository;
import taskswitching.repository.ParticipantRepository;
import taskswitching.repository.ReactionRepository;
import taskswitching.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private AdditionalKeyPressRepository additionalKeyPressRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    @Async
    @Transactional(readOnly = false)
    public void save(TestResult result) {
        if (result.getParticipant() == null || result.getParticipant().getUsername() == null) {
            return;
        }

        Participant p = participantRepository.findByUsernameAndContextPath(result.getParticipant().getUsername(), servletContext.getContextPath());
        result.setParticipant(p);

        List<AdditionalKeyPress> presses = new ArrayList<AdditionalKeyPress>();
        for (AdditionalKeyPress additionalKeyPress : result.getAdditionalKeyPresses()) {
            presses.add(additionalKeyPressRepository.save(additionalKeyPress));
        }
        result.setAdditionalKeyPresses(presses);

        List<Reaction> reactions = new ArrayList<Reaction>();
        for (Reaction reaction : result.getReactions()) {
            reactions.add(reactionRepository.save(reaction));
        }
        result.setReactions(reactions);

        resultRepository.save(result);
    }

    @Transactional(readOnly = true)
    public List<TestResult> list() {
        return resultRepository.findAll();
    }

    @Transactional(readOnly = true)
    public int getCount(Long participantId, String testType, String info) {
        Participant participant = participantRepository.findOne(participantId);
        if (participant == null) {
            return 0;
        }

        return resultRepository.findByParticipantAndTestTypeAndInfo(participant, testType, info).size();
    }
}
