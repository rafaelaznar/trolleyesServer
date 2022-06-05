package net.ausiasmarch.trolleyes.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyes.entity.ProductoEntity;
import net.ausiasmarch.trolleyes.entity.UsuarioEntity;
import net.ausiasmarch.trolleyes.repository.ProductoRepository;
import net.ausiasmarch.trolleyes.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    ProductoService oProductoService;

    // /producto/3
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ProductoEntity>(oProductoService.get(id), HttpStatus.OK);
    }

    // producto/count
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oProductoService.count(), HttpStatus.OK);
    }

    // /producto?page=0&size=10&sort=precio,desc&filter=verde&tipoproducto=2
    @GetMapping("")
    public ResponseEntity<Page<ProductoEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter, @RequestParam(name = "tipoproducto", required = false) Long lTipoProducto) {
        Page<ProductoEntity> oPage = null;
        if (lTipoProducto != null) {
            if (strFilter != null) {
                oPage = oProductoRepository.findByTipoproductoIdAndNombreOrCodigo(lTipoProducto, strFilter, strFilter, oPageable);
            } else {
                oPage = oProductoRepository.findByTipoproductoId(lTipoProducto, oPageable);
            }
        } else {
            if (strFilter != null) {
                oPage = oProductoRepository.findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(strFilter, strFilter, oPageable);
            } else {
                oPage = oProductoRepository.findAll(oPageable);
            }
        }
        return new ResponseEntity<Page<ProductoEntity>>(oPage, HttpStatus.OK);
    }

    //CREAR
    // producto/
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ProductoEntity oProductoEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getTipousuario().getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                oProductoEntity.setId(null);

                return new ResponseEntity<ProductoEntity>(oProductoRepository.save(oProductoEntity), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        }

    }

    //UPDATE
    //producto/
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody ProductoEntity oProductoEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getTipousuario().getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                if (oProductoRepository.existsById(oProductoEntity.getId())) {
                    return new ResponseEntity<ProductoEntity>(oProductoRepository.save(oProductoEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);

        }
    }

    // producto/id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getTipousuario().getId() == 1) {

            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                if (oProductoRepository.existsById(id)) {
                    oProductoRepository.deleteById(id);
                    if (oProductoRepository.existsById(id)) {
                        return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<?> genera(@PathVariable(value = "amount") int amount) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario() == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                if (oUsuarioEntity.getTipousuario().getId() == 1) {
                    if (oUsuarioEntity == null) {
                        return new ResponseEntity<>(0L, HttpStatus.UNAUTHORIZED);
                    } else {
                        for (int i = 0; i < amount; i++) {
                            ProductoEntity oProductoEntity;
                            oProductoEntity = oProductoService.generateRandomProduct();
                            oProductoRepository.save(oProductoEntity);
                        }
                        return new ResponseEntity<>(oProductoRepository.count(), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(0L, HttpStatus.UNAUTHORIZED);
                }
            }
        }
    }

}
