package net.ausiasmarch.trolleyes.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyes.entity.TipoproductoEntity;
import net.ausiasmarch.trolleyes.entity.UsuarioEntity;
import net.ausiasmarch.trolleyes.helper.ValidationHelper;
import net.ausiasmarch.trolleyes.service.TipoProductoService;
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
import net.ausiasmarch.trolleyes.repository.TipoproductoRepository;

@RestController
@RequestMapping("/tipoproducto")
public class TipoProductoController {

    @Autowired
    TipoproductoRepository oTipoProductoRepository;

    @Autowired
    TipoProductoService oTipoProductoService;

    @Autowired
    HttpSession oHttpSession;

    @GetMapping("/{id}")
    public ResponseEntity<TipoproductoEntity> get(@PathVariable(value = "id") Long id) {
        if (oTipoProductoRepository.existsById(id)) {
            return new ResponseEntity<TipoproductoEntity>(oTipoProductoRepository.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<Page<TipoproductoEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        Page<TipoproductoEntity> oPage = null;
        if (strFilter != null) {
            oPage = oTipoProductoRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        } else {
            oPage = oTipoProductoRepository.findAll(oPageable);
        }
        return new ResponseEntity<>(oPage, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipoProductoRepository.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                if (oTipoProductoRepository.existsById(id)) {
                    oTipoProductoRepository.deleteById(id);
                    if (oTipoProductoRepository.existsById(id)) {
                        return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TipoproductoEntity oTipoProductoEntity) {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                oTipoProductoEntity.setId(null);
                if (!ValidationHelper.validateDescripcion(oTipoProductoEntity.getNombre())) {
                    return new ResponseEntity<>("descripción invalid", HttpStatus.NOT_MODIFIED);
                } else {
                    return new ResponseEntity<TipoproductoEntity>(oTipoProductoRepository.save(oTipoProductoEntity), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody TipoproductoEntity oTipoProductoEntity) {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                if (oTipoProductoRepository.existsById(oTipoProductoEntity.getId())) {
                    if (!ValidationHelper.validateDescripcion(oTipoProductoEntity.getNombre())) {
                        return new ResponseEntity<>("descripción invalid", HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<TipoproductoEntity>(oTipoProductoRepository.save(oTipoProductoEntity), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<?> generateAmount(@PathVariable(value = "amount") int amount) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity.getTipousuario().getId() == 1) {
            if (oUsuarioSessionEntity == null) {
                return new ResponseEntity<>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                for (int i = 0; i < amount; i++) {
                    TipoproductoEntity oTipoProductoEntity = oTipoProductoService.generateTipoProducto();
                    oTipoProductoRepository.save(oTipoProductoEntity);
                }
                return new ResponseEntity<>(oTipoProductoRepository.count(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(0L, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generate() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == 1) {
                List<TipoproductoEntity> ListaTipoProd = oTipoProductoService.generateAllTipoProductoList();
                for (int i = 0; i < ListaTipoProd.size(); i++) {
                    oTipoProductoRepository.save(ListaTipoProd.get(i));
                }
                return new ResponseEntity<>(oTipoProductoRepository.count(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }

}
