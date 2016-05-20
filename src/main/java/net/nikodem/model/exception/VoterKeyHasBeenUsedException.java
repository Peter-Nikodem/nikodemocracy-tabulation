package net.nikodem.model.exception;

public class VoterKeyHasBeenUsedException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Voter key has already been used.";
    }
}
