package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
	@Query("select transacao from Transacao transacao join transacao.modulo modulo where modulo.idModulo = :idModulo")
    Optional<List<Transacao>> findByModulo_IdModulo(@Param("idModulo") Long idModulo);
	

}
