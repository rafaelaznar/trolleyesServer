package net.ausiasmarch.trolleyes.api;

import net.ausiasmarch.trolleyes.entity.ProductoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.trolleyes.entity.UsuarioEntity;
import net.ausiasmarch.trolleyes.repository.UsuarioRepository;
import net.ausiasmarch.trolleyes.service.AuthService;
import net.ausiasmarch.trolleyes.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    UsuarioService oUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> get(@PathVariable(value = "id") Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        UsuarioEntity oUsuarioEntity111=(UsuarioEntity) oUsuarioService.get(id);
        return new ResponseEntity<UsuarioEntity>(oUsuarioEntity111, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        oAuthService.OnlyAdmins();
        return new ResponseEntity<Long>(oUsuarioService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<UsuarioEntity>> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipousuario", required = false) Long lTipoUsuario) {
        oAuthService.OnlyAdmins();
        return new ResponseEntity<Page<UsuarioEntity>>(oUsuarioService.getPage(oPageable, strFilter, lTipoUsuario), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UsuarioEntity> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {
        oAuthService.OnlyAdmins();
        //oUsuarioService.validate(oNewUsuarioEntity);
        return new ResponseEntity<UsuarioEntity>((UsuarioEntity) oUsuarioService.create(oNewUsuarioEntity), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> update(
            @PathVariable(value = "id") Long id,
            @RequestBody UsuarioEntity oUsuarioEntity) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        oUsuarioService.validate(oUsuarioEntity);
        if (oAuthService.isAdmin()) {
            return new ResponseEntity<UsuarioEntity>(oUsuarioService.update4Admins(id, oUsuarioEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<UsuarioEntity>(oUsuarioService.update4Users(id, oUsuarioEntity), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        oAuthService.OnlyAdmins();
        return new ResponseEntity<Long>(oUsuarioService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<UsuarioEntity> generate() {
        oAuthService.OnlyAdmins();
        return oUsuarioService.generateOne();
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        oAuthService.OnlyAdmins();
        return oUsuarioService.generateSome(amount);
    }

}
