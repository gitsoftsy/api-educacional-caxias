package br.com.softsy.educacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.PagarmeRecebedorEscola;

@Repository
public interface PagarmeRecebedorEscolaRepository extends JpaRepository<PagarmeRecebedorEscola, Long> {

}
