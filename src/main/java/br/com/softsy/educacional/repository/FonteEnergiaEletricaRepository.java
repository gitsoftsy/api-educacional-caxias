package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softsy.educacional.model.FonteEnergiaEletrica;

@Repository
public interface FonteEnergiaEletricaRepository extends JpaRepository<FonteEnergiaEletrica, Long>{

	List<FonteEnergiaEletrica> findByFonteEnergiaEletrica (String fonteEnergiaEletrica);
}
