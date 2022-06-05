package net.ausiasmarch.trolleyes.repository;

import java.util.List;
import javax.transaction.Transactional;
import net.ausiasmarch.trolleyes.entity.CarritoEntity;
import net.ausiasmarch.trolleyes.entity.ProductoEntity;
import net.ausiasmarch.trolleyes.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

	Page<CarritoEntity> findByProductoId(Long id_Producto, Pageable oPageable);

	Page<CarritoEntity> findByUsuarioId(Long id_Usuario, Pageable oPageable);
        
	List<CarritoEntity> findByUsuarioId(Long id_Usuario);        
        
	long countByUsuarioId(Long id_Usuario);
                
	Page<CarritoEntity> findAllByUsuario(UsuarioEntity usuario, Pageable oPageable);

	Page<CarritoEntity> findAllByIdIgnoreCaseContainingOrCantidadIgnoreCaseContainingOrPrecioIgnoreCaseContainingOrProductoIgnoreCaseContainingOrUsuarioIgnoreCaseContaining(
			String id, String cantidad, String precio, String producto, String usuario, Pageable oPageable);

	CarritoEntity findByUsuarioAndProducto(UsuarioEntity usuario, ProductoEntity producto);

	@Transactional
	int deleteAllByUsuario(UsuarioEntity usuario);

	@Transactional
	int deleteByUsuarioAndProducto(UsuarioEntity usuario, ProductoEntity producto);

}
