package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AgendaDTO;
import br.com.softsy.educacional.model.Agenda;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.repository.AgendaRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public List<AgendaDTO> listarTudo() {
        List<Agenda> agendas = repository.findAll();
        return agendas.stream()
                .map(AgendaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendaDTO buscarPorId(Long id) {
        return new AgendaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public AgendaDTO salvar(AgendaDTO dto) {
        Agenda agenda = criarAgendaAPartirDTO(dto);
        agenda = repository.save(agenda);
        return new AgendaDTO(agenda);
    }

    @Transactional
    public AgendaDTO atualizar(AgendaDTO dto) {
        Agenda agenda = repository.getReferenceById(dto.getIdAgenda());
        atualizaDados(agenda, dto);
        return new AgendaDTO(agenda);
    }

    private Agenda criarAgendaAPartirDTO(AgendaDTO dto) {
        Agenda agenda = new Agenda();
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        BeanUtils.copyProperties(dto, agenda, "idAgenda");
        agenda.setTurma(turma);
        agenda.setAtivo('S');
        return agenda;
    }

    private void atualizaDados(Agenda destino, AgendaDTO origem) {
        destino.setTurma(turmaRepository.findById(origem.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idAgenda", "ativo");
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        Agenda agenda = repository.getReferenceById(id);
        agenda.setAtivo(status);
    }
}