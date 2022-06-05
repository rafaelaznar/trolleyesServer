package net.ausiasmarch.trolleyes.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyes.bean.UsuarioBean;
import net.ausiasmarch.trolleyes.entity.UsuarioEntity;
import net.ausiasmarch.trolleyes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @GetMapping("")
    public ResponseEntity<UsuarioEntity> check() {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity != null) {
            return new ResponseEntity<UsuarioEntity>(oSessionUsuarioEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
//        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
//        try {
//            oSessionUsuarioEntity = oUsuarioRepository.findById(oSessionUsuarioEntity.getId()).get();
//        } catch (Exception ex) {
//            oSessionUsuarioEntity = null;
//        }
//        if (oSessionUsuarioEntity == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        } else {
//            return new ResponseEntity<UsuarioEntity>(oSessionUsuarioEntity, HttpStatus.OK);
//        }
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getPassword() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByLoginAndPassword(oUsuarioBean.getLogin(), oUsuarioBean.getPassword());
            if (oUsuarioEntity != null) {
                oHttpSession.setAttribute("usuario", oUsuarioEntity);
                return new ResponseEntity<UsuarioEntity>(oUsuarioEntity, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> logout() {
        oHttpSession.invalidate();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
