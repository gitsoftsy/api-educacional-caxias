package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.EscolaInfraestrutura;

@Repository
public interface EscolaInfraestruturaRepository extends JpaRepository<EscolaInfraestrutura, Long>{

}
