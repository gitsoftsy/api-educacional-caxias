package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.NivelEscolaridadeDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.NivelEscolaridade;
import br.com.softsy.educacional.repository.NivelEscolaridadeRepository;

@Service
public class NivelEscolaridadeService {

	@Autowired
	private NivelEscolaridadeRepository repository;

	public List<NivelEscolaridade> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public NivelEscolaridadeDTO buscarPorId(Long id) {
		return new NivelEscolaridadeDTO(repository.getReferenceById(id));
	}

	@Transactional
	public NivelEscolaridadeDTO salvar(NivelEscolaridadeDTO dto) {
		validarNivelEscolaridade(dto.getNivelEscolaridade());

		NivelEscolaridade nivelEscolaridade = criarNivelEscolaridadeAPartirDTO(dto);

		nivelEscolaridade = repository.save(nivelEscolaridade);
		return new NivelEscolaridadeDTO(nivelEscolaridade);
	}

	private NivelEscolaridade criarNivelEscolaridadeAPartirDTO(NivelEscolaridadeDTO dto) {
		NivelEscolaridade nivelEscolaridade = new NivelEscolaridade();
		BeanUtils.copyProperties(dto, nivelEscolaridade, "idNivelEscolaridade", "ativo", "dataCadastro");
		nivelEscolaridade.setAtivo('S');
		nivelEscolaridade.setDataCadastro(LocalDateTime.now());
		return nivelEscolaridade;
	}

	@Transactional
	public NivelEscolaridadeDTO atualizar(NivelEscolaridadeDTO dto) {
		NivelEscolaridade nivelEscolaridade = repository.getReferenceById(dto.getIdNivelEscolaridade());
		atualizaDados(nivelEscolaridade, dto);
		return new NivelEscolaridadeDTO(nivelEscolaridade);
	}

	@Transactional
	public void ativaDesativa(char status, Long IdNivelEscolaridade) {
		NivelEscolaridade nivelEscolaridade = repository.getReferenceById(IdNivelEscolaridade);
		nivelEscolaridade.setAtivo(status);
	}

	private void validarNivelEscolaridade(String nivelEscolaridade) {
		Optional<NivelEscolaridade> nivelEscolaridadeExistente = repository.findByNivelEscolaridade(nivelEscolaridade)
				.stream().findFirst();
		if (nivelEscolaridadeExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}

	private void atualizaDados(NivelEscolaridade destino, NivelEscolaridadeDTO origem) {
		BeanUtils.copyProperties(origem, destino, "IdNivelEscolaridade", "ativo", "dataCadastro");

	}

}
