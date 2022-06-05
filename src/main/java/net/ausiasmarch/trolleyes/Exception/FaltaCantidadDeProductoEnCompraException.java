package net.ausiasmarch.trolleyes.Exception;

public class FaltaCantidadDeProductoEnCompraException extends RuntimeException {

    public FaltaCantidadDeProductoEnCompraException(long id_producto) {
        super("ERROR: Falta Cantidad de producto " + id_producto + " en el proceso de compra");
    }

}
