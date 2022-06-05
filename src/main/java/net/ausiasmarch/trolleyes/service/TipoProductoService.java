package net.ausiasmarch.trolleyes.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.trolleyes.entity.TipoproductoEntity;
import net.ausiasmarch.trolleyes.helper.RandomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.ausiasmarch.trolleyes.repository.TipoproductoRepository;

@Service
public class TipoProductoService {

    @Autowired
    TipoproductoRepository oTipoProductoRepository;

    private final String[] TIPO = {"Productos", "Artículos", "Utilidades", "Herramientas", "Manufacturas", "Elaboraciones", "Mercancías"};
    private final String[] CARATERISTICA = {"artesanos", "de lujo", "económicos", "de segunda mano", "estándar", "retro", "plegables", "abatibles", "de gran tonelage", "ortopédicos", "de alta resistencia"};
    private final String[] UTILIDAD = {"para el hogar", "para el jardin", "para la restauracion", "para la oficina", "para el camping", "para colegios", "para prisiones", "para hospitales", "para cines y teatros"};

    public List<TipoproductoEntity> generateAllTipoProductoList() {
        List<TipoproductoEntity> TipoProdList = new ArrayList<>();
        for (int i = 0; i < TIPO.length; i++) {
            for (int j = 0; j < CARATERISTICA.length; j++) {
                for (int k = 0; k < UTILIDAD.length; k++) {
                    TipoProdList.add(this.generateTipoProducto(i, j, k));
                }
            }
        }
        return TipoProdList;
    }

    public TipoproductoEntity generateTipoProducto() {
        String nombre = TIPO[RandomHelper.getRandomInt(0, TIPO.length - 1)] + " " + CARATERISTICA[RandomHelper.getRandomInt(0, CARATERISTICA.length - 1)] + " " + UTILIDAD[RandomHelper.getRandomInt(0, UTILIDAD.length - 1)];
        TipoproductoEntity oTipoProductoEntity = new TipoproductoEntity();
        oTipoProductoEntity.setNombre(nombre);
        return oTipoProductoEntity;
    }

    private TipoproductoEntity generateTipoProducto(int i, int j, int k) {
        String nombre = TIPO[i] + " " + CARATERISTICA[j] + " " + UTILIDAD[k];
        TipoproductoEntity oTipoProductoEntity = new TipoproductoEntity();
        oTipoProductoEntity.setNombre(nombre);
        return oTipoProductoEntity;
    }

    public TipoproductoEntity getRandomTipoProducto() {
        TipoproductoEntity oTipoProductoEntity = null;
        int iPosicion = RandomHelper.getRandomInt(0, (int) oTipoProductoRepository.count()-1);
        Pageable oPageable = PageRequest.of(iPosicion, 1);
        Page<TipoproductoEntity> tipoProductoPage = oTipoProductoRepository.findAll(oPageable);
        List<TipoproductoEntity> tipoProductoList = tipoProductoPage.getContent();
        oTipoProductoEntity = oTipoProductoRepository.getById(tipoProductoList.get(0).getId());
        return oTipoProductoEntity;
    }

}
