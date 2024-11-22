package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroMatriculaDTO;
import br.com.softsy.educacional.dto.MatriculaDTO;
import br.com.softsy.educacional.dto.PrematriculaDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Matricula;
import br.com.softsy.educacional.model.PeriodoLetivo;
import br.com.softsy.educacional.model.Prematricula;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.TipoMatricula;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.MatriculaRepository;
import br.com.softsy.educacional.repository.PeriodoLetivoRepository;
import br.com.softsy.educacional.repository.TurmaRepository;
import br.com.softsy.educacional.repository.TipoMatriculaRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TipoMatriculaRepository tipoMatriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<MatriculaDTO> listarTudo() {
        List<Matricula> matriculas = repository.findAll();
        return matriculas.stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MatriculaDTO buscarPorId(Long id) {
        return new MatriculaDTO(repository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<MatriculaDTO> buscarPorIdConta(Long idConta) {
        List<Matricula> matricula = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID da conta"));
        return matricula.stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MatriculaDTO salvar(CadastroMatriculaDTO dto) {
        Matricula matricula = criarMatriculaAPartirDTO(dto);
        matricula = repository.save(matricula);
        return new MatriculaDTO(matricula);
    }

    private Matricula criarMatriculaAPartirDTO(CadastroMatriculaDTO dto) {
        Matricula matricula = new Matricula();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        TipoMatricula tipoMatricula = tipoMatriculaRepository.findById(dto.getTipoMatriculaId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Matrícula não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(dto.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período Letivo não encontrado"));
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
    Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        BeanUtils.copyProperties(dto, matricula, "idMatricula", "dataCadastro", "dataAtualizacao", "ativo");

        matricula.setConta(conta);
        matricula.setTipoMatricula(tipoMatricula);
        matricula.setAluno(aluno);
        matricula.setPeriodoLetivo(periodoLetivo);
        matricula.setTurma(turma);
        matricula.setUsuario(usuario);
        matricula.setAtivo('S');
        matricula.setDataCadastro(LocalDateTime.now());

        return matricula;
    }

    @Transactional
    public MatriculaDTO atualizar(CadastroMatriculaDTO dto) {
        Matricula matricula = repository.getReferenceById(dto.getIdMatricula());
        atualizaDados(matricula, dto);
        matricula = repository.save(matricula);
        return new MatriculaDTO(matricula);
    }

    private void atualizaDados(Matricula destino, CadastroMatriculaDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        TipoMatricula tipoMatricula = tipoMatriculaRepository.findById(origem.getTipoMatriculaId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Matrícula não encontrado"));
        Aluno aluno = alunoRepository.findById(origem.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(origem.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período Letivo não encontrado"));
        Turma turma = turmaRepository.findById(origem.getTurmaId())
                    .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        BeanUtils.copyProperties(origem, destino, "idMatricula", "dataCadastro", "dataAtualizacao", "ativo");

        destino.setConta(conta);
        destino.setTipoMatricula(tipoMatricula);
        destino.setAluno(aluno);
        destino.setPeriodoLetivo(periodoLetivo);
        destino.setTurma(turma);
        destino.setUsuario(usuario);
    }

    @Transactional
    public void ativaDesativa(char status, Long idMatricula) {
        Matricula matricula = repository.getReferenceById(idMatricula);
        matricula.setAtivo(status);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
