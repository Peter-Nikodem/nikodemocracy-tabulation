package net.nikodem.model.exception;

public class AnswerDoesNotExistException extends NikodemocracyRequestException {

    private final String chosenAnswer;

    public AnswerDoesNotExistException(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Chosen answer (" + chosenAnswer + ") does not exist.";
    }
}
