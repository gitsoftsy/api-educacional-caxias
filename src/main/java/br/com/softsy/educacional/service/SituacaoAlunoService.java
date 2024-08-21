package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.softsy.educacional.dto.AreaConhecimentoDTO;
import br.com.softsy.educacional.dto.SituacaoAlunoDTO;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.SituacaoAluno;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.SituacaoAlunoRepository;

@Service
public class SituacaoAlunoService {

    @Autowired
    private SituacaoAlunoRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<SituacaoAlunoDTO> listarTudo() {
        List<SituacaoAluno> situacoes = repository.findAll();
        return situacoes.stream()
                .map(SituacaoAlunoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SituacaoAlunoDTO buscarPorId(Long id) {
        return new SituacaoAlunoDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Situação do Aluno não encontrada")));
    }
    
    @Transactional(readOnly = true)
    public List<SituacaoAlunoDTO> buscarPorIdConta(Long idConta) {
        List<SituacaoAluno> areaConhecimento = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar situação do aluno por ID da conta"));
        return areaConhecimento.stream()
                .map(SituacaoAlunoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SituacaoAlunoDTO salvar(SituacaoAlunoDTO dto) {
        SituacaoAluno situacaoAluno = criarSituacaoAlunoAPartirDTO(dto);
        situacaoAluno = repository.save(situacaoAluno);
        return new SituacaoAlunoDTO(situacaoAluno);
    }

    @Transactional
    public SituacaoAlunoDTO atualizar(SituacaoAlunoDTO dto) {
        SituacaoAluno situacaoAluno = repository.findById(dto.getIdSituacaoAluno())
                .orElseThrow(() -> new EntityNotFoundException("Situação do Aluno não encontrada"));
        atualizaDados(situacaoAluno, dto);
        situacaoAluno = repository.save(situacaoAluno);
        return new SituacaoAlunoDTO(situacaoAluno);
    }

    private SituacaoAluno criarSituacaoAlunoAPartirDTO(SituacaoAlunoDTO dto) {
        SituacaoAluno situacaoAluno = new SituacaoAluno();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        
        situacaoAluno.setConta(conta);
        
        situacaoAluno.setSituacaoAluno(dto.getSituacaoAluno());
        situacaoAluno.setDataCadastro(LocalDateTime.now());
        return situacaoAluno;
    }

    private void atualizaDados(SituacaoAluno destino, SituacaoAlunoDTO origem) {
            Conta conta = contaRepository.findById(origem.getContaId())
                    .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
            destino.setConta(conta);
        
        destino.setSituacaoAluno(origem.getSituacaoAluno());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}