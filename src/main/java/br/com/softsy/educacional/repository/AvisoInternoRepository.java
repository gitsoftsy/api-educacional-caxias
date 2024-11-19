package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.com.softsy.educacional.model.AvisoInterno;

@Repository
public interface AvisoInternoRepository extends JpaRepository<AvisoInterno, Long> {

	
    List<AvisoInterno> findByIdUsuarioEnvio(Long idUsuarioEnvio);
    List<AvisoInterno> findByIdProfessorEnvio(Long idProfessorEnvio);

}
