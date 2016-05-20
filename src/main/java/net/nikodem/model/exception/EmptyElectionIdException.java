package net.nikodem.model.exception;

public class EmptyElectionIdException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "ElectionId must not be empty.";
    }
}
