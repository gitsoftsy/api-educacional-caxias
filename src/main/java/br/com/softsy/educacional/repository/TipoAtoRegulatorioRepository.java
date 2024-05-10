package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.TipoAtoRegulatorio;

@Repository
public interface TipoAtoRegulatorioRepository extends JpaRepository<TipoAtoRegulatorio, Long>{
	
	List<TipoAtoRegulatorio> findByTipoAtoRegulatorio (String tipoAtoRegulatorio);


}
