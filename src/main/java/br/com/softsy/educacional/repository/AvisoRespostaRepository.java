package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AvisoResposta;

@Repository
public interface AvisoRespostaRepository extends JpaRepository<AvisoResposta, Long> {
    
    @Query("select ar from AvisoResposta ar join ar.professor professor where professor.idProfessor = :idProfessor")
    Optional<List<AvisoResposta>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);

    @Query("select ar from AvisoResposta ar join ar.aluno aluno where aluno.idAluno = :idAluno")
    Optional<List<AvisoResposta>> findByAluno_IdAluno(@Param("idAluno") Long idAluno);

    @Query("select ar from AvisoResposta ar join ar.usuario usuario where usuario.idUsuario = :idUsuario")
    Optional<List<AvisoResposta>> findByUsuario_IdUsuario(@Param("idUsuario") Long idUsuario);
}
