package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaDispositivo;

@Repository
public interface EscolaDispositivoRepository extends JpaRepository<EscolaDispositivo, Long>{

}
