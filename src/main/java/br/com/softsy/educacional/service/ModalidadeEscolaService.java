package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ModalidadeEscolaDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.ModalidadeEscolaRepository;

@Service
public class ModalidadeEscolaService {

	@Autowired
	private ModalidadeEscolaRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<ModalidadeEscolaDTO> buscarPorIdConta(Long id) {
		List<ModalidadeEscola> modalidadeEscola = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar modalidadeEscola por id de conta"));
		return modalidadeEscola.stream()
				.map(ModalidadeEscolaDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ModalidadeEscolaDTO buscarPorId(Long id){
		return new ModalidadeEscolaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public ModalidadeEscolaDTO salvar(ModalidadeEscolaDTO dto) {
		
		ModalidadeEscola modalidadeEscola = criarModalidadeEscolaAPartirDTO(dto);
		
		modalidadeEscola = repository.save(modalidadeEscola);
		return new ModalidadeEscolaDTO(modalidadeEscola);
	}
	
	
	private ModalidadeEscola criarModalidadeEscolaAPartirDTO(ModalidadeEscolaDTO dto) {
		ModalidadeEscola modalidadeEscola = new ModalidadeEscola();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, modalidadeEscola, "idModalidadeEscola", "ativo", "dataCadastro");
		modalidadeEscola.setConta(conta);
		modalidadeEscola.setAtivo('S');
		modalidadeEscola.setDataCadastro(LocalDateTime.now());
		return modalidadeEscola;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idModalidadeEscola) {
		ModalidadeEscola destinacao = repository.getReferenceById(idModalidadeEscola);
		destinacao.setAtivo(status);
	}
	
	@Transactional
	public ModalidadeEscolaDTO atualizar(ModalidadeEscolaDTO dto) {
		ModalidadeEscola modalidadeEscola = repository.getReferenceById(dto.getIdModalidadeEscola());
		atualizaDados(modalidadeEscola, dto);
		return new ModalidadeEscolaDTO(modalidadeEscola);
	}
	
	private void atualizaDados(ModalidadeEscola destino, ModalidadeEscolaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idModalidadeEscola", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);;
		
	}	
}
