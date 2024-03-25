package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroTurmaDTO;
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
    private TurmaRepository repository;

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

    public List<TurmaDTO> listarTudo() {
        List<Turma> turmas = repository.findAll();
        return turmas.stream().map(TurmaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TurmaDTO buscarPorId(Long id) {
        return new TurmaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public TurmaDTO salvar(CadastroTurmaDTO dto) {
        Turma turma = criarTurmaAPartirDTO(dto);
        turma = repository.save(turma);
        return new TurmaDTO(turma);
    }

    @Transactional
    public TurmaDTO atualizar(CadastroTurmaDTO dto) {
        Turma turma = repository.getReferenceById(dto.getIdTurma());
        atualizaDados(turma, dto);
        return new TurmaDTO(turma);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
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
                .orElseThrow(() -> new IllegalArgumentException("Tipo de medição não encontrada"));
        Turno turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        TipoAtendimento tipoAtendimento = tipoAtendimentoRepository.findById(dto.getTipoAtendimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de atendimento não encontrado"));
        ModalidadeEscola modalidadeEscola = modalidadeEscolaRepository.findById(dto.getModalidadeEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Modalidade de escola não encontrada"));


        turma.setEscola(escola);
        turma.setAnoEscolar(anoEscolar);
        turma.setFormaOrganEnsino(formaOrganEnsino);
        turma.setTipoDeMedicao(tipoDeMedicao);
        turma.setTurno(turno);
        turma.setTipoAtendimento(tipoAtendimento);
        turma.setModalidadeEscola(modalidadeEscola);

        return turma;
    }

    private void atualizaDados(Turma destino, CadastroTurmaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idTurma", "escolaId", "anoEscolarId", "formaOrganEnsinoId",
                "tipoDeMedicaoId", "turnoId", "tipoAtendimentoId", "modalidadeEscolaId");
    }
}