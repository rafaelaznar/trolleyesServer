package net.ausiasmarch.trolleyes.Exception;

public class ResourceNotModifiedException extends RuntimeException {

    public ResourceNotModifiedException(String msg) {
        super("ERROR: Resource not modified: " + msg);
    }

}
