package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ModalidadeEscolaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.repository.ModalidadeEscolaRepository;

@Service
public class ModalidadeEscolaService {

	
	@Autowired
	private ModalidadeEscolaRepository repository;
	
	public List<ModalidadeEscola> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public ModalidadeEscolaDTO buscarPorId(Long id){
		return new ModalidadeEscolaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public ModalidadeEscolaDTO salvar(ModalidadeEscolaDTO dto) {
		validarModalidadeEscola(dto.getModalidadeEscola());
		
		ModalidadeEscola modalidadeEscola = criarModalidadeEscolaAPartirDTO(dto);
		
		modalidadeEscola = repository.save(modalidadeEscola);
		return new ModalidadeEscolaDTO(modalidadeEscola);
	}
	
	private void validarModalidadeEscola(String modalidadeEscola) {
		Optional<ModalidadeEscola> ModalidadeEscolaExistente = repository.findByModalidadeEscola(modalidadeEscola).stream().findFirst();
		if(ModalidadeEscolaExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private ModalidadeEscola criarModalidadeEscolaAPartirDTO(ModalidadeEscolaDTO dto) {
		ModalidadeEscola modalidadeEscola = new ModalidadeEscola();
		BeanUtils.copyProperties(dto, modalidadeEscola, "idModalidadeEscola", "modalidadeEscola", "ativo", "dataCadastro");
		modalidadeEscola.setAtivo('S');
		modalidadeEscola.setDataCadastro(LocalDateTime.now());
		return modalidadeEscola;
	}
	
	@Transactional
	public ModalidadeEscolaDTO atualizar(ModalidadeEscolaDTO dto) {
		ModalidadeEscola modalidadeEscola = repository.getReferenceById(dto.getIdModalidadeEscola());
		atualizaDados(modalidadeEscola, dto);
		return new ModalidadeEscolaDTO(modalidadeEscola);
	}
	
	private void atualizaDados(ModalidadeEscola destino, ModalidadeEscolaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idModalidadeEscola", "modalidadeEscola", "ativo", "dataCadastro");
		
	}	
}
