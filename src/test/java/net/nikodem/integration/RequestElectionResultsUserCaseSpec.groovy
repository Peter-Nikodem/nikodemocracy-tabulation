package net.nikodem.integration

import net.nikodem.NikodemocracyTabulationApplication
import net.nikodem.model.dto.ResultPlace
import net.nikodem.model.dto.VoteSubmission
import net.nikodem.service.ElectionReceivingService
import net.nikodem.service.ResultsService
import net.nikodem.service.VoteReceivingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static net.nikodem.testdata.ExampleData.ELECTION_EXAMPLE
import static net.nikodem.testdata.ExampleData.FIFTH_VOTER_KEY
import static net.nikodem.testdata.ExampleData.FIRST_VOTER_KEY
import static net.nikodem.testdata.ExampleData.FOURTH_VOTER_KEY
import static net.nikodem.testdata.ExampleData.SECOND_VOTER_KEY
import static net.nikodem.testdata.ExampleData.THIRD_VOTER_KEY

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyTabulationApplication.class)
class RequestElectionResultsUserCaseSpec extends Specification {

    @Autowired
    ResultsService resultsService

    @Autowired
    ElectionReceivingService electionReceivingService

    @Autowired
    VoteReceivingService voteReceivingService

    @Transactional
    @Rollback
    def "Everybody votes results"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        when:
        voteReceivingService.receiveVote(new VoteSubmission('01', 'a', FIRST_VOTER_KEY, "first"))
        voteReceivingService.receiveVote(new VoteSubmission('01', 'a', SECOND_VOTER_KEY, 'second'))
        voteReceivingService.receiveVote(new VoteSubmission('01', 'b', THIRD_VOTER_KEY, 'third'))
        voteReceivingService.receiveVote(new VoteSubmission('01', 'b', FOURTH_VOTER_KEY, 'fourth'))
        voteReceivingService.receiveVote(new VoteSubmission('01', 'a', FIFTH_VOTER_KEY, 'fifth'))
        then:
        ResultPlace[] results = resultsService.getElectionResults('01').sortedResults
        results.length == ELECTION_EXAMPLE.answers.size()
        def winner = results[0]
        winner.answer == 'a'
        winner.votes == 3
    }

    @Transactional
    @Rollback
    def "Results for an election without any votes"() {
        given:
        electionReceivingService.receiveElection(ELECTION_EXAMPLE)
        expect:
        def electionResults = resultsService.getElectionResults('01')
        electionResults.voteProofs.length == 0
        electionResults.sortedResults.length == ELECTION_EXAMPLE.answers.size()
        electionResults.sortedResults.every { it.votes == 0 }
    }

}
