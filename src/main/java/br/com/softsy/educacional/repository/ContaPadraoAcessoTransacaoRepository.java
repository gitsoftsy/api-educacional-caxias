package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.ContaPadraoAcessoTransacao;

@Repository
public interface ContaPadraoAcessoTransacaoRepository extends JpaRepository<ContaPadraoAcessoTransacao, Long> {

	@Query("select contaPadraoAcessoTransacao from ContaPadraoAcessoTransacao contaPadraoAcessoTransacao join contaPadraoAcessoTransacao.contaPadraoAcesso contaPadraoAcesso where contaPadraoAcesso.idContaPadraoAcesso = :idContaPadraoAcesso")
    Optional<List<ContaPadraoAcessoTransacao>> findByContaPadraoAcesso_IdContaPadraoAcesso(@Param("idContaPadraoAcesso") Long idContaPadraoAcesso);
	
	
    @Procedure(name = "PROC_ALTERA_INSERE_PADRAO_ACESSO_TRANSACAO")
    void alteraInserePadraoAcessoTransacao(
        @Param("P_ID_CONTA_PADRAO_ACESSO") Long idContaPadraoAcesso,
        @Param("P_ID_TRANSACAO") Long idTransacao,
        @Param("P_ALTERA") String altera,
        @Param("P_ACESSA") String acessa
    );
	
}
