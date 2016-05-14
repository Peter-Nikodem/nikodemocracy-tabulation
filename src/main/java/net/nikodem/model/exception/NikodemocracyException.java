package net.nikodem.model.exception;

import net.nikodem.model.json.ErrorMessage;

public class NikodemocracyException extends RuntimeException {
    public ErrorMessage getErrorMessageJson() {
        return new ErrorMessage(" ");
    }
}
