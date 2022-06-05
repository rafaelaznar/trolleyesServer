package net.ausiasmarch.trolleyes.repository;

import net.ausiasmarch.trolleyes.entity.CompraEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {

    @Query(
            value = "SELECT * FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = ?1)",
            nativeQuery = true)
    Page<CompraEntity> findAllUsuario(long idusuario, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_factura = ?1 AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%)",
            nativeQuery = true)
    Page<CompraEntity> findByFacturaIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProducto(long IdFactura, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_factura = ?1 AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%) AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?7)",
            nativeQuery = true)
    Page<CompraEntity> findByFacturaIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProductoUsuario(long IdFactura, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Long idusuario, Pageable oPageable);

    Page<CompraEntity> findByFacturaId(Long factura, Pageable oPageable);

    @Query(value = "SELECT * FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = ?1) AND id_factura = ?2", nativeQuery = true)
    Page<CompraEntity> findByFacturaIdUsuario(Long id_usuario, Long id_factura, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_producto = ?1 AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%)",
            nativeQuery = true)
    Page<CompraEntity> findByProductoIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProducto(long IdProducto, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_producto = ?1 AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%) AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?7)",
            nativeQuery = true)
    Page<CompraEntity> findByProductoIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProductoUsuario(long IdProducto, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Long idusuario, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_producto = ?1 AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?2)",
            nativeQuery = true)
    Page<CompraEntity> findByProductoIdUsuario(Long producto, Long idusuario, Pageable oPageable);

    Page<CompraEntity> findByProductoId(Long producto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id LIKE  %?1% OR cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%",
            nativeQuery = true)
    Page<CompraEntity> findByIdContain(String IdCompra, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id LIKE  %?1% OR cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6% AND where id_factura IN (SELECT id FROM factura WHERE id_usuario = ?7) ",
            nativeQuery = true)
    Page<CompraEntity> findByIdContainUsuario(String IdCompra, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Long idusuario, Pageable oPageable);

    Page<CompraEntity> findByCantidadIgnoreCaseContainingOrPrecioIgnoreCaseContainingOrFechaIgnoreCaseContaining(String cantidad, String precio, String fecha, Pageable oPageable);

    @Query(value = "SELECT * FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = :id_usuario)", nativeQuery = true)
    Page<CompraEntity> findByCompraIdUsuarioPage(Long id_usuario, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = :id_usuario)", nativeQuery = true)
    Long findByCompraIdUsuarioCount(Long id_usuario);

    @Query(value = "SELECT * FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = :id_usuario) AND id = :id_compra", nativeQuery = true)
    CompraEntity findByCompraIdUsuarioView(Long id_usuario, Long id_compra);
}
