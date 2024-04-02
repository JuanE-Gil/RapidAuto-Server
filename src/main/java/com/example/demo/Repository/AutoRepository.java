package com.example.demo.Repository;

import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.CategoriaEntity;
import com.example.demo.Model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<AutoEntity, Integer> {

    List<AutoEntity> findByEstatus(boolean estatus);

    //El "ContainigIgnoreCase" ayuda a obtener el nombre que estamos buscando eliminando minusculas y mayusculas y asi la palabra no este completa
    List<AutoEntity> findByModeloContainingIgnoreCase(String modeloAuto);

    List<AutoEntity> findByIdusuarioAndEstatus(UsuarioEntity id, boolean estatus);

    List<AutoEntity> findByIdCategoriaAndAndEstatus(CategoriaEntity idcategoria, boolean estatus);

    List<AutoEntity> findByMarcaAndEstatus(String marca, boolean estatus);

    List<AutoEntity> findByEstadoAndEstatus(String estado, boolean estatus);



    @Modifying
    @Query("UPDATE AutoEntity e SET e.estatus = :estatus WHERE e.idAuto = :IdAuto")
    void actualizarEstatus(@Param("IdAuto") Integer idAuto, @Param("estatus") boolean estatus);

}
