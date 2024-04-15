package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ProfessorDisciplinaDTO;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.ProfessorDisciplina;
import br.com.softsy.educacional.repository.DisciplinaRepository;
import br.com.softsy.educacional.repository.ProfessorDisciplinaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;

@Service
public class ProfessorDisciplinaService {

    @Autowired
    private ProfessorDisciplinaRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Transactional(readOnly = true)
    public List<ProfessorDisciplinaDTO> listarTudo() {
        List<ProfessorDisciplina> professorDisciplinas = repository.findAll();
        return professorDisciplinas.stream()
                .map(ProfessorDisciplinaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProfessorDisciplinaDTO buscarPorId(Long id) {
        return new ProfessorDisciplinaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public ProfessorDisciplinaDTO salvar(ProfessorDisciplinaDTO dto) {
        ProfessorDisciplina professorDisciplina = criarProfessorDisciplinaAPartirDTO(dto);
        professorDisciplina = repository.save(professorDisciplina);
        return new ProfessorDisciplinaDTO(professorDisciplina);
    }

    @Transactional
    public ProfessorDisciplinaDTO atualizar(ProfessorDisciplinaDTO dto) {
        ProfessorDisciplina professorDisciplina = repository.getReferenceById(dto.getIdProfessorDisciplina());
        atualizaDados(professorDisciplina, dto);
        return new ProfessorDisciplinaDTO(professorDisciplina);
    }

    private ProfessorDisciplina criarProfessorDisciplinaAPartirDTO(ProfessorDisciplinaDTO dto) {
        ProfessorDisciplina professorDisciplina = new ProfessorDisciplina();
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        BeanUtils.copyProperties(dto, professorDisciplina, "idProfessorDisciplina", "dataCadastro", "ativo");
        professorDisciplina.setProfessor(professor);
        professorDisciplina.setDisciplina(disciplina);
        professorDisciplina.setDataCadastro(LocalDateTime.now());
        professorDisciplina.setAtivo('S');
        return professorDisciplina;
    }

    private void atualizaDados(ProfessorDisciplina destino, ProfessorDisciplinaDTO origem) {
        destino.setProfessor(professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado")));
        destino.setDisciplina(disciplinaRepository.findById(origem.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idProfessorDisciplina", "dataCadastro", "ativo");
    }
    
	@Transactional
	public void ativaDesativa(char status, Long idProfessorDisciplina) {
		ProfessorDisciplina professorDisciplina = repository.getReferenceById(idProfessorDisciplina);
		professorDisciplina.setAtivo(status);
	}
	

}
