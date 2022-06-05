/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.trolleyes.service;

import javax.transaction.Transactional;
import net.ausiasmarch.trolleyes.Exception.ResourceNotFoundException;
import net.ausiasmarch.trolleyes.Exception.ResourceNotModifiedException;
import net.ausiasmarch.trolleyes.entity.GenericEntity;
import net.ausiasmarch.trolleyes.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GenericServiceImplementation implements GenericServiceInterface {

    private GenericRepository<GenericEntity, Long> oGenericRepository;

    //@Autowired
    public GenericServiceImplementation(GenericRepository oGenericRepository) {
        this.oGenericRepository = oGenericRepository;
    }

    public GenericEntity get(long id) throws ResourceNotFoundException {
        if (oGenericRepository.existsById(id)) {
            return oGenericRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("can't find id=" + id);
        }
    }

    public long count() {
        return oGenericRepository.count();
    }

    public Page<GenericEntity> getPage(Pageable oPageable) {
        return oGenericRepository.findAll(oPageable);
    }

    public void validate(GenericEntity oGenericEntity) {
    }

    public GenericEntity create(GenericEntity oGenericEntity) {
        this.validate(oGenericEntity);
        oGenericEntity.setId(0L);
        return oGenericRepository.save(oGenericEntity);
    }

    public GenericEntity update(Long id, GenericEntity oGenericEntity) throws ResourceNotFoundException {
        if (oGenericRepository.existsById(id)) {
            return oGenericRepository.save(oGenericEntity);
        } else {
            throw new ResourceNotFoundException("id not found");
        }
    }

    public Long delete(Long id) {
        if (oGenericRepository.existsById(id)) {
            oGenericRepository.deleteById(id);
            if (oGenericRepository.existsById(id)) {
                throw new ResourceNotModifiedException("Can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

}
