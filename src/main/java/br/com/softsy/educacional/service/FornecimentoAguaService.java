package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.FornecimentoAguaDTO;
import br.com.softsy.educacional.dto.LocalizacaoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.FornecimentoAgua;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.repository.FornecimentoAguaRepository;

@Service
public class FornecimentoAguaService {

	@Autowired FornecimentoAguaRepository repository;
	
	public List<FornecimentoAgua> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public FornecimentoAguaDTO buscarPorId(Long id){
		return new FornecimentoAguaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public FornecimentoAguaDTO salvar(FornecimentoAguaDTO dto) {
		validarFornecimento(dto.getFornecimentoAgua());
		
		FornecimentoAgua fornecimento = criarFornecimentoAPartirDTO(dto);
		
		fornecimento = repository.save(fornecimento);
		return new FornecimentoAguaDTO(fornecimento);
	}
	
	private FornecimentoAgua criarFornecimentoAPartirDTO(FornecimentoAguaDTO dto) {
		FornecimentoAgua fornecimento = new FornecimentoAgua();
		BeanUtils.copyProperties(dto, fornecimento, "idFornecimentoAgua", "ativo", "dataCadastro");
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
	
	
	private void validarFornecimento(String fornecimentoAgua) {
		Optional<FornecimentoAgua> fornecimentoExistente = repository.findByFornecimentoAgua(fornecimentoAgua).stream().findFirst();
		if(fornecimentoExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}
	
	private void atualizaDados(FornecimentoAgua destino, FornecimentoAguaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idFornecimentoAgua", "ativo", "dataCadastro");
		
	}
}
