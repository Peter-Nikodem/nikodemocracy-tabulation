package net.nikodem.model.exception;

public class EmptyVoterKeyException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "VoterKey must not be empty";
    }
}
