package net.ausiasmarch.trolleyes.repository;

import net.ausiasmarch.trolleyes.entity.TipousuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipousuarioRepository extends JpaRepository<TipousuarioEntity, Long> {

    public Page<TipousuarioEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

}
