package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroCursoDescrDTO;
import br.com.softsy.educacional.dto.CursoDescrDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.CursoDescr;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CursoDescrRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class CursoDescrService {

    @Autowired
    private CursoDescrRepository cursoDescrRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<CursoDescrDTO> listarPorCursoEConta(Long idCurso, Long idConta) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        if (!curso.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("Curso informado não pertence à conta fornecida.");
        }

        List<CursoDescr> cursosDescr = cursoDescrRepository.findByCursoIdCurso(idCurso);
        
        return cursosDescr.stream()
                .map(CursoDescrDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CursoDescrDTO buscarPorId(Long idCursoDescr, Long idConta) {
        CursoDescr cursoDescr = cursoDescrRepository.findById(idCursoDescr)
                .orElseThrow(() -> new IllegalArgumentException("Descrição de curso não encontrada."));

        if (!cursoDescr.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A descrição de curso informada não pertence à conta fornecida.");
        }

        return new CursoDescrDTO(cursoDescr);
    }

    @Transactional
    public CursoDescrDTO salvar(CadastroCursoDescrDTO dto) {
        CursoDescr cursoDescr = criarCursoDescrAPartirDTO(dto);
        cursoDescr = cursoDescrRepository.save(cursoDescr);
        return new CursoDescrDTO(cursoDescr);
    }

    @Transactional
    public CursoDescrDTO atualizar(CadastroCursoDescrDTO dto) {
        CursoDescr cursoDescr = cursoDescrRepository.findById(dto.getIdCursoDescr())
                .orElseThrow(() -> new IllegalArgumentException("CursoDescr não encontrado"));
        atualizarDados(cursoDescr, dto);
        cursoDescr = cursoDescrRepository.save(cursoDescr);
        return new CursoDescrDTO(cursoDescr);
    }

    private CursoDescr criarCursoDescrAPartirDTO(CadastroCursoDescrDTO dto) {
        CursoDescr cursoDescr = new CursoDescr();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        boolean ordemExistente = cursoDescrRepository.existsByOrdemAndCursoAndConta(dto.getOrdem(), curso, conta);
        if (ordemExistente) {
            throw new IllegalArgumentException("Já existe um registro com essa ordem para o mesmo curso e conta.");
        }

        cursoDescr.setConta(conta);
        cursoDescr.setCurso(curso);
        cursoDescr.setUsuario(usuario);
        cursoDescr.setDataCadastro(LocalDateTime.now());
        cursoDescr.setTitulo(dto.getTitulo());
        cursoDescr.setDescricao(dto.getDescricao());
        cursoDescr.setOrdem(dto.getOrdem());

        return cursoDescr;
    }

    private void atualizarDados(CursoDescr destino, CadastroCursoDescrDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Curso curso = cursoRepository.findById(origem.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        boolean ordemExistente = cursoDescrRepository.existsByOrdemAndCursoAndConta(origem.getOrdem(), curso, conta);
        if (ordemExistente) {
            throw new IllegalArgumentException("Já existe um registro com essa ordem para o mesmo curso e conta.");
        }

        destino.setConta(conta);
        destino.setCurso(curso);
        destino.setUsuario(usuario);
        destino.setTitulo(origem.getTitulo());
        destino.setDescricao(origem.getDescricao());
        destino.setOrdem(origem.getOrdem());
    }
    
    @Transactional
    public void excluirPorId(Long idCursoDescr, Long idConta) {
        CursoDescr cursoDescr = cursoDescrRepository.findById(idCursoDescr)
                .orElseThrow(() -> new IllegalArgumentException("CursoDescr não encontrado"));

        if (!cursoDescr.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("O CursoDescr informado não pertence à conta fornecida.");
        }

        cursoDescrRepository.delete(cursoDescr);
    }
}