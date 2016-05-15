package net.nikodem.service

import net.nikodem.model.exception.UnauthorizedVoteSubmissionException
import net.nikodem.model.json.VoteSubmission
import spock.lang.Specification


class VoteReceivingServiceSpec extends Specification {
    VoteReceivingService service
    VoteSubmissionValidator validatorMock
    ElectionCompletionChecker electionCompletionCheckerMock
    VoteSaver voteSaverMock

    VoteSubmission voteSubmissionExample = new VoteSubmission("01","a","12345671234567",null)

    def setup() {
        validatorMock = Mock(VoteSubmissionValidator)
        voteSaverMock = Mock(VoteSaver)
        electionCompletionCheckerMock = Mock(ElectionCompletionChecker)

        service = new VoteReceivingService()
        service.voteSubmissionValidator = validatorMock
        service.voteSaver = voteSaverMock
        service.electionCompletionChecker = electionCompletionCheckerMock
    }

    def "Successful vote"(){
        when:
        service.receiveVote(voteSubmissionExample)
        then:
        1 * validatorMock.validate(_)
        1 * voteSaverMock.save(_)
        1 * electionCompletionCheckerMock.checkIfFinished(_)
    }

    def "Unsuccessful vote"(){
        given:
        1 * validatorMock.validate(_) >> {throw new UnauthorizedVoteSubmissionException()}
        when:
        service.receiveVote(voteSubmissionExample)
        then:
        0 * voteSaverMock.save(_)
        0 * electionCompletionCheckerMock.checkIfFinished(_)
        thrown(UnauthorizedVoteSubmissionException)
    }

}
