package net.nikodem.integration

import net.nikodem.NikodemocracyTabulationApplication
import net.nikodem.model.exception.AnswerDoesNotExistException
import net.nikodem.model.exception.UnauthorizedVoteSubmissionException
import net.nikodem.model.exception.VoterKeyHasBeenUsedException
import net.nikodem.model.dto.ElectionInformation
import net.nikodem.model.dto.VoteSubmission
import net.nikodem.repository.VoteRepository
import net.nikodem.service.ElectionReceivingService
import net.nikodem.service.VoteReceivingService
import net.nikodem.testdata.ExampleData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static net.nikodem.testdata.ExampleData.*

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyTabulationApplication.class)
class SubmitVoteUseCaseSpec extends Specification {

    @Autowired
    ElectionReceivingService electionReceivingService

    @Autowired
    VoteReceivingService voteReceivingService

    @Autowired
    VoteRepository voteRepository

    @Transactional
    @Rollback
    def "Votes must have valid voter key"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'd', 'nonexistentKey', null))
        then:
        thrown(UnauthorizedVoteSubmissionException)
    }

    @Transactional
    @Rollback
    def "ElectionId must exist"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('02', 'a',FIRST_VOTER_KEY, 'hello'))
        then:
        thrown(UnauthorizedVoteSubmissionException)
    }

    @Transactional
    @Rollback
    def "Answer must exist"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'z', FIRST_VOTER_KEY, 'myVoteKey'))
        then:
        thrown(AnswerDoesNotExistException)
    }

    @Transactional
    @Rollback
    def "VoterKey can not be used more than once"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        voteReceivingService.receiveVote(new VoteSubmission('01', 'd', FIRST_VOTER_KEY, 'myVoteKey'))
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'a', FIRST_VOTER_KEY, null))
        then:
        thrown(VoterKeyHasBeenUsedException)
    }

    @Transactional
    @Rollback
    def "One person votes"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'b', FIRST_VOTER_KEY, 'silverFox'))
        then:
        voteRepository.count() == 1
    }

    @Transactional
    @Rollback
    def "Everybody votes"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        int i = 2
        ELECTION_EXAMPLE.voterKeys.forEach{
            voteReceivingService.receiveVote(new VoteSubmission('01','a',it,Integer.toString(i++)))
        }
        then:
        voteRepository.count() == ELECTION_EXAMPLE.voterKeys.size()
    }
}
