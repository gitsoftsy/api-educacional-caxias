package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoDescrDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDescrDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.OfertaConcursoDescr;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.OfertaConcursoDescrRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class OfertaConcursoDescrService {

    @Autowired
    private OfertaConcursoDescrRepository ofertaConcursoDescrRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private OfertaConcursoRepository ofertaConcursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<OfertaConcursoDescrDTO> listarPorOfertaConcursoEConta(Long idOfertaConcurso, Long idConta) {
        OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(idOfertaConcurso)
                .orElseThrow(() -> new IllegalArgumentException("Oferta de Concurso não encontrada"));

        if (!ofertaConcurso.getCurso().getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A oferta de concurso informada não pertence à conta fornecida.");
        }

        List<OfertaConcursoDescr> descricoes = ofertaConcursoDescrRepository.findByOfertaConcursoIdOfertaConcurso(idOfertaConcurso);
        
        return descricoes.stream()
                .map(OfertaConcursoDescrDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public OfertaConcursoDescrDTO buscarPorId(Long idOfertaConcursoDescr, Long idConta) {
        OfertaConcursoDescr descricao = ofertaConcursoDescrRepository.findById(idOfertaConcursoDescr)
                .orElseThrow(() -> new IllegalArgumentException("Descrição da Oferta de Concurso não encontrada."));

        if (!descricao.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A descrição da oferta de concurso informada não pertence à conta fornecida.");
        }

        return new OfertaConcursoDescrDTO(descricao);
    }

    @Transactional
    public OfertaConcursoDescrDTO salvar(CadastroOfertaConcursoDescrDTO dto) {
        OfertaConcursoDescr descricao = criarDescricaoAPartirDTO(dto);
        descricao = ofertaConcursoDescrRepository.save(descricao);
        return new OfertaConcursoDescrDTO(descricao);
    }

    @Transactional
    public OfertaConcursoDescrDTO atualizar(CadastroOfertaConcursoDescrDTO dto) {
        OfertaConcursoDescr descricao = ofertaConcursoDescrRepository.findById(dto.getIdOfertaConcursoDescr())
                .orElseThrow(() -> new IllegalArgumentException("Descrição da Oferta de Concurso não encontrada"));
        atualizarDados(descricao, dto);
        descricao = ofertaConcursoDescrRepository.save(descricao);
        return new OfertaConcursoDescrDTO(descricao);
    }

    private OfertaConcursoDescr criarDescricaoAPartirDTO(CadastroOfertaConcursoDescrDTO dto) {
        OfertaConcursoDescr descricao = new OfertaConcursoDescr();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(dto.getOfertaConcursoId())
                .orElseThrow(() -> new IllegalArgumentException("Oferta de Concurso não encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        boolean ordemExistente = ofertaConcursoDescrRepository.existsByOrdemAndOfertaConcursoAndConta(dto.getOrdem(), ofertaConcurso, conta);
        if (ordemExistente) {
            throw new IllegalArgumentException("Já existe um registro com essa ordem para a mesma oferta de concurso e conta.");
        }

        descricao.setConta(conta);
        descricao.setOfertaConcurso(ofertaConcurso);
        descricao.setUsuario(usuario);
        descricao.setDataCadastro(LocalDateTime.now());
        descricao.setTitulo(dto.getTitulo());
        descricao.setDescricao(dto.getDescricao());
        descricao.setOrdem(dto.getOrdem());

        return descricao;
    }

    private void atualizarDados(OfertaConcursoDescr destino, CadastroOfertaConcursoDescrDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(origem.getOfertaConcursoId())
                .orElseThrow(() -> new IllegalArgumentException("Oferta de Concurso não encontrada"));
        Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
       
        if (origem.getOrdem() != destino.getOrdem()) {
            boolean ordemExistente = ofertaConcursoDescrRepository.existsByOrdemAndOfertaConcursoAndConta(origem.getOrdem(), ofertaConcurso, conta);
            if (ordemExistente) {
                throw new IllegalArgumentException("Já existe um registro com essa ordem para a mesma oferta de concurso e conta.");
            }
        }

        destino.setConta(conta);
        destino.setOfertaConcurso(ofertaConcurso);
        destino.setUsuario(usuario);
        destino.setTitulo(origem.getTitulo());
        destino.setDescricao(origem.getDescricao());
        destino.setOrdem(origem.getOrdem());
    }
    
    @Transactional
    public void excluirPorId(Long idOfertaConcursoDescr, Long idConta) {
        OfertaConcursoDescr descricao = ofertaConcursoDescrRepository.findById(idOfertaConcursoDescr)
                .orElseThrow(() -> new IllegalArgumentException("Descrição da Oferta de Concurso não encontrada"));

        if (!descricao.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A descrição da oferta de concurso informada não pertence à conta fornecida.");
        }

        ofertaConcursoDescrRepository.delete(descricao);
    }
}
