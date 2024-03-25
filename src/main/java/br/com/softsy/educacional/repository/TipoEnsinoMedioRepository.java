package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.TipoEnsinoMedio;

@Repository
public interface TipoEnsinoMedioRepository extends JpaRepository<TipoEnsinoMedio, Long> {

	List<TipoEnsinoMedio> findByTipoEnsinoMedio(String tipoEnsinoMedio);
}