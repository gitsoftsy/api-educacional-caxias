package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.FormaOrganEnsinoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.FormaOrganEnsino;
import br.com.softsy.educacional.repository.FormaOrganEnsinoRepository;

@Service
public class FormaOrganEnsinoService {

	@Autowired
	private FormaOrganEnsinoRepository repository;

	public List<FormaOrganEnsino> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public FormaOrganEnsinoDTO buscarPorId(Long id) {
		return new FormaOrganEnsinoDTO(repository.getReferenceById(id));
	}

	@Transactional
	public FormaOrganEnsinoDTO salvar(FormaOrganEnsinoDTO dto) {
		validarFormaOrganEnsino(dto.getFormaOrganEnsino());

		FormaOrganEnsino formaOrganEnsino = criarFormaAPartirDTO(dto);

		formaOrganEnsino = repository.save(formaOrganEnsino);
		return new FormaOrganEnsinoDTO(formaOrganEnsino);
	}

	private FormaOrganEnsino criarFormaAPartirDTO(FormaOrganEnsinoDTO dto) {
		FormaOrganEnsino formaOrganEnsino = new FormaOrganEnsino();
		BeanUtils.copyProperties(dto, formaOrganEnsino, "idFormaOrganEnsino", "dataCadastro");
		formaOrganEnsino.setDataCadastro(LocalDateTime.now());
		return formaOrganEnsino;
	}

	@Transactional
	public FormaOrganEnsinoDTO atualizar(FormaOrganEnsinoDTO dto) {
		FormaOrganEnsino formaOrganEnsino = repository.getReferenceById(dto.getIdFormaOrganEnsino());
		atualizaDados(formaOrganEnsino, dto);
		return new FormaOrganEnsinoDTO(formaOrganEnsino);
	}

	private void validarFormaOrganEnsino(String formaOrganEnsino) {
		Optional<FormaOrganEnsino> formaOrganEnsinoExistente = repository.findByFormaOrganEnsino(formaOrganEnsino)
				.stream().findFirst();
		if (formaOrganEnsinoExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}

	private void atualizaDados(FormaOrganEnsino destino, FormaOrganEnsinoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idDestinacaoLixo", "dataCadastro");

	}

	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}

}
