package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EntidadeSuperiorDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.EntidadeSuperior;
import br.com.softsy.educacional.repository.EntidadeSuperiorRepository;

@Service
public class EntidadeSuperiorService {
	
	@Autowired
	private EntidadeSuperiorRepository repository;
	
	public List<EntidadeSuperior> listarTudo() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public EntidadeSuperiorDTO buscarPorId(Long id) {
		return new EntidadeSuperiorDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public EntidadeSuperiorDTO salvar(EntidadeSuperiorDTO dto) {
		validarEntidadeSuperior(dto.getEntidadeSuperior());
		
		EntidadeSuperior entidadeSuperior = criarEntidadeSuperiorAPartirDTO(dto);
		
		entidadeSuperior = repository.save(entidadeSuperior);
		return new EntidadeSuperiorDTO(entidadeSuperior);
	}
	
	private void validarEntidadeSuperior(String entidadeSuperior) {
		Optional<EntidadeSuperior> entidadeSuperiorExistente = repository.findByEntidadeSuperior(entidadeSuperior).stream().findFirst();
		if (entidadeSuperiorExistente.isPresent()) {
			throw new UniqueException("Essa entidade superior já existe.");
		}
	}
	
	private EntidadeSuperior criarEntidadeSuperiorAPartirDTO(EntidadeSuperiorDTO dto) {
		EntidadeSuperior entidadeSuperior = new EntidadeSuperior();
		BeanUtils.copyProperties(dto, entidadeSuperior, "idEntidadeSuperior", "ativo", "dataCadastro");
		entidadeSuperior.setDataCadastro(LocalDateTime.now());
		entidadeSuperior.setAtivo('S');
		return entidadeSuperior;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idEntidadeSuperior) {
		EntidadeSuperior entidadeSuperior = repository.getReferenceById(idEntidadeSuperior);
		entidadeSuperior.setAtivo(status);
	}
	
	@Transactional
	public EntidadeSuperiorDTO atualizar(EntidadeSuperiorDTO dto) {
		EntidadeSuperior entidadeSuperior = repository.getReferenceById(dto.getIdEntidadeSuperior());
		atualizaDados(entidadeSuperior, dto);
		return new EntidadeSuperiorDTO(entidadeSuperior);
	}
	
	private void atualizaDados(EntidadeSuperior destino, EntidadeSuperiorDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idEntidadeSuperior", "ativo", "dataCadastro");
	}
}
