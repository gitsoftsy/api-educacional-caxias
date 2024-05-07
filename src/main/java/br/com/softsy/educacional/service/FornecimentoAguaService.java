package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.FornecimentoAguaDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.FornecimentoAgua;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.FornecimentoAguaRepository;

@Service
public class FornecimentoAguaService {

	@Autowired 
	private FornecimentoAguaRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<FornecimentoAguaDTO> buscarPorIdConta(Long id) {
		List<FornecimentoAgua> fornecimentoAgua = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar fornecimentoAgua por id de conta"));
		return fornecimentoAgua.stream()
				.map(FornecimentoAguaDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public FornecimentoAguaDTO buscarPorId(Long id){
		return new FornecimentoAguaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public FornecimentoAguaDTO salvar(FornecimentoAguaDTO dto) {
		
		FornecimentoAgua fornecimento = criarFornecimentoAPartirDTO(dto);
		
		fornecimento = repository.save(fornecimento);
		return new FornecimentoAguaDTO(fornecimento);
	}
	
	private FornecimentoAgua criarFornecimentoAPartirDTO(FornecimentoAguaDTO dto) {
		FornecimentoAgua fornecimento = new FornecimentoAgua();
		Conta conta = contaRepository.findById(dto.getContaId())
	                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, fornecimento, "idFornecimentoAgua", "ativo", "dataCadastro");
		fornecimento.setConta(conta);
		fornecimento.setAtivo('S');
		fornecimento.setDataCadastro(LocalDateTime.now());
		return fornecimento;
	}
	
	@Transactional
	public FornecimentoAguaDTO atualizar(FornecimentoAguaDTO dto) {
		FornecimentoAgua fornecimento = repository.getReferenceById(dto.getIdFornecimentoAgua());
		atualizaDados(fornecimento, dto);
		return new FornecimentoAguaDTO(fornecimento);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idFornecimentoAgua) {
		FornecimentoAgua fornecimento = repository.getReferenceById(idFornecimentoAgua);
		fornecimento.setAtivo(status);
	}
	
	private void atualizaDados(FornecimentoAgua destino, FornecimentoAguaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idFornecimentoAgua", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		
	}
}
