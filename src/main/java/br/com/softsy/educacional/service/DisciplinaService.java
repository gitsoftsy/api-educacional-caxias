package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroDisciplinaDTO;
import br.com.softsy.educacional.dto.DisciplinaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.repository.AreaConhecimentoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private AreaConhecimentoRepository areaConhecimentoRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Transactional(readOnly = true)
	public List<DisciplinaDTO> listarTudo() {
		List<Disciplina> disciplinas = disciplinaRepository.findAll();
		return disciplinas.stream().map(DisciplinaDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<DisciplinaDTO> buscarPorIdConta(Long id) {
		List<Disciplina> disciplinas = disciplinaRepository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar disciplinas por id de conta"));
		return disciplinas.stream().map(DisciplinaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<DisciplinaDTO> buscarPorIdAreaConhecimento(Long id) {
		List<Disciplina> disciplinas = disciplinaRepository.findByAreaConhecimento_IdAreaConhecimento(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar disciplinas por id de area conhecimento"));
		return disciplinas.stream().map(DisciplinaDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public DisciplinaDTO salvar(CadastroDisciplinaDTO dto) {

		validarDisciplina(dto.getCodDiscip());

		Disciplina disciplina = criarDisciplinaAPartirDTO(dto);
		disciplina = disciplinaRepository.save(disciplina);
		return new DisciplinaDTO(disciplina);
	}

	@Transactional
	public DisciplinaDTO atualizar(CadastroDisciplinaDTO dto) {
		Disciplina disciplina = disciplinaRepository.findById(dto.getIdDisciplina())
				.orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
		atualizarDados(disciplina, dto);
		return new DisciplinaDTO(disciplina);
	}

	private Disciplina criarDisciplinaAPartirDTO(CadastroDisciplinaDTO dto) {
		Disciplina disciplina = new Disciplina();
		Conta conta = contaRepository
				.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		AreaConhecimento areaConhecimento = areaConhecimentoRepository
				.findById(dto.getAreaConhecimentoId())
				.orElseThrow(() -> new IllegalArgumentException("Area conhecimento não encontrada"));
		disciplina.setAreaConhecimento(areaConhecimento);
		disciplina.setCodDiscip(dto.getCodDiscip());
		disciplina.setNome(dto.getNome());
		disciplina.setCreditos(dto.getCreditos());
		disciplina.setHorasAula(dto.getHorasAula());
		disciplina.setHorasEstagio(dto.getHorasEstagio());
		disciplina.setHorasAtiv(dto.getHorasAtiv());
		disciplina.setHorasLab(dto.getHorasLab());
		disciplina.setDataCadastro(LocalDateTime.now());
		disciplina.setHorasAno(dto.getHorasAno());
		disciplina.setHorasSemanal(dto.getHorasSemanal());
		disciplina.setAtivo('S');
		disciplina.setConta(conta);
		return disciplina;
	}

	@Transactional
	public void ativaDesativa(char status, Long idDisciplina) {
		Disciplina disciplina = disciplinaRepository.getReferenceById(idDisciplina);
		disciplina.setAtivo(status);
	}

	private void validarDisciplina(String codDisciplina) {
		Optional<Disciplina> disciplinaExistente = disciplinaRepository.findByCodDiscip(codDisciplina).stream()
				.findFirst();
		if (disciplinaExistente.isPresent()) {
			throw new UniqueException("Essa disciplina já existe.");
		}
	}

	private void atualizarDados(Disciplina destino, CadastroDisciplinaDTO origem) {
		AreaConhecimento areaConhecimento = areaConhecimentoRepository
				.findById(origem.getAreaConhecimentoId())
				.orElseThrow(() -> new IllegalArgumentException("Area conhecimento não encontrada"));
		Conta conta = contaRepository
				.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setAreaConhecimento(areaConhecimento);
		destino.setCodDiscip(origem.getCodDiscip());
		destino.setNome(origem.getNome());
		destino.setCreditos(origem.getCreditos());
		destino.setHorasAula(origem.getHorasAula());
		destino.setHorasEstagio(origem.getHorasEstagio());
		destino.setHorasAtiv(origem.getHorasAtiv());
		destino.setHorasLab(origem.getHorasLab());
		destino.setHorasAno(origem.getHorasAno());
		destino.setHorasSemanal(origem.getHorasSemanal());
		destino.setConta(conta);
	}
}