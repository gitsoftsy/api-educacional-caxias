package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	
	List<Professor> findByCodigoInep(String codigoInep);
	List<Professor> findByMatricula(String matricula);
	
	@Query("select professor from Professor join professor.conta conta where conta.idConta = :idConta")
    Optional<List<Professor>> findByConta_IdConta(@Param("idConta") Long idConta);

    @Procedure(name = "PROC_LISTA_DISCIPLINAS_PROFESSOR")
    List<Object[]> listarDisciplinasProfessor(
            @Param("P_ID_PROFESSOR") Long idProfessor
    );
    
    @Procedure(name = "PROC_LISTA_ESCOLAS_PROFESSOR")
    List<Object[]> listarEscolasProfessor(
            @Param("P_ID_PROFESSOR") Long idProfessor
    );
    
    @Procedure(name = "PROC_FILTRAR_PROFESSOR")
    List<Object[]> filtrarProfessor(
            @Param("P_CPF") String cpf,
            @Param("P_NOME") String nome,
            @Param("P_MATRICULA") String matricula
    );
	
}
