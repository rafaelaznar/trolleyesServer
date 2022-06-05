package net.ausiasmarch.trolleyes.Exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super("ERROR: Resource not found: " + msg);
    }

}
