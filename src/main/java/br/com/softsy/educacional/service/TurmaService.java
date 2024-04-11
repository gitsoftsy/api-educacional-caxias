package br.com.softsy.educacional.service;

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
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.model.TipoAtendimento;
import br.com.softsy.educacional.model.TipoDeMedicao;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.AnoEscolarRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FormaOrganEnsinoRepository;
import br.com.softsy.educacional.repository.ModalidadeEscolaRepository;
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
    private AnoEscolarRepository anoEscolarRepository;

    @Autowired
    private FormaOrganEnsinoRepository formaOrganEnsinoRepository;

    @Autowired
    private TipoDeMedicaoRepository tipoDeMedicaoRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private TipoAtendimentoRepository tipoAtendimentoRepository;

    @Autowired
    private ModalidadeEscolaRepository modalidadeEscolaRepository;

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
        AnoEscolar anoEscolar = anoEscolarRepository.findById(dto.getAnoEscolarId())
                .orElseThrow(() -> new IllegalArgumentException("Ano escolar não encontrado"));
        FormaOrganEnsino formaOrganEnsino = formaOrganEnsinoRepository.findById(dto.getFormaOrganEnsinoId())
                .orElseThrow(() -> new IllegalArgumentException("Forma de organização de ensino não encontrada"));
        TipoDeMedicao tipoDeMedicao = tipoDeMedicaoRepository.findById(dto.getTipoDeMedicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de medição não encontrado"));
        Turno turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        TipoAtendimento tipoAtendimento = tipoAtendimentoRepository.findById(dto.getTipoAtendimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de atendimento não encontrado"));
        ModalidadeEscola modalidadeEscola = modalidadeEscolaRepository.findById(dto.getModalidadeEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Modalidade de escola não encontrada"));

        turma.setEscola(escola);
        turma.setAnoEscolar(anoEscolar);
        turma.setNumTurma(dto.getNumTurma());
        turma.setCodTurmaInep(dto.getCodTurmaInep());
        turma.setFormaOrganEnsino(formaOrganEnsino);
        turma.setTipoDeMedicao(tipoDeMedicao);
        turma.setTurno(turno);
        turma.setTipoAtendimento(tipoAtendimento);
        turma.setModalidadeEscola(modalidadeEscola);
        turma.setLibras(dto.getLibras());
        return turma;
    }

    private void atualizaDados(Turma destino, CadastroTurmaDTO origem) {
        Escola escola = escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        AnoEscolar anoEscolar = anoEscolarRepository.findById(origem.getAnoEscolarId())
                .orElseThrow(() -> new IllegalArgumentException("Ano escolar não encontrado"));
        FormaOrganEnsino formaOrganEnsino = formaOrganEnsinoRepository.findById(origem.getFormaOrganEnsinoId())
                .orElseThrow(() -> new IllegalArgumentException("Forma de organização de ensinode Ensino não encontrada"));
        TipoDeMedicao tipoDeMedicao = tipoDeMedicaoRepository.findById(origem.getTipoDeMedicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de medição não encontrado"));
        Turno turno = turnoRepository.findById(origem.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        TipoAtendimento tipoAtendimento = tipoAtendimentoRepository.findById(origem.getTipoAtendimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de atendimento não encontrado"));
        ModalidadeEscola modalidadeEscola = modalidadeEscolaRepository.findById(origem.getModalidadeEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Modalidade de escola não encontrada"));

        destino.setEscola(escola);
        destino.setAnoEscolar(anoEscolar);
        destino.setNumTurma(origem.getNumTurma());
        destino.setCodTurmaInep(origem.getCodTurmaInep());
        destino.setFormaOrganEnsino(formaOrganEnsino);
        destino.setTipoDeMedicao(tipoDeMedicao);
        destino.setTurno(turno);
        destino.setTipoAtendimento(tipoAtendimento);
        destino.setModalidadeEscola(modalidadeEscola);
        destino.setLibras(origem.getLibras());
    }
}