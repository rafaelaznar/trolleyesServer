package net.ausiasmarch.trolleyes.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.trolleyes.entity.TipousuarioEntity;
import net.ausiasmarch.trolleyes.service.TipoUsuarioService;
import net.ausiasmarch.trolleyes.repository.TipousuarioRepository;
import net.ausiasmarch.trolleyes.service.AuthService;

@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioController {

    @Autowired
    TipousuarioRepository oTipoUsuarioRepository;

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    AuthService oAuthService;

    @Autowired
    TipoUsuarioService oTipousuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<TipousuarioEntity> get(@PathVariable(value = "id") Long id) {
        return oTipousuarioService.get(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TipousuarioEntity>> all() {
        return new ResponseEntity<List<TipousuarioEntity>>(oTipoUsuarioRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipoUsuarioRepository.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TipousuarioEntity>> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return oTipousuarioService.getPage(oPageable, strFilter);
    }

    @PutMapping("")
    public ResponseEntity<TipousuarioEntity> update(@RequestBody TipousuarioEntity oTipoUsuarioEntity) {
        oAuthService.OnlyAdmins();
        return oTipousuarioService.update(oTipoUsuarioEntity);
    }

    @PostMapping("/generate")
    public ResponseEntity<Long> generate() {
        oAuthService.OnlyAdmins();
        return oTipousuarioService.generate();
    }

}
