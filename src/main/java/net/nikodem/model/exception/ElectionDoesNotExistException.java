package net.nikodem.model.exception;

public class ElectionDoesNotExistException extends NikodemocracyRequestException {


    private String electionId;

    public ElectionDoesNotExistException(String electionId) {
        this.electionId = electionId;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Election (" + electionId + ") does not exist.";
    }
}
