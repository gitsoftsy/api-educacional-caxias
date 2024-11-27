package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CriterioAvaliacaoDTO;
import br.com.softsy.educacional.model.CriterioAvaliacao;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.repository.CriterioAvaliacaoRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class CriterioAvaliacaoService {

    @Autowired
    private CriterioAvaliacaoRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public List<CriterioAvaliacaoDTO> listarTudo() {
        List<CriterioAvaliacao> criteriosAvaliacao = repository.findAll();
        return criteriosAvaliacao.stream()
                .map(CriterioAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CriterioAvaliacaoDTO buscarPorId(Long id) {
        return new CriterioAvaliacaoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public CriterioAvaliacaoDTO salvar(CriterioAvaliacaoDTO dto) {
        CriterioAvaliacao criterioAvaliacao = criarCriterioAvaliacaoAPartirDTO(dto);
        criterioAvaliacao = repository.save(criterioAvaliacao);
        return new CriterioAvaliacaoDTO(criterioAvaliacao);
    }

    @Transactional
    public CriterioAvaliacaoDTO atualizar(CriterioAvaliacaoDTO dto) {
        CriterioAvaliacao criterioAvaliacao = repository.getReferenceById(dto.getIdCriterioAvaliacao());
        atualizaDados(criterioAvaliacao, dto);
        return new CriterioAvaliacaoDTO(criterioAvaliacao);
    }

    private CriterioAvaliacao criarCriterioAvaliacaoAPartirDTO(CriterioAvaliacaoDTO dto) {
        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        BeanUtils.copyProperties(dto, criterioAvaliacao, "idCriterioAvaliacao");
        criterioAvaliacao.setTurma(turma);
        criterioAvaliacao.setAtivo('S');
        return criterioAvaliacao;
    }

    private void atualizaDados(CriterioAvaliacao destino, CriterioAvaliacaoDTO origem) {
        destino.setTurma(turmaRepository.findById(origem.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idCriterioAvaliacao", "ativo");
    }
    

    @Transactional
    public void ativaDesativa(char status, Long id) {
        CriterioAvaliacao criterioAvaliacao = repository.getReferenceById(id);
        criterioAvaliacao.setAtivo(status);
    }
}