package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.AvisoDestinatario;

@Repository
public interface AvisoDestinatarioRepository extends JpaRepository<AvisoDestinatario, Long>{

	@Query("select avisoDestinatario from AvisoDestinatario join avisoDestinatario.aluno aluno where aluno.idAluno = :idAluno")
    Optional<List<AvisoDestinatario>> findByAluno_IdAluno(@Param("idAluno") Long idAluno);
	
}
