package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.UsuarioContaDTO;
import br.com.softsy.educacional.model.ContaPadraoAcesso;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.model.UsuarioConta;
import br.com.softsy.educacional.repository.ContaPadraoAcessoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.UsuarioContaRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class UsuarioContaService {

    @Autowired
    private UsuarioContaRepository usuarioContaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaPadraoAcessoRepository contaPadraoAcessoRepository;

    @Autowired
    private EscolaRepository escolaRepository;
    
    @Transactional(readOnly = true)
    public List<UsuarioContaDTO> buscarPorIdUsuario(Long idUsuario) {
        List<UsuarioConta> usuarioContas = usuarioContaRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar contas por ID do usuário"));
        return usuarioContas.stream()
                .map(UsuarioContaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioContaDTO buscarPorId(Long id) {
        UsuarioConta usuarioConta = usuarioContaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar conta por ID"));
        return new UsuarioContaDTO(usuarioConta);
    }

    @Transactional
    public UsuarioContaDTO salvar(UsuarioContaDTO dto) {
        UsuarioConta usuarioConta = criarUsuarioContaAPartirDTO(dto);
        usuarioConta = usuarioContaRepository.save(usuarioConta);
        return new UsuarioContaDTO(usuarioConta);
    }

    private UsuarioConta criarUsuarioContaAPartirDTO(UsuarioContaDTO dto) {
        UsuarioConta usuarioConta = new UsuarioConta();

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar usuário"));

        ContaPadraoAcesso contaPadraoAcesso = null;
        if (dto.getContaPadraoAcessoId() != null) {
            contaPadraoAcesso = contaPadraoAcessoRepository.findById(dto.getContaPadraoAcessoId())
                    .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta padrão de acesso"));
        }
        
        Escola escola = null;
        if (dto.getEscolaId() != null) {
        	escola = escolaRepository.findById(dto.getEscolaId())
                    .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta padrão de acesso"));
        }

        BeanUtils.copyProperties(dto, usuarioConta, "idUsuarioConta");
        usuarioConta.setUsuario(usuario);
        usuarioConta.setContaPadraoAcesso(contaPadraoAcesso);
        usuarioConta.setEscola(escola);

        return usuarioConta;
    }

    @Transactional
    public UsuarioContaDTO atualizar(UsuarioContaDTO dto) {
        UsuarioConta usuarioConta = usuarioContaRepository.findById(dto.getIdUsuarioConta())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta para atualizar"));
        atualizarDados(usuarioConta, dto);
        usuarioConta = usuarioContaRepository.save(usuarioConta);
        return new UsuarioContaDTO(usuarioConta);
    }

    private void atualizarDados(UsuarioConta destino, UsuarioContaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idUsuarioConta");

        Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar usuário"));
        

        ContaPadraoAcesso contaPadraoAcesso = null;
        if (origem.getContaPadraoAcessoId() != null) {
            contaPadraoAcesso = contaPadraoAcessoRepository.findById(origem.getContaPadraoAcessoId())
                    .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta padrão de acesso"));
        }
        
        Escola escola = null;
        if (origem.getEscolaId() != null) {
        	escola = escolaRepository.findById(origem.getEscolaId())
                    .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar Escola"));
        }

        destino.setEscola(escola);
        destino.setUsuario(usuario);
        destino.setContaPadraoAcesso(contaPadraoAcesso);
    }

    @Transactional
    public void remover(Long id) {
        usuarioContaRepository.deleteById(id);
    }
    
    @Transactional
    public void removerPorUsuarioId(Long usuarioId) {
        usuarioContaRepository.deleteByUsuario_IdUsuario(usuarioId);
    }
}