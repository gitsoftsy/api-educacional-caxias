package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.LinguaEnsinoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.repository.LinguaEnsinoRepository;

@Service
public class LinguaEnsinoService {
	
	
	@Autowired
	private LinguaEnsinoRepository repository;
	
	public List<LinguaEnsino> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public LinguaEnsinoDTO buscarPorId(Long id){
		return new LinguaEnsinoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public LinguaEnsinoDTO salvar(LinguaEnsinoDTO dto) {
		validarLinguaEnsino(dto.getLinguaEnsino());
		
		LinguaEnsino linguaEnsino = criarLinguaEnsinoAPartirDTO(dto);
		
		linguaEnsino = repository.save(linguaEnsino);
		return new LinguaEnsinoDTO(linguaEnsino);
	}
	
	private void validarLinguaEnsino(String linguaEnsino) {
		Optional<LinguaEnsino> LinguaEnsinoExistente = repository.findByLinguaEnsino(linguaEnsino).stream().findFirst();
		if(LinguaEnsinoExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private LinguaEnsino criarLinguaEnsinoAPartirDTO(LinguaEnsinoDTO dto) {
		LinguaEnsino linguaEnsino = new LinguaEnsino();
		BeanUtils.copyProperties(dto, linguaEnsino, "idLinguaEnsino", "ativo", "dataCadastro");
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
		
	}	
	
	@Transactional
	public void ativaDesativa(char status, Long idLinguaEnsino) {
		LinguaEnsino destinacao = repository.getReferenceById(idLinguaEnsino);
		destinacao.setAtivo(status);
	}
	
}
