package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.FormaOcupacaoPredioDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.FormaOcupacaoPredio;
import br.com.softsy.educacional.repository.FormaOcupacaoPredioRepository;

@Service
public class FormaOcupacaoPredioService {

	@Autowired
	private FormaOcupacaoPredioRepository repository;
	
	public List<FormaOcupacaoPredio> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public FormaOcupacaoPredioDTO buscarPorId(Long id){
		return new FormaOcupacaoPredioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public FormaOcupacaoPredioDTO salvar(FormaOcupacaoPredioDTO dto) {
		validarFormaOcupacao(dto.getFormaOcupacaoPredio());
		
		FormaOcupacaoPredio formaOcupacao = criarFormaOcupacaoAPartirDTO(dto);
		
		formaOcupacao = repository.save(formaOcupacao);
		return new FormaOcupacaoPredioDTO(formaOcupacao);
	}
	
	private void validarFormaOcupacao(String formaOcupacaoPredio) {
		Optional<FormaOcupacaoPredio> formaOcupacaoExistente = repository.findByFormaOcupacaoPredio(formaOcupacaoPredio).stream().findFirst();
		if(formaOcupacaoExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private FormaOcupacaoPredio criarFormaOcupacaoAPartirDTO(FormaOcupacaoPredioDTO dto) {
		FormaOcupacaoPredio formaOcupacao = new FormaOcupacaoPredio();
		BeanUtils.copyProperties(dto, formaOcupacao, "idFormaOcupacaoPredio", "ativo", "dataCadastro");
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
		
	}
}
