package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AlunoDTO;
import br.com.softsy.educacional.dto.CadastroNotaDTO;
import br.com.softsy.educacional.dto.NotaDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Nota;
import br.com.softsy.educacional.model.Prova;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.NotaRepository;
import br.com.softsy.educacional.repository.ProvaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProvaRepository provaRepository;

    @Transactional(readOnly = true)
    public List<NotaDTO> listarTudo() {
        List<Nota> notas = repository.findAll();
        return notas.stream()
                .map(NotaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotaDTO buscarPorId(Long id) {
        Nota nota = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));
        return new NotaDTO(nota);
    }
    
    @Transactional(readOnly = true)
    public List<NotaDTO> buscarPorIdAluno(Long idConta) {
        List<Nota> notas = repository.findByAluno_IdAluno(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar nota por ID do aluno"));
        return notas.stream()
                .map(NotaDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public NotaDTO salvar(CadastroNotaDTO dto) {
        Nota nota = criarNotaAPartirDTO(dto);
        nota = repository.save(nota);
        return new NotaDTO(nota);
    }

    private Nota criarNotaAPartirDTO(CadastroNotaDTO dto) {
        Nota nota = new Nota();

        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Prova prova = provaRepository.findById(dto.getProvaId())
                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

        BeanUtils.copyProperties(dto, nota, "idNota", "dataCadastro");

        nota.setAluno(aluno);
        nota.setProva(prova);
        nota.setDataCadastro(LocalDateTime.now());
        return nota;
    }

    @Transactional
    public NotaDTO atualizar(CadastroNotaDTO dto) {
        Nota nota = repository.findById(dto.getIdNota())
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));

        atualizaDados(nota, dto);

        nota = repository.save(nota);

        return new NotaDTO(nota);
    }

    private void atualizaDados(Nota destino, CadastroNotaDTO origem) {
        Aluno aluno = alunoRepository.findById(origem.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Prova prova = provaRepository.findById(origem.getProvaId())
                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

        BeanUtils.copyProperties(origem, destino, "idNota", "dataCadastro");

        destino.setAluno(aluno);
        destino.setProva(prova);
    }

}