package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

	List<Disciplina> findByDisciplina(String disciplina);

	@Query("select disciplina from Disciplina disciplina join disciplina.escola escola where escola.idEscola = :idEscola")
	Optional<List<Disciplina>> findByEscola_IdEscola(@Param("idEscola") Long idEscola);

}
