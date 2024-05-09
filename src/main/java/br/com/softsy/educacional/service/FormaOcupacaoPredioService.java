package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.FormaOcupacaoPredioDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.FormaOcupacaoPredio;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.FormaOcupacaoPredioRepository;

@Service
public class FormaOcupacaoPredioService {

	@Autowired
	private FormaOcupacaoPredioRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List< FormaOcupacaoPredioDTO> buscarPorIdConta(Long id) {
		List<FormaOcupacaoPredio> formaOcupacaoPredio = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar FormaOcupacaoPredio por id de conta"));
		return  formaOcupacaoPredio.stream()
				.map( FormaOcupacaoPredioDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public FormaOcupacaoPredioDTO buscarPorId(Long id){
		return new FormaOcupacaoPredioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public FormaOcupacaoPredioDTO salvar(FormaOcupacaoPredioDTO dto) {
		FormaOcupacaoPredio formaOcupacao = criarFormaOcupacaoAPartirDTO(dto);
		
		formaOcupacao = repository.save(formaOcupacao);
		return new FormaOcupacaoPredioDTO(formaOcupacao);
	}
	
	
	private FormaOcupacaoPredio criarFormaOcupacaoAPartirDTO(FormaOcupacaoPredioDTO dto) {
		FormaOcupacaoPredio formaOcupacao = new FormaOcupacaoPredio();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, formaOcupacao, "idFormaOcupacaoPredio", "ativo", "dataCadastro");
		formaOcupacao.setConta(conta);
		formaOcupacao.setAtivo('S');
		formaOcupacao.setDataCadastro(LocalDateTime.now());
		return formaOcupacao;
	}
	
	@Transactional
	public FormaOcupacaoPredioDTO atualizar(FormaOcupacaoPredioDTO dto) {
		FormaOcupacaoPredio formaOcupacao = repository.getReferenceById(dto.getIdFormaOcupacaoPredio());
		atualizaDados(formaOcupacao, dto);
		return new FormaOcupacaoPredioDTO(formaOcupacao);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idFormaOcupacaoPredio) {
		FormaOcupacaoPredio formaOcupacao = repository.getReferenceById(idFormaOcupacaoPredio);
		formaOcupacao.setAtivo(status);
	}
	
	private void atualizaDados(FormaOcupacaoPredio destino, FormaOcupacaoPredioDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idFormaOcupacaoPredio", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setConta(conta);
		
	}
}
