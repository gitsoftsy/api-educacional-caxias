package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.DestinacaoLixo;

@Repository
public interface DestinacaoLixoRepository extends JpaRepository<DestinacaoLixo, Long>{

	List<DestinacaoLixo> findByDestinacaoLixo (String destinacaoLixo);
}
