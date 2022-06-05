package net.ausiasmarch.trolleyes.repository;

import net.ausiasmarch.trolleyes.entity.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    Page<ProductoEntity> findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nombre, String codigo, Pageable oPageable);

    Page<ProductoEntity> findByTipoproductoId(Long tipoproducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM producto WHERE id_tipoproducto = ?1 AND (nombre LIKE  %?2% OR codigo LIKE %?3%)",
            nativeQuery = true)
    Page<ProductoEntity> findByTipoproductoIdAndNombreOrCodigo(long IdTipoproducto, String nombre, String codigo, Pageable oPageable);
}
