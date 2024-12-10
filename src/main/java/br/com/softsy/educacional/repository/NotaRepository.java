package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long>{

	@Query("select nota from Nota nota join nota.aluno nota where nota.idAluno = :idAluno")
    Optional<List<Nota>> findByAluno_IdAluno(@Param("idAluno") Long idAluno);
	
}
