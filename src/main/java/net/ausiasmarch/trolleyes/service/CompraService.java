package net.ausiasmarch.trolleyes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.ausiasmarch.trolleyes.entity.CompraEntity;
import net.ausiasmarch.trolleyes.entity.FacturaEntity;
import net.ausiasmarch.trolleyes.entity.ProductoEntity;
import net.ausiasmarch.trolleyes.repository.FacturaRepository;
import net.ausiasmarch.trolleyes.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    FacturaRepository oFacturaRepository;

    @Autowired
    FacturaService oFacturaService;

    @Autowired
    ProductoService oProductoService;
       
    public CompraEntity generateRandomCompra() {
        CompraEntity oCompraEntity = new CompraEntity();
        oCompraEntity.setCantidad(generateCantidad(1, 200));
        oCompraEntity.setPrecio(generatePrecio(0.99, 99.99));
        oCompraEntity.setFecha(getRadomDate());
        oCompraEntity.setDescuento_usuario(generateDescuentoUsuario(1, 10));
        oCompraEntity.setDescuento_producto(generateDescuentoProducto(1, 60));
        //oCompraEntity.setFactura(generateFactura());
        //oCompraEntity.setProducto(generateProducto());
        oCompraEntity.setFactura(oFacturaService.getRandomFactura());
        oCompraEntity.setProducto(oProductoService.getRandomProducto());

        return oCompraEntity;
    }

    private int generateCantidad(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    private double generatePrecio(double minValue, double maxValue) {
        return Math.round(ThreadLocalRandom.current().nextDouble(minValue, maxValue) * 100d) / 100d;
    }

    private LocalDateTime getRadomDate() {
        int randomSeconds = new Random().nextInt(3600 * 24);
        LocalDateTime anyTime = LocalDateTime.now().minusSeconds(randomSeconds);
        return anyTime;
    }

    private int generateDescuentoUsuario(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    private int generateDescuentoProducto(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }


    

    private FacturaEntity generateFactura() {
        List<FacturaEntity> list = oFacturaRepository.findAll();

        int randomNumber = generateNumber(0, list.size());
        FacturaEntity value = list.get(randomNumber);
        return value;
    }

    private ProductoEntity generateProducto() {
        List<ProductoEntity> list = oProductoRepository.findAll();

        int randomNumber = generateNumber(0, list.size());
        ProductoEntity value = list.get(randomNumber);
        return value;
    }

    private int generateNumber(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

}
