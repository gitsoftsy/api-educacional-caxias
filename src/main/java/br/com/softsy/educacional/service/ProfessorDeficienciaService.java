package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProfessorDeficienciaDTO;
import br.com.softsy.educacional.dto.ProfessorDeficienciaDTO;
import br.com.softsy.educacional.model.Deficiencia;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.ProfessorDeficiencia;
import br.com.softsy.educacional.repository.DeficienciaRepository;
import br.com.softsy.educacional.repository.ProfessorDeficienciaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;

@Service
public class ProfessorDeficienciaService {

    @Autowired
    private ProfessorDeficienciaRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DeficienciaRepository deficienciaRepository;

    @Transactional(readOnly = true)
    public List<ProfessorDeficienciaDTO> listarTudo() {
        List<ProfessorDeficiencia> professoresDeficiencia = repository.findAll();
        return professoresDeficiencia.stream()
                .map(ProfessorDeficienciaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProfessorDeficienciaDTO> buscarPorIdProfessor(Long id) {
        List<ProfessorDeficiencia> professoresDeficiencia = repository.findByProfessorDeficiencia_IdProfessor(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar deficiências do professor por ID"));
        return professoresDeficiencia.stream()
                .map(ProfessorDeficienciaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProfessorDeficienciaDTO salvar(CadastroProfessorDeficienciaDTO dto) {
        ProfessorDeficiencia professorDeficiencia = criarProfessorDeficienciaAPartirDTO(dto);
        professorDeficiencia = repository.save(professorDeficiencia);
        return new ProfessorDeficienciaDTO(professorDeficiencia);
    }

    @Transactional
    public ProfessorDeficienciaDTO atualizar(CadastroProfessorDeficienciaDTO dto) {
        ProfessorDeficiencia professorDeficiencia = repository.getReferenceById(dto.getIdProfessorDeficiencia());
        atualizaDados(professorDeficiencia, dto);
        return new ProfessorDeficienciaDTO(professorDeficiencia);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private ProfessorDeficiencia criarProfessorDeficienciaAPartirDTO(CadastroProfessorDeficienciaDTO dto) {
        ProfessorDeficiencia professorDeficiencia = new ProfessorDeficiencia();
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        Deficiencia deficiencia = deficienciaRepository.findById(dto.getDeficienciaId())
                .orElseThrow(() -> new IllegalArgumentException("Deficiência não encontrada"));
        professorDeficiencia.setProfessor(professor);
        professorDeficiencia.setDeficiencia(deficiencia);
        professorDeficiencia.setDataCadastro(LocalDateTime.now());
        return professorDeficiencia;
    }

    private void atualizaDados(ProfessorDeficiencia destino, CadastroProfessorDeficienciaDTO origem) {
        destino.setProfessor(professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado")));
        destino.setDeficiencia(deficienciaRepository.findById(origem.getDeficienciaId())
                .orElseThrow(() -> new IllegalArgumentException("Deficiência não encontrada")));
    }
}