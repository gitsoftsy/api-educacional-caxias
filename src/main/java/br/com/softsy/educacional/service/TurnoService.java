package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDTO;
import br.com.softsy.educacional.dto.TurnoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.Equipamento;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TurnoRepository;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository repository;

	@Autowired
	private ContaRepository contaRepository;

	public List<TurnoDTO> listarTudo() {
		List<Turno> turnos = repository.findAll();
		return turnos.stream().map(TurnoDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public TurnoDTO buscarPorId(Long id) {
		Turno turno = repository.getReferenceById(id);
		return new TurnoDTO(turno);
	}

	@Transactional(readOnly = true)
	public List<TurnoDTO> buscarPorIdConta(Long idConta) {
		List<Turno> turno = repository.findByConta_idConta(idConta)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar turno por ID da conta"));
		return turno.stream().map(TurnoDTO::new).collect(Collectors.toList());
	}

	
	@Transactional(readOnly = true)
	public List<TurnoDTO> buscarTurnosAtivosPorIdConta(Long idConta) {
		if (idConta == null) {
			throw new IllegalArgumentException("O ID da conta é obrigatório.");
		}

		Character ativo = 'S';
		List<Turno> turnos = repository.findActiveTurnoByConta_IdContaAndAtivo(idConta, ativo).orElseThrow(
				() -> new IllegalArgumentException("Nenhum turno ativo encontrado para a conta informada."));

		return turnos.stream().map(TurnoDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public TurnoDTO salvar(TurnoDTO dto) {
		Turno turno = criarTurnoAPartirDTO(dto);
		turno = repository.save(turno);
		return new TurnoDTO(turno);
	}

	@Transactional
	public TurnoDTO atualizar(TurnoDTO dto) {
		Turno turno = repository.getReferenceById(dto.getIdTurno());
		atualizarDados(turno, dto);
		return new TurnoDTO(turno);
	}

	@Transactional
	public void ativarDesativar(char status, Long idTurno) {
		Turno turno = repository.getReferenceById(idTurno);
		turno.setAtivo(status);
	}

	private Turno criarTurnoAPartirDTO(TurnoDTO dto) {
		Turno turno = new Turno();
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, turno, "idTurno", "dataCadastro", "ativo");
		turno.setConta(conta);
		turno.setDataCadastro(LocalDateTime.now());
		turno.setAtivo('S');
		return turno;
	}

	private void atualizarDados(Turno destino, TurnoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTurno", "dataCadastro", "ativo");
		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
	}

}