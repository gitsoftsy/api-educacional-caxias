package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.DependenciaAdministrativaDTO;
import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;

@Service
public class DependenciaAdministrativaService {

	@Autowired DependenciaAdministrativaRepository repository;
	
	public List<DependenciaAdministrativa> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public DependenciaAdministrativaDTO buscarPorId(Long id){
		return new DependenciaAdministrativaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public DependenciaAdministrativaDTO salvar(DependenciaAdministrativaDTO dto) {
		validarDependencia(dto.getDependenciaAdministrativa());
		
		DependenciaAdministrativa dependencia = criarDependenciaAPartirDTO(dto);
		
		dependencia = repository.save(dependencia);
		return new DependenciaAdministrativaDTO(dependencia);
	}
	
	private DependenciaAdministrativa criarDependenciaAPartirDTO(DependenciaAdministrativaDTO dto) {
		DependenciaAdministrativa dependencia = new DependenciaAdministrativa();
		BeanUtils.copyProperties(dto, dependencia, "idDependenciaAdministrativa", "ativo", "dataCadastro");
		dependencia.setAtivo('S');
		dependencia.setDataCadastro(LocalDateTime.now());
		return dependencia;
	}
	
	@Transactional
	public DependenciaAdministrativaDTO atualizar(DependenciaAdministrativaDTO dto) {
		DependenciaAdministrativa dependencia = repository.getReferenceById(dto.getIdDependenciaAdministrativa());
		atualizaDados(dependencia, dto);
		return new DependenciaAdministrativaDTO(dependencia);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idDependenciaAdministrativa) {
		DependenciaAdministrativa dependencia = repository.getReferenceById(idDependenciaAdministrativa);
		dependencia.setAtivo(status);
	}
	
	private void validarDependencia(String dependenciaAdministrativa) {
		Optional<DependenciaAdministrativa> dependenciaExistente = repository.findByDependenciaAdministrativa(dependenciaAdministrativa).stream().findFirst();
		if(dependenciaExistente.isPresent()) {
			throw new UniqueException("Essa dependência já existe.");
		}
	}
	
	private void atualizaDados(DependenciaAdministrativa destino, DependenciaAdministrativaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idDependenciaAdministrativa", "ativo", "dataCadastro");	
	}
}
