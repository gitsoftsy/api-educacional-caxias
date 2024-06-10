package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroConcursoDTO;
import br.com.softsy.educacional.dto.ConcursoDTO;
import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Concurso;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.PeriodoLetivo;
import br.com.softsy.educacional.repository.ConcursoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.PeriodoLetivoRepository;

@Service
public class ConcursoService {

    @Autowired
    private ConcursoRepository concursoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Transactional(readOnly = true)
    public List<ConcursoDTO> listarTudo() {
        List<Concurso> concursos = concursoRepository.findAll();
        return concursos.stream()
                .map(ConcursoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConcursoDTO buscarPorId(Long id) {
        return new ConcursoDTO(concursoRepository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<ConcursoDTO> buscarPorIdConta(Long idConta) {
        List<Concurso> curso = concursoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID da conta"));
        return curso.stream()
                .map(ConcursoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ConcursoDTO salvar(CadastroConcursoDTO dto) {

        Concurso concurso = criarConcursoAPartirDTO(dto);
        concurso = concursoRepository.save(concurso);
        return new ConcursoDTO(concurso);
    }

    @Transactional
    public ConcursoDTO atualizar(CadastroConcursoDTO dto) {
        Concurso concurso = concursoRepository.findById(dto.getIdConcurso())
                .orElseThrow(() -> new IllegalArgumentException("Concurso não encontrado"));
        atualizarDados(concurso, dto);
        concurso = concursoRepository.save(concurso);
        return new ConcursoDTO(concurso);
    }

    private Concurso criarConcursoAPartirDTO(CadastroConcursoDTO dto) {
        Concurso concurso = new Concurso();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(dto.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));
        

        concurso.setConta(conta);
        concurso.setConcurso(dto.getConcurso());
        concurso.setPeriodoLetivo(periodoLetivo);
        concurso.setDataAbertura(dto.getDataAbertura());
        concurso.setDataFechamento(dto.getDataFechamento());
        concurso.setDataCadastro(LocalDateTime.now());
        concurso.setAtivo('S');
        return concurso;
    }


    @Transactional
    public void ativaDesativa(char status, Long idConcurso) {
        Concurso concurso = concursoRepository.getReferenceById(idConcurso);
        concurso.setAtivo(status);
        concursoRepository.save(concurso);
    }

    private void atualizarDados(Concurso destino, CadastroConcursoDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(origem.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));

        destino.setConta(conta);
        destino.setConcurso(origem.getConcurso());
        destino.setPeriodoLetivo(periodoLetivo);
        destino.setDataAbertura(origem.getDataAbertura());
        destino.setDataFechamento(origem.getDataFechamento());
    }
}