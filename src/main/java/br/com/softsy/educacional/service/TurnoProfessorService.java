package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TurnoProfessorDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.TurnoProfessor;
import br.com.softsy.educacional.repository.TurnoProfessorRepository;

@Service
public class TurnoProfessorService {

	@Autowired
	private TurnoProfessorRepository repository;

	public List<TurnoProfessor> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public TurnoProfessorDTO buscarPorId(Long id) {
		return new TurnoProfessorDTO(repository.getReferenceById(id));
	}

	@Transactional
	public TurnoProfessorDTO salvar(TurnoProfessorDTO dto) {
		validarTurnoProfessor(dto.getTurnoProfessor());

		TurnoProfessor turnoProfessor = criarTurnoProfessorAPartirDTO(dto);

		turnoProfessor = repository.save(turnoProfessor);
		return new TurnoProfessorDTO(turnoProfessor);
	}

	private TurnoProfessor criarTurnoProfessorAPartirDTO(TurnoProfessorDTO dto) {
		TurnoProfessor turnoProfessor = new TurnoProfessor();
		BeanUtils.copyProperties(dto, turnoProfessor, "idTurnoProfessor", "ativo", "dataCadastro");
		turnoProfessor.setAtivo('S');
		turnoProfessor.setDataCadastro(LocalDateTime.now());
		return turnoProfessor;
	}

	@Transactional
	public TurnoProfessorDTO atualizar(TurnoProfessorDTO dto) {
		TurnoProfessor turnoProfessor = repository.getReferenceById(dto.getIdTurnoProfessor());
		atualizaDados(turnoProfessor, dto);
		return new TurnoProfessorDTO(turnoProfessor);
	}

	@Transactional
	public void ativaDesativa(char status, Long idTurnoProfessor) {
		TurnoProfessor turnoProfessor = repository.getReferenceById(idTurnoProfessor);
		turnoProfessor.setAtivo(status);
	}

	private void validarTurnoProfessor(String turnoProfessor) {
		Optional<TurnoProfessor> turnoProfessorExistente = repository.findByTurnoProfessor(turnoProfessor).stream()
				.findFirst();
		if (turnoProfessorExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}

	private void atualizaDados(TurnoProfessor destino, TurnoProfessorDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTurnoProfessor", "ativo", "dataCadastro");

	}
}
