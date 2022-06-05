/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.trolleyes.repository;

import java.io.Serializable;
import net.ausiasmarch.trolleyes.entity.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity, Long> extends JpaRepository<T, Long> {
//public class GenericRepository<T extends GenericEntity, ID extends Serializable> extends JpaRepository<T extends GenericEntity, Long>{

}
