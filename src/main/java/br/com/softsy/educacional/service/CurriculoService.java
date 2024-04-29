package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.repository.CurriculoRepository;
import br.com.softsy.educacional.repository.CursoRepository;

@Service
public class CurriculoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CurriculoRepository curriculoRepository;

	@Transactional(readOnly = true)
	public List<CurriculoDTO> listarTudo() {
		List<Curriculo> curriculos = curriculoRepository.findAll();
		return curriculos.stream().map(CurriculoDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CurriculoDTO buscarPorId(Long id) {
		Curriculo curriculo = curriculoRepository.getReferenceById(id);
		return new CurriculoDTO(curriculo);
	}

	@Transactional
	public CurriculoDTO salvar(CurriculoDTO dto) {
		Curriculo curriculo = criarCurriculoAPartirDTO(dto);
		curriculo = curriculoRepository.save(curriculo);
		return new CurriculoDTO(curriculo);
	}

	@Transactional
	public CurriculoDTO atualizar(CurriculoDTO dto) {
		Curriculo curriculo = curriculoRepository.findById(dto.getIdCurriculo())
				.orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));
		atualizarDados(curriculo, dto);
		return new CurriculoDTO(curriculo);
	}

	@Transactional
	public void ativaDesativa(char status, Long idCurriculo) {
		Curriculo curriculo = curriculoRepository.getReferenceById(idCurriculo);
		curriculo.setAtivo(status);
	}

	private Curriculo criarCurriculoAPartirDTO(CurriculoDTO dto) {
		Curriculo curriculo = new Curriculo();
		Curso curso = cursoRepository.findById(dto.getCursoId())
				.orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
		curriculo.setCurso(curso);
		curriculo.setCurriculo(dto.getCurriculo());
		curriculo.setDtHomologacao(dto.getDtHomologacao());
		curriculo.setDtExtincao(dto.getDtExtincao());
		curriculo.setPrazoIdeal(dto.getPrazoIdeal());
		curriculo.setPrazoMax(dto.getPrazoMax());
		curriculo.setCreditos(dto.getCreditos());
		curriculo.setAulasPrevistas(dto.getAulasPrevistas());
		curriculo.setDataCadastro(LocalDateTime.now());
		curriculo.setAtivo('S');
		return curriculo;
	}

	private void atualizarDados(Curriculo destino, CurriculoDTO origem) {
		Curso curso = cursoRepository.findById(origem.getCursoId())
				.orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
		destino.setCurso(curso);
		destino.setCurriculo(origem.getCurriculo());
		destino.setDtHomologacao(origem.getDtHomologacao());
		destino.setDtExtincao(origem.getDtExtincao());
		destino.setPrazoIdeal(origem.getPrazoIdeal());
		destino.setPrazoMax(origem.getPrazoMax());
		destino.setCreditos(origem.getCreditos());
		destino.setAulasPrevistas(origem.getAulasPrevistas());
		destino.setDataCadastro(origem.getDataCadastro());

	}
}