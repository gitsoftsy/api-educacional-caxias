package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ContaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.repository.ContaRepository;

@Service
public class ContaService {

	@Autowired ContaRepository repository;
	
	public List<Conta> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public ContaDTO buscarPorId(Long id){
		return new ContaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public ContaDTO salvar(ContaDTO dto) {
		validarConta(dto.getConta());
		
		Conta conta = criarContaAPartirDTO(dto);
		
		conta = repository.save(conta);
		return new ContaDTO(conta);
	}
	
	private Conta criarContaAPartirDTO(ContaDTO dto) {
		Conta conta = new Conta();
		BeanUtils.copyProperties(dto, conta, "idConta", "ativo", "dataCadastro");
		conta.setAtivo('S');
		conta.setDataCadastro(LocalDateTime.now());
		return conta;
	}
	
	@Transactional
	public ContaDTO atualizar(ContaDTO dto) {
		Conta dependencia = repository.getReferenceById(dto.getIdConta());
		atualizaDados(dependencia, dto);
		return new ContaDTO(dependencia);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idConta) {
		Conta conta = repository.getReferenceById(idConta);
		conta.setAtivo(status);
	}
	
	private void validarConta(String conta) {
		Optional<Conta> contaExistente = repository.findByConta(conta).stream().findFirst();
		if(contaExistente.isPresent()) {
			throw new UniqueException("Essa dependência já existe.");
		}
	}
	
	private void atualizaDados(Conta destino, ContaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idConta", "ativo", "dataCadastro");	
	}
}