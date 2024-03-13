package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.SituacaoFuncionamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.SituacaoFuncionamento;
import br.com.softsy.educacional.repository.SituacaoFuncionamentoRepository;


@Service
public class SituacaoFuncionamentoService {
	
	@Autowired
	private SituacaoFuncionamentoRepository repository;
	
	public List<SituacaoFuncionamento> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public SituacaoFuncionamentoDTO buscarPorId(Long id){
		return new SituacaoFuncionamentoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public SituacaoFuncionamentoDTO salvar(SituacaoFuncionamentoDTO dto) {
		validarSituacaoFuncionamento(dto.getSituacaoFuncionamento());
		
		SituacaoFuncionamento situacaoFuncionamento = criarSituacaoFuncionamentoAPartirDTO(dto);
		
		situacaoFuncionamento = repository.save(situacaoFuncionamento);
		return new SituacaoFuncionamentoDTO(situacaoFuncionamento);
	}
	
	private void validarSituacaoFuncionamento(String situacaoFuncionamento) {
		Optional<SituacaoFuncionamento> SituacaoFuncionamentoExistente = repository.findBySituacaoFuncionamento(situacaoFuncionamento).stream().findFirst();
		if(SituacaoFuncionamentoExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private SituacaoFuncionamento criarSituacaoFuncionamentoAPartirDTO(SituacaoFuncionamentoDTO dto) {
		SituacaoFuncionamento situacaoFuncionamento = new SituacaoFuncionamento();
		BeanUtils.copyProperties(dto, situacaoFuncionamento, "idSituacaoFuncionamento", "ativo", "dataCadastro");
		situacaoFuncionamento.setAtivo('S');
		situacaoFuncionamento.setDataCadastro(LocalDateTime.now());
		return situacaoFuncionamento;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idSituacaoFuncionamento) {
		SituacaoFuncionamento destinacao = repository.getReferenceById(idSituacaoFuncionamento);
		destinacao.setAtivo(status);
	}
	
	@Transactional
	public SituacaoFuncionamentoDTO atualizar(SituacaoFuncionamentoDTO dto) {
		SituacaoFuncionamento situacaoFuncionamento = repository.getReferenceById(dto.getIdSituacaoFuncionamento());
		atualizaDados(situacaoFuncionamento, dto);
		return new SituacaoFuncionamentoDTO(situacaoFuncionamento);
	}
	
	private void atualizaDados(SituacaoFuncionamento destino, SituacaoFuncionamentoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idSituacaoFuncionamento", "ativo", "dataCadastro");
		
	}	

}
