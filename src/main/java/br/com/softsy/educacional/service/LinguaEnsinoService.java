package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.LinguaEnsinoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.LinguaEnsinoRepository;

@Service
public class LinguaEnsinoService {
	
	@Autowired
	private LinguaEnsinoRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<LinguaEnsinoDTO> buscarPorIdConta(Long id) {
		List<LinguaEnsino> linguaEnsino = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar linguaEnsino por id de conta"));
		return linguaEnsino.stream()
				.map(LinguaEnsinoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public LinguaEnsinoDTO buscarPorId(Long id){
		return new LinguaEnsinoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public LinguaEnsinoDTO salvar(LinguaEnsinoDTO dto) {
		
		LinguaEnsino linguaEnsino = criarLinguaEnsinoAPartirDTO(dto);
		
		linguaEnsino = repository.save(linguaEnsino);
		return new LinguaEnsinoDTO(linguaEnsino);
	}

	
	private LinguaEnsino criarLinguaEnsinoAPartirDTO(LinguaEnsinoDTO dto) {
		LinguaEnsino linguaEnsino = new LinguaEnsino();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, linguaEnsino, "idLinguaEnsino", "ativo", "dataCadastro");
		linguaEnsino.setConta(conta);;
		linguaEnsino.setAtivo('S');
		linguaEnsino.setDataCadastro(LocalDateTime.now());
		return linguaEnsino;
	}
	
	@Transactional
	public LinguaEnsinoDTO atualizar(LinguaEnsinoDTO dto) {
		LinguaEnsino linguaEnsino = repository.getReferenceById(dto.getIdLinguaEnsino());
		atualizaDados(linguaEnsino, dto);
		return new LinguaEnsinoDTO(linguaEnsino);
	}
	
	private void atualizaDados(LinguaEnsino destino, LinguaEnsinoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idLinguaEnsino", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		
	}	
	
	@Transactional
	public void ativaDesativa(char status, Long idLinguaEnsino) {
		LinguaEnsino destinacao = repository.getReferenceById(idLinguaEnsino);
		destinacao.setAtivo(status);
	}
	
}
