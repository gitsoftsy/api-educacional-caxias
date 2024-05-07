package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EntidadeSuperiorDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.EntidadeSuperior;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.EntidadeSuperiorRepository;

@Service
public class EntidadeSuperiorService {
	
	@Autowired
	private EntidadeSuperiorRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
		
	@Transactional(readOnly = true)
	public List<EntidadeSuperiorDTO> buscarPorIdConta(Long id) {
		List<EntidadeSuperior> entidadeSuperior = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar entidadeSuperior por id de conta"));
		return entidadeSuperior.stream()
				.map(EntidadeSuperiorDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public EntidadeSuperiorDTO buscarPorId(Long id) {
		return new EntidadeSuperiorDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public EntidadeSuperiorDTO salvar(EntidadeSuperiorDTO dto) {
		EntidadeSuperior entidadeSuperior = criarEntidadeSuperiorAPartirDTO(dto);
		
		entidadeSuperior = repository.save(entidadeSuperior);
		return new EntidadeSuperiorDTO(entidadeSuperior);
	}
	
	
	private EntidadeSuperior criarEntidadeSuperiorAPartirDTO(EntidadeSuperiorDTO dto) {
		EntidadeSuperior entidadeSuperior = new EntidadeSuperior();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, entidadeSuperior, "idEntidadeSuperior", "ativo", "dataCadastro");
		entidadeSuperior.setConta(conta);
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
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
	}
}
