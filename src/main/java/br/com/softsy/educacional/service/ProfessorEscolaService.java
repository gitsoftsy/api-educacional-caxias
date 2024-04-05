package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProfessorEscolaDTO;
import br.com.softsy.educacional.dto.ProfessorEscolaDTO;
import br.com.softsy.educacional.model.CargoProfessor;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.ProfessorEscola;
import br.com.softsy.educacional.model.TurnoProfessor;
import br.com.softsy.educacional.repository.CargoProfessorRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.ProfessorEscolaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.TurnoProfessorRepository;

@Service
public class ProfessorEscolaService {

    @Autowired
    private ProfessorEscolaRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TurnoProfessorRepository turnoProfessorRepository;

    @Autowired
    private CargoProfessorRepository cargoProfessorRepository;

    @Transactional(readOnly = true)
    public List<ProfessorEscolaDTO> listarTudo() {
        List<ProfessorEscola> professorEscolas = repository.findAll();
        return professorEscolas.stream()
                .map(ProfessorEscolaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProfessorEscolaDTO> buscarPorIdProfessor(Long id) {
        List<ProfessorEscola> professorEscolas = repository.findByProfessor_IdProfessor(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar professores escolas por ID do professor"));
        return professorEscolas.stream()
                .map(ProfessorEscolaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void ativaDesativa(char status, Long idProfessorEscola) {
        ProfessorEscola professorEscola = repository.getReferenceById(idProfessorEscola);
        professorEscola.setAtivo(status);
    }

    @Transactional
    public ProfessorEscolaDTO salvar(CadastroProfessorEscolaDTO dto) {
        ProfessorEscola professorEscola = criarProfessorEscolaAPartirDTO(dto);
        professorEscola = repository.save(professorEscola);
        return new ProfessorEscolaDTO(professorEscola);
    }

    @Transactional
    public ProfessorEscolaDTO atualizar(CadastroProfessorEscolaDTO dto) {
        ProfessorEscola professorEscola = repository.getReferenceById(dto.getIdProfessorEscola());
        atualizaDados(professorEscola, dto);
        return new ProfessorEscolaDTO(professorEscola);
    }

    private ProfessorEscola criarProfessorEscolaAPartirDTO(CadastroProfessorEscolaDTO dto) {
        ProfessorEscola professorEscola = new ProfessorEscola();
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        TurnoProfessor turnoProfessor = turnoProfessorRepository.findById(dto.getTurnoProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Turno do professor não encontrado"));
        CargoProfessor cargoProfessor = cargoProfessorRepository.findById(dto.getCargoProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Cargo do professor não encontrado"));
        professorEscola.setProfessor(professor);
        professorEscola.setEscola(escola);
        professorEscola.setTurnoProfessor(turnoProfessor);
        professorEscola.setCargoProfessor(cargoProfessor);
        professorEscola.setDtNomenclatura(dto.getDtNomenclatura());
        professorEscola.setDataCadastro(LocalDateTime.now());
        professorEscola.setAtivo('S');
        return professorEscola;
    }

    private void atualizaDados(ProfessorEscola destino, CadastroProfessorEscolaDTO origem) {
        destino.setProfessor(professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado")));
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setTurnoProfessor(turnoProfessorRepository.findById(origem.getTurnoProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Turno do professor não encontrado")));
        destino.setCargoProfessor(cargoProfessorRepository.findById(origem.getCargoProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Cargo do professor não encontrado")));
        destino.setDtNomenclatura(origem.getDtNomenclatura());
        destino.setDataCadastro(LocalDateTime.now());
    }
}
