package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AtoRegulatorioDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AtoRegulatorio;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.repository.AtoRegulatorioRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;

@Service
public class AtoRegulatorioService {
	
	@Autowired AtoRegulatorioRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<AtoRegulatorio> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AtoRegulatorioDTO buscarPorId(Long id){
		return new AtoRegulatorioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public AtoRegulatorioDTO salvar(AtoRegulatorioDTO dto) {
		
		AtoRegulatorio atoRegulatorio = criarAtoAPartirDTO(dto);
		
		atoRegulatorio = repository.save(atoRegulatorio);
		return new AtoRegulatorioDTO(atoRegulatorio);
	}
	
	private AtoRegulatorio criarAtoAPartirDTO(AtoRegulatorioDTO dto) {
		AtoRegulatorio atoRegulatorio = new AtoRegulatorio();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, atoRegulatorio, "idAtoRegulatorio", "ativo", "dataCadastro");
		atoRegulatorio.setDependenciaAdm(dependenciaAdm);
		atoRegulatorio.setAtivo('S');
		atoRegulatorio.setDataCadastro(LocalDateTime.now());
		return atoRegulatorio;
	}
	
	@Transactional
	public AtoRegulatorioDTO atualizar(AtoRegulatorioDTO dto) {
		AtoRegulatorio atoRegulatorio = repository.getReferenceById(dto.getIdAtoRegulatorio());
		atualizaDados(atoRegulatorio, dto);
		return new AtoRegulatorioDTO(atoRegulatorio);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idAtoRegulatorio) {
		AtoRegulatorio atoRegulatorio = repository.getReferenceById(idAtoRegulatorio);
		atoRegulatorio.setAtivo(status);
	}
	
	
	private void atualizaDados(AtoRegulatorio destino, AtoRegulatorioDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idAtoRegulatorio", "ativo", "dataCadastro");
		
	}

}
