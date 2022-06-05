package net.ausiasmarch.trolleyes.Exception;

public class CarritoVacioEnCompraException extends RuntimeException {

    public CarritoVacioEnCompraException() {
        super("ERROR: Carrito vacío en proceso de compra");
    }

}
