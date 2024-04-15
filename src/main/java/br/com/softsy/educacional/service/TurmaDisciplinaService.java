package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroTurmaDisciplinaDTO;
import br.com.softsy.educacional.dto.TurmaDisciplinaDTO;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.model.PeriodoLetivo;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.TurmaDisciplina;
import br.com.softsy.educacional.repository.CurriculoRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.DisciplinaRepository;
import br.com.softsy.educacional.repository.PeriodoLetivoRepository;
import br.com.softsy.educacional.repository.TurmaDisciplinaRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class TurmaDisciplinaService {

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CurriculoRepository curriculoRepository;

    public List<TurmaDisciplinaDTO> listarTudo() {
        List<TurmaDisciplina> turmaDisciplinaList = turmaDisciplinaRepository.findAll();
        return turmaDisciplinaList.stream()
                .map(TurmaDisciplinaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TurmaDisciplinaDTO buscarPorId(Long id) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma disciplina não encontrada"));
        return new TurmaDisciplinaDTO(turmaDisciplina);
    }

    @Transactional
    public TurmaDisciplinaDTO salvar(CadastroTurmaDisciplinaDTO dto) {
        TurmaDisciplina turmaDisciplina = criarTurmaDisciplinaAPartirDTO(dto);
        turmaDisciplina = turmaDisciplinaRepository.save(turmaDisciplina);
        return new TurmaDisciplinaDTO(turmaDisciplina);
    }

    private TurmaDisciplina criarTurmaDisciplinaAPartirDTO(CadastroTurmaDisciplinaDTO dto) {
        TurmaDisciplina turmaDisciplina = new TurmaDisciplina();
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(dto.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Curriculo curriculo = curriculoRepository.findById(dto.getCurriculoId())
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));

        turmaDisciplina.setTurma(turma);
        turmaDisciplina.setDisciplina(disciplina);
        turmaDisciplina.setPeriodoLetivo(periodoLetivo);
        turmaDisciplina.setSerie(dto.getSerie());
        turmaDisciplina.setCurso(curso);
        turmaDisciplina.setCurriculo(curriculo);
        turmaDisciplina.setQtdAulasPrevistas(dto.getQtdAulasPrevistas());
        turmaDisciplina.setDtInicio(dto.getDtInicio());
        turmaDisciplina.setDtFim(dto.getDtFim());
        turmaDisciplina.setSituacao(dto.getSituacao());
        turmaDisciplina.setNivelPresenca(dto.getNivelPresenca());
        turmaDisciplina.setDuracaoAula(dto.getDuracaoAula());
        turmaDisciplina.setDataCadastro(LocalDateTime.now());
        turmaDisciplina.setAtivo('S');

        return turmaDisciplina;
    }

    @Transactional
    public TurmaDisciplinaDTO atualizar(CadastroTurmaDisciplinaDTO dto) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(dto.getIdTurmaDisciplina())
                .orElseThrow(() -> new IllegalArgumentException("Turma disciplina não encontrada"));

        atualizaDados(turmaDisciplina, dto);

        return new TurmaDisciplinaDTO(turmaDisciplina);
    }

    @Transactional
    public void ativaDesativa(char status, Long idTurmaDisciplina) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(idTurmaDisciplina)
                .orElseThrow(() -> new IllegalArgumentException("Turma disciplina não encontrada"));

        turmaDisciplina.setAtivo(status);
    }

    private void atualizaDados(TurmaDisciplina destino, CadastroTurmaDisciplinaDTO origem) {
        Turma turma = turmaRepository.findById(origem.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        Disciplina disciplina = disciplinaRepository.findById(origem.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(origem.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));
        Curso curso = cursoRepository.findById(origem.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Curriculo curriculo = curriculoRepository.findById(origem.getCurriculoId())
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));

        BeanUtils.copyProperties(origem, destino, "idTurmaDisciplina", "dataCadastro", "ativo");

        destino.setTurma(turma);
        destino.setDisciplina(disciplina);
        destino.setPeriodoLetivo(periodoLetivo);
        destino.setSerie(origem.getSerie());
        destino.setCurso(curso);
        destino.setCurriculo(curriculo);
        destino.setQtdAulasPrevistas(origem.getQtdAulasPrevistas());
        destino.setDtInicio(origem.getDtInicio());
        destino.setDtFim(origem.getDtFim());
        destino.setSituacao(origem.getSituacao());
        destino.setNivelPresenca(origem.getNivelPresenca());
        destino.setDuracaoAula(origem.getDuracaoAula());
    }
}