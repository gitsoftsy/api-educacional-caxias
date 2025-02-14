package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.PagarmeRecebedorEscola;

@Repository
public interface PagarmeRecebedorEscolaRepository extends JpaRepository<PagarmeRecebedorEscola, Long> {

   List< PagarmeRecebedorEscola> findByEscola_IdEscola(@Param("escolaId") Long escolaId);
   
   List<PagarmeRecebedorEscola> findByIdPagarmeRecebedorEscola(Long idPagarmeRecebedorEscola);
   
   @Procedure(name = "PROC_LISTAR_PARCEIRO_POR_ESCOLA")
	List<Object[]> listarParceiroPorEscola(@Param("P_ID_ESCOLA") Long idEscola); 
	
	@Procedure(name = "PROC_LISTAR_ESCOLA_POR_RECEBEDOR")
	List<Object[]> listarEscolaPorParceiro(@Param("P_ID_RECEBEDOR") Long idRecebedor); 

}
