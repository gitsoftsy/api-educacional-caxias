package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.Escola;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long>{

	List<Escola> findByNomeEscola(String nomeEscola);
	List<Escola> findEscolaByAtivo(Character ativo);
	List<Escola> findEscolaByCnpj(String cnpj);
	List<Escola> findEscolaByCodigoInep(String codigoInep);
	
	
	@Query("select escola from Escola escola join escola.conta conta where conta.idConta = :idConta")
    Optional<List<Escola>> findByConta_IdConta(@Param("idConta") Long idConta);
	
	@org.springframework.data.jpa.repository.Query(value = "CALL PROC_LISTA_ESCOLAS_USUARIO(:pIdUsuario, :pIdConta)", nativeQuery = true)
    List<Object[]> listaEscolasUsuario(@Param("pIdUsuario") Long idUsuario, @Param("pIdConta") Long idConta);

    Optional<List<Escola>> findActiveSchoolsByConta_IdContaAndAtivo(Long idConta, Character ativo);



}
