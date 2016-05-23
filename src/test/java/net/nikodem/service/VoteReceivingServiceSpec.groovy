package net.nikodem.service

import net.nikodem.model.exception.UnauthorizedVoteSubmissionException
import net.nikodem.model.dto.VoteSubmission
import spock.lang.Specification


class VoteReceivingServiceSpec extends Specification {
    VoteReceivingService service
    VoteSubmissionValidator validatorMock
    VoteSaver voteSaverMock

    VoteSubmission voteSubmissionExample = new VoteSubmission("01","a","12345671234567",null)

    def setup() {
        validatorMock = Mock(VoteSubmissionValidator)
        voteSaverMock = Mock(VoteSaver)

        service = new VoteReceivingService()
        service.voteSubmissionValidator = validatorMock
        service.voteSaver = voteSaverMock
    }

    def "Successful vote"(){
        when:
        service.receiveVote(voteSubmissionExample)
        then:
        1 * validatorMock.validate(_)
        1 * voteSaverMock.save(_)
    }

    def "Unsuccessful vote"(){
        given:
        1 * validatorMock.validate(_) >> {throw new UnauthorizedVoteSubmissionException()}
        when:
        service.receiveVote(voteSubmissionExample)
        then:
        0 * voteSaverMock.save(_)
        thrown(UnauthorizedVoteSubmissionException)
    }

}
