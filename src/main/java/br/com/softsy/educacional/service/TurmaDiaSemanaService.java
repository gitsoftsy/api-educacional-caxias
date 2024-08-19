package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TurmaDiaSemanaDTO;
import br.com.softsy.educacional.model.EscolaDependencia;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.TurmaDiaSemana;
import br.com.softsy.educacional.model.TurmaDisciplina;
import br.com.softsy.educacional.model.TurmaProfessor;
import br.com.softsy.educacional.repository.EscolaDependenciaRepository;
import br.com.softsy.educacional.repository.TurmaDiaSemanaRepository;
import br.com.softsy.educacional.repository.TurmaDisciplinaRepository;
import br.com.softsy.educacional.repository.TurmaProfessorRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class TurmaDiaSemanaService {

    @Autowired
    private TurmaDiaSemanaRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;
    

    @Transactional(readOnly = true)
    public List<TurmaDiaSemanaDTO> listarTudo() {
        List<TurmaDiaSemana> turmasDiaSemana = repository.findAll();
        return turmasDiaSemana.stream()
                .map(TurmaDiaSemanaDTO::new)
                .collect(Collectors.toList());
    }

	@Transactional(readOnly = true)
	public TurmaDiaSemanaDTO buscarPorId(Long id) {
		return new TurmaDiaSemanaDTO(repository.getReferenceById(id));
	}
    
    @Transactional
    public TurmaDiaSemanaDTO salvar(TurmaDiaSemanaDTO dto) {
        TurmaDiaSemana turmaDiaSemana = criarTurmaDiaSemanaAPartirDTO(dto);
        turmaDiaSemana = repository.save(turmaDiaSemana);
        return new TurmaDiaSemanaDTO(turmaDiaSemana);
    }

    @Transactional
    public TurmaDiaSemanaDTO atualizar(TurmaDiaSemanaDTO dto) {
        TurmaDiaSemana turmaDiaSemana = repository.getReferenceById(dto.getIdTurmaDiaSemana());
        atualizaDados(turmaDiaSemana, dto);
        return new TurmaDiaSemanaDTO(turmaDiaSemana);
    }


    private TurmaDiaSemana criarTurmaDiaSemanaAPartirDTO(TurmaDiaSemanaDTO dto) {
        TurmaDiaSemana turmaDiaSemana = new TurmaDiaSemana();
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        BeanUtils.copyProperties(dto, turmaDiaSemana, "idTurmaDiaSemana", "dataCadastro");
        turmaDiaSemana.setTurma(turma);
        turmaDiaSemana.setDataCadastro(LocalDateTime.now());
        turmaDiaSemana.setAtivo('S');
        return turmaDiaSemana;
    }

    private void atualizaDados(TurmaDiaSemana destino, TurmaDiaSemanaDTO origem) {
        destino.setTurma(turmaRepository.findById(origem.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idTurmaDiaSemana", "dataCadastro", "ativo");
    }
    
	@Transactional
	public void ativaDesativa(char status, Long id) {
		TurmaDiaSemana turmaDiaSemana = repository.getReferenceById(id);
		turmaDiaSemana.setAtivo(status);
	}
    
}