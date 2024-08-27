package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Candidato;


@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
	
	List<Candidato> findByCandidato(String candidato);
	
    @Procedure(name = "PROC_OBTEM_STEP_CANDIDATO")
    List<Object[]> obtemStepCandidato(
            @Param("P_ID_CANDIDATO") Long idCandidato,
            @Param("P_CANDIDATO") String candidato,
            @Param("P_RG_NUM") String rgNum,
            @Param("P_CPF_NUM") String cpfNum,
            @Param("P_CERT_NASC") String certNasc,
            @Param("P_CERT_CASAMENTO") String certCasamento
    );
    
    @Procedure(name = "PROC_LISTA_DADOS_CANDIDATO_FINAL_RESERVA")
    List<Object[]> listaDadosCandidatoFinal(
            @Param("P_CANDIDATO") String candidato
    );
    
    
    @Procedure(name = "PROC_LISTA_RESERVA_DE_VAGAS")
    List<Object[]> listarReservas(
            @Param("P_ID_USUARIO") Long idUsuario
    );
    
    @Procedure(name = "PROC_LISTA_RESERVA_DE_VAGAS_FILTRO_DOC")
    List<Object[]> listarReservasPorDoc(
            @Param("P_ID_CONTA") Long idConta,
            @Param("P_ID_ESCOLA") Long idEscola,
            @Param("P_RG_NUM") String rgNum,
            @Param("P_CPF_NUM") String cpfNum,
            @Param("P_CERT_NASC") String certNasc,
            @Param("P_CERT_CASAMENTO") String certCasamento
    );
    
    
    @Procedure(name = "PROC_LISTA_RESERVA_DE_VAGAS_EXCEL")
    List<Object[]> listaReservaDeVagasExcel(@Param("P_ID_CONTA") Long idConta);
	
	@Query("select candidato from Candidato join candidato.conta conta where conta.idConta = :idConta")
    Optional<List<Candidato>> findByConta_IdConta(@Param("idConta") Long idConta);
	
}
