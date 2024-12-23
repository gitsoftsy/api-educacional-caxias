package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroPrematriculaDTO;
import br.com.softsy.educacional.dto.PrematriculaDTO;
import br.com.softsy.educacional.model.Prematricula;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.model.PeriodoLetivo;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.Serie;
import br.com.softsy.educacional.model.TipoMatricula;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.PrematriculaRepository;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DisciplinaRepository;
import br.com.softsy.educacional.repository.PeriodoLetivoRepository;
import br.com.softsy.educacional.repository.TurmaRepository;
import br.com.softsy.educacional.repository.SerieRepository;
import br.com.softsy.educacional.repository.TipoMatriculaRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class PrematriculaService {

    @Autowired
    private PrematriculaRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TipoMatriculaRepository tipoMatriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<PrematriculaDTO> listarTudo() {
        List<Prematricula> prematricula = repository.findAll();
        return prematricula.stream()
                .map(PrematriculaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PrematriculaDTO buscarPorId(Long id) {
        return new PrematriculaDTO(repository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<PrematriculaDTO> buscarPorIdConta(Long idConta) {
        List<Prematricula> prematricula = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID da conta"));
        return prematricula.stream()
                .map(PrematriculaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PrematriculaDTO salvar(CadastroPrematriculaDTO dto) {
        Prematricula prematricula = criarPrematriculaAPartirDTO(dto);
        prematricula = repository.save(prematricula);
        return new PrematriculaDTO(prematricula);
    }

    private Prematricula criarPrematriculaAPartirDTO(CadastroPrematriculaDTO dto) {
        Prematricula prematricula = new Prematricula();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        TipoMatricula tipoMatricula = tipoMatriculaRepository.findById(dto.getTipoMatriculaId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Matrícula não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(dto.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período Letivo não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        BeanUtils.copyProperties(dto, prematricula, "idPrematricula", "dataCadastro", "dataAtualizacao", "ativo");

        prematricula.setConta(conta);
        prematricula.setTipoMatricula(tipoMatricula);
        prematricula.setAluno(aluno);
        prematricula.setPeriodoLetivo(periodoLetivo);
        prematricula.setDisciplina(disciplina);
        prematricula.setTurma(turma);
        prematricula.setSerie(serie);
        prematricula.setUsuario(usuario);
        prematricula.setAtivo('S');
        prematricula.setDataCadastro(LocalDateTime.now());

        return prematricula;
    }

    @Transactional
    public PrematriculaDTO atualizar(CadastroPrematriculaDTO dto) {
        Prematricula prematricula = repository.getReferenceById(dto.getIdPrematricula());
        atualizaDados(prematricula, dto);
        return new PrematriculaDTO(prematricula);
    }

    private void atualizaDados(Prematricula destino, CadastroPrematriculaDTO origem) {
    	
    	 Prematricula prematricula = new Prematricula();
         Conta conta = contaRepository.findById(origem.getContaId())
                 .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
         TipoMatricula tipoMatricula = tipoMatriculaRepository.findById(origem.getTipoMatriculaId())
                 .orElseThrow(() -> new IllegalArgumentException("Tipo de Matrícula não encontrado"));
         Aluno aluno = alunoRepository.findById(origem.getAlunoId())
                 .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
         PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(origem.getPeriodoLetivoId())
                 .orElseThrow(() -> new IllegalArgumentException("Período Letivo não encontrado"));
         Disciplina disciplina = disciplinaRepository.findById(origem.getDisciplinaId())
                 .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
         Turma turma = turmaRepository.findById(origem.getTurmaId())
                 .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
         Serie serie = serieRepository.findById(origem.getSerieId())
                 .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));
         Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                 .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        BeanUtils.copyProperties(origem, destino, "idPrematricula", "dataCadastro", "dataAtualizacao", "ativo");

        destino.setConta(conta);
        destino.setTipoMatricula(tipoMatricula);
        destino.setAluno(aluno);
        destino.setPeriodoLetivo(periodoLetivo);
        destino.setDisciplina(disciplina);
        destino.setTurma(turma);
        destino.setSerie(serie);
        destino.setUsuario(usuario);
    }

    @Transactional
    public void ativaDesativa(char status, Long idPrematricula) {
        Prematricula prematricula = repository.getReferenceById(idPrematricula);
        prematricula.setAtivo(status);
    }
    
    @Transactional
    public void excluir(Long id) {
    	repository.deleteById(id);
    }
}
