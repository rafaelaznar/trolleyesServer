/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.trolleyes.service;

import java.io.Serializable;
import net.ausiasmarch.trolleyes.Exception.ResourceNotFoundException;
import net.ausiasmarch.trolleyes.entity.GenericEntity;

/**
 *
 * @author rafa
 */
public interface GenericServiceInterface<GenericEntity,Long>{
    public GenericEntity get(long id) throws ResourceNotFoundException;
}
