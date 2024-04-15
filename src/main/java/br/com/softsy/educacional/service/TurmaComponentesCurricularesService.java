package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroTurmaComponentesCurricularesDTO;
import br.com.softsy.educacional.dto.FormaOrganEnsinoDTO;
import br.com.softsy.educacional.dto.TurmaComponentesCurricularesDTO;
import br.com.softsy.educacional.model.ComponentesCurriculares;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.TurmaComponentesCurriculares;
import br.com.softsy.educacional.model.TurmaDisciplina;
import br.com.softsy.educacional.repository.ComponentesCurricularesRepository;
import br.com.softsy.educacional.repository.TurmaComponentesCurricularesRepository;
import br.com.softsy.educacional.repository.TurmaDisciplinaRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class TurmaComponentesCurricularesService {

    @Autowired
    private TurmaComponentesCurricularesRepository repository;

    @Autowired
    private TurmaDisciplinaRepository turmaRepository;

    @Autowired
    private ComponentesCurricularesRepository componentesCurricularesRepository;

    @Transactional(readOnly = true)
    public List<TurmaComponentesCurricularesDTO> listarTudo() {
        List<TurmaComponentesCurriculares> turmasComponentesCurriculares = repository.findAll();
        return turmasComponentesCurriculares.stream()
                .map(TurmaComponentesCurricularesDTO::new)
                .collect(Collectors.toList());
    }

	@Transactional(readOnly = true)
	public TurmaComponentesCurricularesDTO buscarPorId(Long id) {
		return new TurmaComponentesCurricularesDTO(repository.getReferenceById(id));
	}
	
    @Transactional
    public TurmaComponentesCurricularesDTO salvar(CadastroTurmaComponentesCurricularesDTO dto) {
        TurmaComponentesCurriculares turmaComponentesCurriculares = criarTurmaComponentesCurricularesAPartirDTO(dto);
        turmaComponentesCurriculares = repository.save(turmaComponentesCurriculares);
        return new TurmaComponentesCurricularesDTO(turmaComponentesCurriculares);
    }

    @Transactional
    public TurmaComponentesCurricularesDTO atualizar(CadastroTurmaComponentesCurricularesDTO dto) {
        TurmaComponentesCurriculares turmaComponentesCurriculares = repository.getReferenceById(dto.getIdTurmaComponentesCurriculares());
        atualizaDados(turmaComponentesCurriculares, dto);
        return new TurmaComponentesCurricularesDTO(turmaComponentesCurriculares);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private TurmaComponentesCurriculares criarTurmaComponentesCurricularesAPartirDTO(CadastroTurmaComponentesCurricularesDTO dto) {
        TurmaComponentesCurriculares turmaComponentesCurriculares = new TurmaComponentesCurriculares();
        TurmaDisciplina turma = turmaRepository.findById(dto.getTurmaDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        ComponentesCurriculares componentesCurriculares = componentesCurricularesRepository.findById(dto.getComponentesCurricularesId())
                .orElseThrow(() -> new IllegalArgumentException("Componentes curriculares não encontrados"));
        turmaComponentesCurriculares.setTurmaDisciplina(turma);
        turmaComponentesCurriculares.setComponentesCurriculares(componentesCurriculares);
        turmaComponentesCurriculares.setDataCadastro(LocalDateTime.now());
        return turmaComponentesCurriculares;
    }

    private void atualizaDados(TurmaComponentesCurriculares destino, CadastroTurmaComponentesCurricularesDTO origem) {
        destino.setTurmaDisciplina(turmaRepository.findById(origem.getTurmaDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")));
        destino.setComponentesCurriculares(componentesCurricularesRepository.findById(origem.getComponentesCurricularesId())
                .orElseThrow(() -> new IllegalArgumentException("Componentes curriculares não encontrados")));
    }
}
