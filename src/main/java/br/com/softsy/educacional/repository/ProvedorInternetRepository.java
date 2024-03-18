package br.com.softsy.educacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softsy.educacional.model.ProvedorInternet;

public interface ProvedorInternetRepository extends JpaRepository<ProvedorInternet, Long>{
	
	List<ProvedorInternet> findByProvedorInternet(String provedorInternet);

}
