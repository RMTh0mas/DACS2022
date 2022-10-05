package br.univille.coredacs2022.repository;

import org.springframework.stereotype.Repository;

import br.univille.coredacs2022.entity.Procedimento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento,Long> {
    
    List<Procedimento> findByDescricaoIgnoreCaseContaining(@Param("descricao") String descricao);

}
