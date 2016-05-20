package net.nikodem.dbIntegration

import net.nikodem.NikodemocracyTabulationApplication
import net.nikodem.model.exception.AnswerDoesNotExistException
import net.nikodem.model.exception.UnauthorizedVoteSubmissionException
import net.nikodem.model.exception.VoterKeyHasBeenUsedException
import net.nikodem.model.json.ElectionInformation
import net.nikodem.model.json.ElectionResults
import net.nikodem.model.json.VoteSubmission
import net.nikodem.repository.VoteRepository
import net.nikodem.service.ElectionReceivingService
import net.nikodem.service.ResultsService
import net.nikodem.service.VoteReceivingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyTabulationApplication.class)
class SubmitVoteUserCaseSpec extends Specification {

    @Autowired
    ElectionReceivingService electionReceivingService

    @Autowired
    VoteReceivingService voteReceivingService

    @Autowired
    ResultsService resultsService

    @Autowired
    VoteRepository voteRepository

    List<String> answers = ['a', 'b','c','d', 'e', 'h']
    List<String> voterKeys = ['0000111122223333', '0011223344556677', '0123456701234567', '1112223334445556', '9999888877776666']
    ElectionInformation ELECTION_EXAMPLE = new ElectionInformation(
            '01',
            'Favorite alphabet character',
            answers,
            voterKeys
    )

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
        voteReceivingService.receiveVote(new VoteSubmission('02', 'a', '0000111122223333', 'hello'))
        then:
        thrown(UnauthorizedVoteSubmissionException)
    }

    @Transactional
    @Rollback
    def "Answer must exist"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'z', '0000111122223333', 'myVoteKey'))
        then:
        thrown(AnswerDoesNotExistException)
    }

    @Transactional
    @Rollback
    def "VoterKey can not be used more than once"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        voteReceivingService.receiveVote(new VoteSubmission('01', 'd', '0000111122223333', 'myVoteKey'))
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'a', '0000111122223333', null))
        then:
        thrown(VoterKeyHasBeenUsedException)
    }

    @Transactional
    @Rollback
    def "One person votes"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'b', '0000111122223333', 'silverFox'))
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
        voterKeys.forEach{
            voteReceivingService.receiveVote(new VoteSubmission('01','a',it,Integer.toString(i++)))
        }
        then:
        voteRepository.count() == voterKeys.size()
        println(resultsService.getElectionResults('01'));
    }

    @Transactional
    @Rollback
    def "Everybody votes results"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01','a','0000111122223333',"first"))
        voteReceivingService.receiveVote(new VoteSubmission('01','a','0011223344556677','second'))
        voteReceivingService.receiveVote(new VoteSubmission('01','b','0123456701234567','third'))
        voteReceivingService.receiveVote(new VoteSubmission('01','b','1112223334445556','fourth'))
        voteReceivingService.receiveVote(new VoteSubmission('01','a','9999888877776666','fifth'))
        then:
        voteRepository.count() == voterKeys.size()
        ElectionResults results = resultsService.getElectionResults('01')
        results.sortedResults[0].answer=='a'
        results.sortedResults[0].votes==3
    }


}
