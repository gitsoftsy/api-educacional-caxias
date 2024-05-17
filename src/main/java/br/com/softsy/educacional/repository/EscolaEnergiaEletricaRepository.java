package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaEnergiaEletrica;

@Repository
public interface EscolaEnergiaEletricaRepository extends JpaRepository<EscolaEnergiaEletrica, Long>{

}
