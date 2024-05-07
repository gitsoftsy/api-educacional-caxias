package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CategoriaEscolaPrivadaDTO;
import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.CategoriaEscolaPrivada;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DestinacaoLixoRepository;

@Service
public class DestinacaoLixoService {

	@Autowired
	private DestinacaoLixoRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List< DestinacaoLixoDTO> buscarPorIdConta(Long id) {
		List< DestinacaoLixo> destinacaoLixo = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinacaoLixo por id de conta"));
		return destinacaoLixo.stream().map(DestinacaoLixoDTO::new).collect(Collectors.toList());
	}

	
	@Transactional(readOnly = true)
	public DestinacaoLixoDTO buscarPorId(Long id){
		return new DestinacaoLixoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public DestinacaoLixoDTO salvar(DestinacaoLixoDTO dto) {
		
		DestinacaoLixo destinacao = criarDestinacaoAPartirDTO(dto);
		
		destinacao = repository.save(destinacao);
		return new DestinacaoLixoDTO(destinacao);
	}
	
	private DestinacaoLixo criarDestinacaoAPartirDTO(DestinacaoLixoDTO dto) {
		DestinacaoLixo destinacao = new DestinacaoLixo();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, destinacao, "idDestinacaoLixo", "ativo", "dataCadastro");
		destinacao.setConta(conta);
		destinacao.setAtivo('S');
		destinacao.setDataCadastro(LocalDateTime.now());
		return destinacao;
	}
	
	@Transactional
	public DestinacaoLixoDTO atualizar(DestinacaoLixoDTO dto) {
		DestinacaoLixo destinacao = repository.getReferenceById(dto.getIdDestinacaoLixo());
		atualizaDados(destinacao, dto);
		return new DestinacaoLixoDTO(destinacao);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idDestinacaoLixo) {
		DestinacaoLixo destinacao = repository.getReferenceById(idDestinacaoLixo);
		destinacao.setAtivo(status);
	}

	
	private void atualizaDados(DestinacaoLixo destino, DestinacaoLixoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idDestinacaoLixo", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		
	}
}
