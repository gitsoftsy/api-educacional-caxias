package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroTurmaProfessorDTO;
import br.com.softsy.educacional.dto.TurmaProfessorDTO;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.TurmaDisciplina;
import br.com.softsy.educacional.model.TurmaProfessor;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.TurmaDisciplinaRepository;
import br.com.softsy.educacional.repository.TurmaProfessorRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class TurmaProfessorService {

    @Autowired
    private TurmaProfessorRepository repository;

    @Autowired
    private TurmaDisciplinaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public List<TurmaProfessorDTO> listarTudo() {
        List<TurmaProfessor> turmasProfessores = repository.findAll();
        return turmasProfessores.stream()
                .map(TurmaProfessorDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TurmaProfessorDTO> buscarPorIdProfessor(Long id) {
        List<TurmaProfessor> turmasProfessores = repository.findByProfessor_IdProfessor(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar turmas dos professores por ID"));
        return turmasProfessores.stream()
                .map(TurmaProfessorDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TurmaProfessorDTO salvar(CadastroTurmaProfessorDTO dto) {
        TurmaProfessor turmaProfessor = criarTurmaProfessorAPartirDTO(dto);
        turmaProfessor = repository.save(turmaProfessor);
        return new TurmaProfessorDTO(turmaProfessor);
    }

    @Transactional
    public TurmaProfessorDTO atualizar(CadastroTurmaProfessorDTO dto) {
        TurmaProfessor turmaProfessor = repository.getReferenceById(dto.getIdTurmaProfessor());
        atualizaDados(turmaProfessor, dto);
        return new TurmaProfessorDTO(turmaProfessor);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private TurmaProfessor criarTurmaProfessorAPartirDTO(CadastroTurmaProfessorDTO dto) {
        TurmaProfessor turmaProfessor = new TurmaProfessor();
        TurmaDisciplina turma = turmaRepository.findById(dto.getTurmaDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        turmaProfessor.setTurmaDisciplina(turma);
        turmaProfessor.setProfessor(professor);
        turmaProfessor.setTipoProfessor(dto.getTipoProfessor());
        turmaProfessor.setTipoVaga(dto.getTipoVaga());
        turmaProfessor.setDataCadastro(LocalDateTime.now());
        return turmaProfessor;
    }

    private void atualizaDados(TurmaProfessor destino, CadastroTurmaProfessorDTO origem) {
        destino.setTurmaDisciplina(turmaRepository.findById(origem.getTurmaDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")));
        destino.setProfessor(professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado")));
        destino.setTipoProfessor(origem.getTipoProfessor());
        destino.setTipoVaga(origem.getTipoVaga());
        destino.setDataCadastro(LocalDateTime.now());
    }
}
