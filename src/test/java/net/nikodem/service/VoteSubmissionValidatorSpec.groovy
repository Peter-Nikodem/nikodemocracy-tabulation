package net.nikodem.service

import net.nikodem.model.exception.*
import net.nikodem.model.json.VoteSubmission
import net.nikodem.repository.AnswerRepository
import net.nikodem.repository.ElectionRepository
import net.nikodem.repository.VoteRepository
import net.nikodem.repository.VoterKeyRepository
import spock.lang.Specification

class VoteSubmissionValidatorSpec extends Specification {

    VoteSubmissionValidator validator

    ElectionRepository electionRepositoryMock
    VoterKeyRepository voterKeyRepositoryMock
    AnswerRepository answerRepositoryMock
    VoteRepository voteRepositoryMock
    VoteSubmission SYNTACTICALLY_VALID_SUBMISSION = new VoteSubmission('01', 'a', '0123456701234567', '1234')

    def setup() {
        voterKeyRepositoryMock = Mock(VoterKeyRepository)
        answerRepositoryMock = Mock(AnswerRepository)
        voteRepositoryMock = Mock(VoteRepository)
        electionRepositoryMock = Mock(ElectionRepository)
        validator = new VoteSubmissionValidator()
        validator.setVoterKeyRepository(voterKeyRepositoryMock)
        validator.setAnswerRepository(answerRepositoryMock)
        validator.setVoteRepository(voteRepositoryMock)
    }


    def "Mock that voterKey has already been used"(boolean isTrue) {
        _ * voteRepositoryMock.existsByElectionElectionIdAndVoterKeyVoterKey(*_) >> isTrue
    }

    def "Mock that chosen answer exists"(boolean isTrue) {
        _ * answerRepositoryMock.existsByElectionIdAndAnswerText(*_) >> isTrue
    }

    def "Mock that voterKey is authorized"(boolean isTrue) {
        _ * voterKeyRepositoryMock.existsByElectionIdAndVoterKey(*_) >> isTrue
    }


    def "ElectionId must not be empty"() {
        when:
        validator.validate(submission)
        then:
        thrown(EmptyElectionIdException)
        where:
        submission << [
                new VoteSubmission("", "", "", ""),
                new VoteSubmission(null, "", "", "")
        ]
    }

    def "Answer must not be empty"() {
        when:
        validator.validate(submission)
        then:
        thrown(EmptyAnswerException)
        where:
        submission << [
                new VoteSubmission("01", "", "", ""),
                new VoteSubmission("01", null, "", "")
        ]
    }

    def "VoterKey must not be empty"() {
        when:
        validator.validate(submission)
        then:
        thrown(EmptyVoterKeyException)
        where:
        submission << [
                new VoteSubmission("01", "a", "", ""),
                new VoteSubmission("01", "a", null, "")
        ]
    }

    def "VoteKey can be empty"() {
        given:
        "Mock that voterKey is authorized"(true)
        "Mock that chosen answer exists"(true)
        "Mock that voterKey has already been used"(false)
        expect:
        validator.validate(submission)
        where:
        submission << [
                new VoteSubmission("01", "a", "fiosd", ""),
                new VoteSubmission("01", "a", "fdjsi", null)
        ]
    }

    def "Election must exist and voterKey must be eligible"() {
        given:
        "Mock that voterKey is authorized"(false)
        when:
        validator.validate(SYNTACTICALLY_VALID_SUBMISSION)
        then:
        thrown(UnauthorizedVoteSubmissionException)
    }

    def "Chosen answer must exist"() {
        given:
        "Mock that voterKey is authorized"(true)
        "Mock that chosen answer exists"(false)
        when:
        validator.validate(SYNTACTICALLY_VALID_SUBMISSION)
        then:
        thrown(AnswerDoesNotExistException)
    }

    def "VoterKey must have not been already used for voting"() {
        given:
        "Mock that voterKey is authorized"(true)
        "Mock that chosen answer exists"(true)
        "Mock that voterKey has already been used"(true)
        when:
        validator.validate(SYNTACTICALLY_VALID_SUBMISSION)
        then:
        thrown(VoterKeyHasBeenUsedException)
    }

    def "Everything ok"() {
        given:
        "Mock that voterKey is authorized"(true)
        "Mock that chosen answer exists"(true)
        "Mock that voterKey has already been used"(false)
        expect:
        validator.validate(SYNTACTICALLY_VALID_SUBMISSION)
    }


}
