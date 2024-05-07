package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.LocalizacaoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.LocalizacaoRepository;

@Service
public class LocalizacaoService {

	@Autowired
	private LocalizacaoRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<LocalizacaoDTO> buscarPorIdConta(Long id) {
		List<Localizacao> localizacao = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar localizacao por id de conta"));
		return localizacao.stream()
				.map(LocalizacaoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public LocalizacaoDTO buscarPorId(Long id){
		return new LocalizacaoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public LocalizacaoDTO salvar(LocalizacaoDTO dto) {
		
		Localizacao localizacao = criarLocalizacaoAPartirDTO(dto);
		
		localizacao = repository.save(localizacao);
		return new LocalizacaoDTO(localizacao);
	}
	
	private Localizacao criarLocalizacaoAPartirDTO(LocalizacaoDTO dto) {
		Localizacao localizacao = new Localizacao();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, localizacao, "idLocalizacao", "ativo", "dataCadastro");
		localizacao.setConta(conta);
		localizacao.setAtivo('S');
		localizacao.setDataCadastro(LocalDateTime.now());
		return localizacao;
	}
	
	@Transactional
	public LocalizacaoDTO atualizar(LocalizacaoDTO dto) {
		Localizacao localizacao = repository.getReferenceById(dto.getIdLocalizacao());
		atualizaDados(localizacao, dto);
		return new LocalizacaoDTO(localizacao);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idLocalizacao) {
		Localizacao localizacao = repository.getReferenceById(idLocalizacao);
		localizacao.setAtivo(status);
	}
	
	
	private void atualizaDados(Localizacao destino, LocalizacaoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idLocalizacao", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
	}
}
