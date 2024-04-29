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
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.DisciplinaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private EscolaRepository escolaRepository;

	@Autowired
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

	@Transactional(readOnly = true)
	public List<DisciplinaDTO> listarTudo() {
		List<Disciplina> disciplinas = disciplinaRepository.findAll();
		return disciplinas.stream().map(DisciplinaDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<DisciplinaDTO> buscarPorIdEscola(Long id) {
		List<Disciplina> disciplinas = disciplinaRepository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar disciplinas por id de escola"));
		return disciplinas.stream().map(DisciplinaDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public DisciplinaDTO salvar(CadastroDisciplinaDTO dto) {

		validarDisciplina(dto.getDisciplina());

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
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository
				.findById(dto.getDependenciaAdmId())
				.orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		disciplina.setEscola(escola);
		disciplina.setDisciplina(dto.getDisciplina());
		disciplina.setNome(dto.getNome());
		disciplina.setCreditos(dto.getCreditos());
		disciplina.setHorasAula(dto.getHorasAula());
		disciplina.setHorasEstagio(dto.getHorasEstagio());
		disciplina.setHorasAtiv(dto.getHorasAtiv());
		disciplina.setHorasLab(dto.getHorasLab());
		disciplina.setDataCadastro(LocalDateTime.now());
		disciplina.setAtivo('S');
		disciplina.setDependenciaAdm(dependenciaAdm);
		return disciplina;
	}

	@Transactional
	public void ativaDesativa(char status, Long idDisciplina) {
		Disciplina disciplina = disciplinaRepository.getReferenceById(idDisciplina);
		disciplina.setAtivo(status);
	}

	private void validarDisciplina(String disciplina) {
		Optional<Disciplina> disciplinaExistente = disciplinaRepository.findByDisciplina(disciplina).stream()
				.findFirst();
		if (disciplinaExistente.isPresent()) {
			throw new UniqueException("Essa disciplina já existe.");
		}
	}

	private void atualizarDados(Disciplina destino, CadastroDisciplinaDTO origem) {
		Escola escola = escolaRepository.findById(origem.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository
				.findById(origem.getDependenciaAdmId())
				.orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setEscola(escola);
		destino.setDisciplina(origem.getDisciplina());
		destino.setNome(origem.getNome());
		destino.setCreditos(origem.getCreditos());
		destino.setHorasAula(origem.getHorasAula());
		destino.setHorasEstagio(origem.getHorasEstagio());
		destino.setHorasAtiv(origem.getHorasAtiv());
		destino.setHorasLab(origem.getHorasLab());
		destino.setDependenciaAdm(dependenciaAdm);
	}
}