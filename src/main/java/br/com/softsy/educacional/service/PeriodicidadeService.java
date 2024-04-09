package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.PeriodicidadeDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.Periodicidade;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.PeriodicidadeRepository;

@Service
public class PeriodicidadeService {

	@Autowired PeriodicidadeRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<PeriodicidadeDTO> listarTudo(){
		return repository.findAll().stream().map(PeriodicidadeDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PeriodicidadeDTO buscarPorId(Long id){
		return new PeriodicidadeDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public PeriodicidadeDTO salvar(PeriodicidadeDTO dto) {

		Periodicidade periodicidade = criarPeriodicidadeAPartirDTO(dto);
		
		periodicidade = repository.save(periodicidade);
		return new PeriodicidadeDTO(periodicidade);
	}
	
	private Periodicidade criarPeriodicidadeAPartirDTO(PeriodicidadeDTO dto) {
		Periodicidade periodicidade = new Periodicidade();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, periodicidade, "idPeriodicidade", "ativo", "dataCadastro");
		periodicidade.setDependenciaAdm(dependenciaAdm);
		periodicidade.setAtivo('S');
		periodicidade.setDataCadastro(LocalDateTime.now());
		return periodicidade;
	}
	
	@Transactional
	public PeriodicidadeDTO atualizar(PeriodicidadeDTO dto) {
		Periodicidade periodicidade = repository.getReferenceById(dto.getIdPeriodicidade());
		atualizaDados(periodicidade, dto);
		return new PeriodicidadeDTO(periodicidade);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idPeriodicidade) {
		Periodicidade periodicidade = repository.getReferenceById(idPeriodicidade);
		periodicidade.setAtivo(status);
	}
	
	
	private void atualizaDados(Periodicidade destino, PeriodicidadeDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idPeriodicidade", "ativo", "dataCadastro");
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
		
	}
}
