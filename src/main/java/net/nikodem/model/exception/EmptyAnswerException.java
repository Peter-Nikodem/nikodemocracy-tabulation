package net.nikodem.model.exception;

public class EmptyAnswerException extends NikodemocracyRequestException {


    @Override
    protected String getSpecifiedErrorMessage() {
        return "Chosen answer must not be empty.";
    }
}
