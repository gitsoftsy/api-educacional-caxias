package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroTurmaDTO;
import br.com.softsy.educacional.dto.ModalidadeEscolaDTO;
import br.com.softsy.educacional.dto.TurmaDTO;
import br.com.softsy.educacional.model.AnoEscolar;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.FormaOrganEnsino;
import br.com.softsy.educacional.model.GradeCurricular;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.model.PeriodoLetivo;
import br.com.softsy.educacional.model.TipoAtendimento;
import br.com.softsy.educacional.model.TipoDeMedicao;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.AnoEscolarRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FormaOrganEnsinoRepository;
import br.com.softsy.educacional.repository.GradeCurricularRepository;
import br.com.softsy.educacional.repository.ModalidadeEscolaRepository;
import br.com.softsy.educacional.repository.PeriodoLetivoRepository;
import br.com.softsy.educacional.repository.TipoAtendimentoRepository;
import br.com.softsy.educacional.repository.TipoDeMedicaoRepository;
import br.com.softsy.educacional.repository.TurmaRepository;
import br.com.softsy.educacional.repository.TurnoRepository;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;
    
    @Autowired
    private GradeCurricularRepository gradeCurricularRepository;


    @Transactional(readOnly = true)
    public List<TurmaDTO> listarTudo() {
        List<Turma> turmas = turmaRepository.findAll();
        return turmas.stream()
                .map(TurmaDTO::new)
                .collect(Collectors.toList());
    }
    
	@Transactional(readOnly = true)
	public TurmaDTO buscarPorId(Long id){
		return new TurmaDTO(turmaRepository.getReferenceById(id));
	}


    @Transactional
    public TurmaDTO salvar(CadastroTurmaDTO dto) {
        Turma turma = criarTurmaAPartirDTO(dto);
        turma = turmaRepository.save(turma);
        return new TurmaDTO(turma);
    }

    @Transactional
    public TurmaDTO atualizar(CadastroTurmaDTO dto) {
        Turma turma = turmaRepository.getReferenceById(dto.getIdTurma());
        atualizaDados(turma, dto);
        return new TurmaDTO(turma);
    }

    @Transactional
    public void remover(Long id) {
        turmaRepository.deleteById(id);
    }

    private Turma criarTurmaAPartirDTO(CadastroTurmaDTO dto) {
        Turma turma = new Turma();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        Turno turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(dto.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));
        GradeCurricular gradeCurricular = gradeCurricularRepository.findById(dto.getGradeCurricularId())
                .orElseThrow(() -> new IllegalArgumentException("Grade curricular não encontrada"));

        turma.setEscola(escola);
        turma.setPeriodoLetivo(periodoLetivo);
        turma.setGradeCurricular(gradeCurricular);
        turma.setNomeTurma(dto.getNomeTurma());
        turma.setCodTurmaInep(dto.getCodTurmaInep());
        turma.setTurno(turno);
        turma.setLibras(dto.getLibras());
        turma.setDataCadastro(LocalDateTime.now());
        turma.setAtivo('S');
        turma.setVagas(dto.getVagas());
        turma.setControlaVagas(dto.getControlaVagas());
        return turma;
    }

    private void atualizaDados(Turma destino, CadastroTurmaDTO origem) {
        Escola escola = escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
      
        Turno turno = turnoRepository.findById(origem.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));

        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(origem.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));
        GradeCurricular gradeCurricular = gradeCurricularRepository.findById(origem.getGradeCurricularId())
                .orElseThrow(() -> new IllegalArgumentException("Grade curricular não encontrada"));

        destino.setEscola(escola);
        destino.setPeriodoLetivo(periodoLetivo);
        destino.setGradeCurricular(gradeCurricular);
        destino.setNomeTurma(origem.getNomeTurma());
        destino.setCodTurmaInep(origem.getCodTurmaInep());
        destino.setTurno(turno);
        destino.setLibras(origem.getLibras());
        destino.setDataCadastro(LocalDateTime.now());
        destino.setAtivo('S');
        destino.setVagas(origem.getVagas());
        destino.setControlaVagas(origem.getControlaVagas());
    }
    
    @Transactional
    public void ativaDesativa(char status, Long idTurma) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

        turma.setAtivo(status);
    }
    
}