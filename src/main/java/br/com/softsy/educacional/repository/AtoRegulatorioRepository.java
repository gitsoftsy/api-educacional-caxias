package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.AtoRegulatorio;

public interface AtoRegulatorioRepository extends JpaRepository<AtoRegulatorio, Long>{

	List<AtoRegulatorio> findByAtoRegulatorio(String atoRegulatorio);
}
