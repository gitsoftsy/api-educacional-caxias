package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.model.Nota;
import br.com.softsy.educacional.model.NotaLog;

public interface NotaLogRepository extends JpaRepository<NotaLog, Long>{
	
	@Query("select notaLog from NotaLog notaLog join notaLog.aluno aluno where aluno.idAluno = :idAluno")
	Optional<List<NotaLog>> findByAluno_IdAluno(@Param("idAluno") Long idAluno);
	
	 @Query("select notaLog from NotaLog notaLog join notaLog.professor professor where professor.idProfessor = :idProfessor")
	 Optional<List<NotaLog>> findByProfessor_IdProfessor(@Param("idProfessor") Long idProfessor);

	 @Query("select notaLog from NotaLog notaLog join notaLog.usuario usuario where usuario.idUsuario = :idUsuario")
	 Optional<List<NotaLog>> findByUsuario_IdUsuario(@Param("idUsuario") Long idUsuario);
	 
	  Optional<List<NotaLog>> findByProva_IdProva(Long idProva);
	  Optional<List<NotaLog>> findByNota_IdNota(Long idNota);

}
