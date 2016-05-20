package net.nikodem.model.exception;

public class UnauthorizedVoteSubmissionException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Unauthorized vote submission.";
    }
}
