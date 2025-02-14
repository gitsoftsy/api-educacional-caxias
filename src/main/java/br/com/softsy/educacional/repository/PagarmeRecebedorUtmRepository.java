package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.PagarmeRecebedorEscola;
import br.com.softsy.educacional.model.PagarmeRecebedorUtm;

@Repository
public interface PagarmeRecebedorUtmRepository extends JpaRepository<PagarmeRecebedorUtm, Long> {

	List<PagarmeRecebedorEscola> findByUtm_IdUtm(@Param("utmId") Long utmId);

	List<PagarmeRecebedorEscola> findByIdPagarmeRecebedorUtm(Long idPagarmeRecebedorUtm);

	   @Procedure(name = "PROC_LISTAR_PARCEIRO_POR_UTM")
		List<Object[]> listarParceiroPorUtm(@Param("P_ID_UTM") Long idUtm); 
		
		@Procedure(name = "PROC_LISTAR_UTM_POR_PARCEIRO")
		List<Object[]> listarUtmPorParceiro(@Param("P_ID_RECEBEDOR") Long idRecebedor);

}
