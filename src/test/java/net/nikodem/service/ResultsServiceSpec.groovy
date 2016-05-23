package net.nikodem.service

import net.nikodem.model.entity.AnswerEntity
import net.nikodem.model.entity.ElectionEntity
import net.nikodem.model.entity.VoteEntity
import net.nikodem.model.entity.VoterKeyEntity
import net.nikodem.model.exception.ElectionDoesNotExistException
import net.nikodem.repository.AnswerRepository
import net.nikodem.repository.ElectionRepository
import net.nikodem.repository.VoteRepository
import spock.lang.Specification

import static net.nikodem.testdata.ExampleData.ELECTION_EXAMPLE


class ResultsServiceSpec extends Specification {

    ResultsService resultsService = new ResultsService()

    ElectionRepository electionRepositoryMock = Mock(ElectionRepository)
    AnswerRepository answerRepositoryMock = Mock(AnswerRepository)
    VoteRepository voteRepositoryMock = Mock(VoteRepository)

    def setup() {
        resultsService.electionRepository = electionRepositoryMock
        resultsService.answerRepository = answerRepositoryMock
        resultsService.voteRepository = voteRepositoryMock
    }

    def "Nonexistent election causes exception"() {
        given:
        1 * electionRepositoryMock.findByElectionId('nonexistent election') >> Optional.empty()
        0 * answerRepositoryMock._(*_)
        0 * voteRepositoryMock._(*_)
        when:
        resultsService.getElectionResults('nonexistent election')
        then:
        thrown(ElectionDoesNotExistException)
    }

    def "Retrieving results with zero votes"(){
        given:
        1 * electionRepositoryMock.findByElectionId(_) >> Optional.of(new ElectionEntity(ELECTION_EXAMPLE.electionId,ELECTION_EXAMPLE.question))
        1 * answerRepositoryMock.findAnswerTextsByElection(_) >> ELECTION_EXAMPLE.answers
        1 * voteRepositoryMock.findByVoterKeyElection(_) >> []
        when:
        def results = resultsService.getElectionResults(ELECTION_EXAMPLE.electionId)
        then:
        results.electionId == ELECTION_EXAMPLE.electionId
        results.question == ELECTION_EXAMPLE.question
        results.sortedResults.each { it.votes == 0}
        results.voteProofs.length == 0
    }
}
