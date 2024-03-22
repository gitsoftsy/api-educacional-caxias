package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.NacionalidadeDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Nacionalidade;
import br.com.softsy.educacional.repository.NacionalidadeRepository;

@Service
public class NacionalidadeService {

	@Autowired
	private NacionalidadeRepository repository;

	public List<Nacionalidade> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public NacionalidadeDTO buscarPorId(Long id) {
		return new NacionalidadeDTO(repository.getReferenceById(id));
	}

	@Transactional
	public NacionalidadeDTO salvar(NacionalidadeDTO dto) {
		validarNacionalidade(dto.getNacionalidade());

		Nacionalidade nacionalidade = criarNacionalidadeAPartirDTO(dto);

		nacionalidade = repository.save(nacionalidade);
		return new NacionalidadeDTO(nacionalidade);
	}

	private Nacionalidade criarNacionalidadeAPartirDTO(NacionalidadeDTO dto) {
		Nacionalidade nacionalidade = new Nacionalidade();
		BeanUtils.copyProperties(dto, nacionalidade, "idNacionalidade", "ativo", "dataCadastro");
		nacionalidade.setAtivo('S');
		nacionalidade.setDataCadastro(LocalDateTime.now());
		return nacionalidade;
	}

	@Transactional
	public NacionalidadeDTO atualizar(NacionalidadeDTO dto) {
		Nacionalidade nacionalidade = repository.getReferenceById(dto.getIdNacionalidade());
		atualizaDados(nacionalidade, dto);
		return new NacionalidadeDTO(nacionalidade);
	}

	@Transactional
	public void ativaDesativa(char status, Long idNacionalidade) {
		Nacionalidade nacionalidade = repository.getReferenceById(idNacionalidade);
		nacionalidade.setAtivo(status);
	}

	private void validarNacionalidade(String nacionalidade) {
		Optional<Nacionalidade> nacionalidadeExistente = repository.findByNacionalidade(nacionalidade).stream()
				.findFirst();
		if (nacionalidadeExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}

	private void atualizaDados(Nacionalidade destino, NacionalidadeDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idNacionalidade", "ativo", "dataCadastro");

	}

}
