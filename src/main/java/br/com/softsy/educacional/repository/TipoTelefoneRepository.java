package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.TipoTelefone;


public interface TipoTelefoneRepository    extends JpaRepository<TipoTelefone, Long>{

	List<TipoTelefone> findByTipoTelefone(String tipoTelefone);
}
