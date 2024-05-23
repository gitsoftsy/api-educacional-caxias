package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.ContaPadraoAcesso;
import br.com.softsy.educacional.model.ContaPadraoAcessoTransacao;

@Repository
public interface ContaPadraoAcessoTransacaoRepository extends JpaRepository<ContaPadraoAcessoTransacao, Long> {

	@Query("select contaPadraoAcessoTransacao from ContaPadraoAcessoTransacao contaPadraoAcessoTransacao join contaPadraoAcessoTransacao.transacao transacao where transacao.idTransacao = :idTransacao")
    Optional<List<ContaPadraoAcessoTransacao>> findByTransacao_IdTransacao(@Param("idTransacao") Long idTransacao);
	
}
