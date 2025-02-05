package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.PagarmeRecebedorUtm;

@Repository
public interface PagarmeRecebedorUtmRepository extends JpaRepository<PagarmeRecebedorUtm, Long> {

	List<PagarmeRecebedorUtm> findByUtmId(Long idUtm);
}
