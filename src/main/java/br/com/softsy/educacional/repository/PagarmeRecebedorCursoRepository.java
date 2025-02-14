package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.PagarmeRecebedorCurso;

@Repository
public interface PagarmeRecebedorCursoRepository extends JpaRepository<PagarmeRecebedorCurso, Long>{
	
	@Procedure(name = "PROC_LISTAR_PARCEIRO_POR_CURSO")
	List<Object[]> listarParceiroPorCurso(@Param("P_ID_CURSO") Long idCurso); 
	
	@Procedure(name = "PROC_LISTAR_CURSO_POR_RECEBEDOR")
	List<Object[]> listarCursoPorParceiro(@Param("P_ID_RECEBEDOR") Long idRecebedor); 


}
