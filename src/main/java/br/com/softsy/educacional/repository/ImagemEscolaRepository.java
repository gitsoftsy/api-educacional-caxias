package br.com.softsy.educacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.softsy.educacional.dto.ImagemEscolaDTO;
import br.com.softsy.educacional.model.Escola;

public interface ImagemEscolaRepository extends JpaRepository<Escola, Long>{
	
//	@Query("select imagemEscola from Escola imagemEscola join imagemEscola.logoEscola logoEscola where logoEscola.idEscola = :idEscola")
//    Optional<List<ImagemEscolaDTO>> findByLogoEscola_IdEscola(@Param("idEscola") Long idEscola);


}
