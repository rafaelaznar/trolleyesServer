package net.ausiasmarch.trolleyes.Exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super("ERROR: Field data not valid: " + msg);
    }

}
