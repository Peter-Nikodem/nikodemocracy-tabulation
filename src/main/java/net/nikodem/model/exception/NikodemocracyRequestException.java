package net.nikodem.model.exception;

import net.nikodem.model.dto.ErrorMessage;

public abstract class NikodemocracyRequestException extends RuntimeException {

    abstract protected String getSpecifiedErrorMessage();

    public final ErrorMessage getErrorMessageJson() {
        return new ErrorMessage(getSpecifiedErrorMessage());
    }
}
