package net.ausiasmarch.trolleyes.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyes.Exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import net.ausiasmarch.trolleyes.entity.TipousuarioEntity;
import net.ausiasmarch.trolleyes.entity.UsuarioEntity;
import net.ausiasmarch.trolleyes.helper.TipoUsuarioHelper;
import net.ausiasmarch.trolleyes.repository.TipousuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TipoUsuarioService {

    @Autowired
    TipousuarioRepository oTipoUsuarioRepository;

    @Autowired
    HttpSession oHttpSession;

    public List<TipousuarioEntity> generateUsersType() {
        List<TipousuarioEntity> usersTypeList = new ArrayList<>();
        usersTypeList.add(new TipousuarioEntity(1L, "administrador"));
        usersTypeList.add(new TipousuarioEntity(2L, "usuario"));

        return usersTypeList;
    }

    public ResponseEntity<TipousuarioEntity> get(Long id) {
        if (id == null || !(oTipoUsuarioRepository.existsById(id))) {
            throw new ResourceNotFoundException("id not found");
        } else {
            return new ResponseEntity<TipousuarioEntity>(oTipoUsuarioRepository.getById(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<Page<TipousuarioEntity>> getPage(Pageable oPageable, String strFilter) {
        Page<TipousuarioEntity> oPage = null;
        if (strFilter != null) {
            oPage = oTipoUsuarioRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        } else {
            oPage = oTipoUsuarioRepository.findAll(oPageable);
        }
        return new ResponseEntity<Page<TipousuarioEntity>>(oPage, HttpStatus.OK);
    }

    public ResponseEntity<TipousuarioEntity> update(TipousuarioEntity oTipoUsuarioEntity) {
        if (oTipoUsuarioEntity.getId() == null) {
            throw new ResourceNotFoundException("id not found");
        } else {
            if (!oTipoUsuarioRepository.existsById(oTipoUsuarioEntity.getId())) {
                throw new ResourceNotFoundException("id not found");
            } else {
                return new ResponseEntity<TipousuarioEntity>(oTipoUsuarioRepository.save(oTipoUsuarioEntity), HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<Long> generate() {
        List<TipousuarioEntity> usersTypeList = generateUsersType();

        for (int i = usersTypeList.size() - 1; i >= 0; i--) {
            oTipoUsuarioRepository.save(usersTypeList.get(i));
        }
        return new ResponseEntity<Long>(oTipoUsuarioRepository.count(), HttpStatus.OK);

    }
}
