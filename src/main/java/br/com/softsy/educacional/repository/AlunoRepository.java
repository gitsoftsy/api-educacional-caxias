package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Candidato;
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	@Query("select aluno from Aluno aluno join aluno.conta conta where conta.idConta = :idConta")
    Optional<List<Aluno>> findByConta_IdConta(@Param("idConta") Long idConta);

	   @Procedure(name = "PROC_LISTA_TURMAS_MATRICULA_ALUNO")
	    List<Object[]> listarTurmasDisciplinaAluno(
	            @Param("P_ID_ALUNO") Long idAluno
	    );
	    
	    @Procedure(name = "PROC_PREMATRICULA_ALUNO")
	    List<Object[]> prematriculaAluno(
	            @Param("P_ID_ALUNO") Long idAluno,
	            @Param("P_ID_TIPO_MATRICULA") Long idTipoMatricula,
	            @Param("P_ID_USUARIO") Long idUsuario
	    );
	    
	    List<Aluno> findAllByIdAlunoIn(List<Long> ids);

	    @Query("select aluno from Aluno aluno join aluno.candidato candidato where candidato.idCandidato = :idCandidato")
	    Optional<Aluno> findByCandidato_IdCandidato(@Param("idCandidato") Long idCandidato);

		

	

}
