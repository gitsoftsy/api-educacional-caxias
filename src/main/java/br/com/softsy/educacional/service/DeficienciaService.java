package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.DeficienciaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Deficiencia;
import br.com.softsy.educacional.repository.DeficienciaRepository;

@Service
public class DeficienciaService {

	@Autowired
	private DeficienciaRepository repository;

	public List<Deficiencia> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public DeficienciaDTO buscarPorId(Long id) {
		return new DeficienciaDTO(repository.getReferenceById(id));
	}

	@Transactional
	public DeficienciaDTO salvar(DeficienciaDTO dto) {
		validarDeficiencia(dto.getDeficiencia());

		Deficiencia deficiencia = criarDeficienciaAPartirDTO(dto);

		deficiencia = repository.save(deficiencia);
		return new DeficienciaDTO(deficiencia);
	}

	private Deficiencia criarDeficienciaAPartirDTO(DeficienciaDTO dto) {
		Deficiencia deficiencia = new Deficiencia();
		BeanUtils.copyProperties(dto, deficiencia, "idDeficiencia", "ativo", "dataCadastro");
		deficiencia.setAtivo('S');
		deficiencia.setDataCadastro(LocalDateTime.now());
		return deficiencia;
	}

	@Transactional
	public DeficienciaDTO atualizar(DeficienciaDTO dto) {
		Deficiencia deficiencia = repository.getReferenceById(dto.getIdDeficiencia());
		atualizaDados(deficiencia, dto);
		return new DeficienciaDTO(deficiencia);
	}

	@Transactional
	public void ativaDesativa(char status, Long IdDeficiencia) {
		Deficiencia deficiencia = repository.getReferenceById(IdDeficiencia);
		deficiencia.setAtivo(status);
	}

	private void validarDeficiencia(String deficiencia) {
		Optional<Deficiencia> deficienciaExistente = repository.findByDeficiencia(deficiencia).stream().findFirst();
		if (deficienciaExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}

	private void atualizaDados(Deficiencia destino, DeficienciaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idDeficiencia", "ativo", "dataCadastro");

	}

}
