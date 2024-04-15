package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroGradeCurricularDTO;
import br.com.softsy.educacional.dto.EscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.dto.GradeCurricularDTO;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.CursoSerie;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.model.GradeCurricular;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.CurriculoRepository;
import br.com.softsy.educacional.repository.CursoSerieRepository;
import br.com.softsy.educacional.repository.DisciplinaRepository;
import br.com.softsy.educacional.repository.GradeCurricularRepository;
import br.com.softsy.educacional.repository.TurnoRepository;
import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class GradeCurricularService {

    @Autowired
    private GradeCurricularRepository repository;

    @Autowired
    private CursoSerieRepository cursoSerieRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CurriculoRepository curriculoRepository;

    public List<GradeCurricularDTO> listarTudo() {
        List<GradeCurricular> gradeCurricularList = repository.findAll();
        return gradeCurricularList.stream()
                .map(GradeCurricularDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GradeCurricularDTO buscarPorId(Long id) {
        GradeCurricular gradeCurricular = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grade curricular não encontrada"));
        return new GradeCurricularDTO(gradeCurricular);
    }
   

    @Transactional
    public GradeCurricularDTO salvar(CadastroGradeCurricularDTO dto) {
        GradeCurricular gradeCurricular = criarGradeCurricularAPartirDTO(dto);
        gradeCurricular = repository.save(gradeCurricular);
        return new GradeCurricularDTO(gradeCurricular);
    }

    private GradeCurricular criarGradeCurricularAPartirDTO(CadastroGradeCurricularDTO dto) {
        GradeCurricular gradeCurricular = new GradeCurricular();
        CursoSerie cursoSerie = cursoSerieRepository.findById(dto.getCursoSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Curso/Série não encontrado"));
        Turno turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        Curriculo curriculo = curriculoRepository.findById(dto.getCurriculoId())
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));

        gradeCurricular.setCursoSerie(cursoSerie);
        gradeCurricular.setTurno(turno);
        gradeCurricular.setDisciplina(disciplina);
        gradeCurricular.setCurriculo(curriculo);
        gradeCurricular.setObrigatoria(dto.getObrigatoria());
        gradeCurricular.setRetemSerie(dto.getRetemSerie());
        gradeCurricular.setDataCadastro(LocalDateTime.now());
        gradeCurricular.setAtivo('S');

        return gradeCurricular;
    }

    @Transactional
    public GradeCurricularDTO atualizar(CadastroGradeCurricularDTO dto) {
        GradeCurricular gradeCurricular = repository.findById(dto.getIdGradeCurricular())
                .orElseThrow(() -> new IllegalArgumentException("Grade curricular não encontrada"));

        atualizaDados(gradeCurricular, dto);

        return new GradeCurricularDTO(gradeCurricular);
    }

    @Transactional
    public void ativaDesativa(char status, Long idGradeCurricular) {
        GradeCurricular gradeCurricular = repository.findById(idGradeCurricular)
                .orElseThrow(() -> new IllegalArgumentException("Grade curricular não encontrada"));

        gradeCurricular.setAtivo(status);
    }

    private void atualizaDados(GradeCurricular destino, CadastroGradeCurricularDTO origem) {
        CursoSerie cursoSerie = cursoSerieRepository.findById(origem.getCursoSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Curso/Série não encontrado"));
        Turno turno = turnoRepository.findById(origem.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(origem.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        Curriculo curriculo = curriculoRepository.findById(origem.getCurriculoId())
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));
        
        BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro");

        destino.setCursoSerie(cursoSerie);
        destino.setTurno(turno);
        destino.setDisciplina(disciplina);
        destino.setCurriculo(curriculo);
        destino.setObrigatoria(origem.getObrigatoria());
        destino.setRetemSerie(origem.getRetemSerie());
    }
}