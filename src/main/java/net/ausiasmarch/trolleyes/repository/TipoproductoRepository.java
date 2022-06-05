package net.ausiasmarch.trolleyes.repository;

import net.ausiasmarch.trolleyes.entity.TipoproductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoproductoRepository extends JpaRepository<TipoproductoEntity, Long> {    

    public Page<TipoproductoEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

    public Page<TipoproductoEntity> findById(Long longTipoProducto, Pageable oPageable);

    public Page<TipoproductoEntity> findByIdAndNombreIgnoreCaseContaining(Long longTipoProducto, String strFilter, Pageable oPageable);

}
