package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ContaPadraoAcessoTransacaoDTO;
import br.com.softsy.educacional.model.ContaPadraoAcesso;
import br.com.softsy.educacional.model.ContaPadraoAcessoTransacao;
import br.com.softsy.educacional.model.Transacao;
import br.com.softsy.educacional.repository.ContaPadraoAcessoRepository;
import br.com.softsy.educacional.repository.ContaPadraoAcessoTransacaoRepository;
import br.com.softsy.educacional.repository.TransacaoRepository;

@Service
public class ContaPadraoAcessoTransacaoService {

    @Autowired
    private ContaPadraoAcessoTransacaoRepository repository;

    @Autowired
    private ContaPadraoAcessoRepository contaPadraoAcessoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional(readOnly = true)
    public List<ContaPadraoAcessoTransacaoDTO> buscarPorIdContaPadraoAcesso(Long idContaPadraoAcesso) {
        List<ContaPadraoAcessoTransacao> contaPadraoAcessoTransacoes = repository.findByContaPadraoAcesso_IdContaPadraoAcesso(idContaPadraoAcesso)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar transações por ID da conta padrão de acesso"));
        return contaPadraoAcessoTransacoes.stream()
                .map(ContaPadraoAcessoTransacaoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContaPadraoAcessoTransacaoDTO buscarPorId(Long id) {
        ContaPadraoAcessoTransacao contaPadraoAcessoTransacao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar transação por ID"));
        return new ContaPadraoAcessoTransacaoDTO(contaPadraoAcessoTransacao);
    }

    @Transactional
    public ContaPadraoAcessoTransacaoDTO salvar(ContaPadraoAcessoTransacaoDTO dto) {
        ContaPadraoAcessoTransacao contaPadraoAcessoTransacao = criarContaPadraoAcessoTransacaoAPartirDTO(dto);
        contaPadraoAcessoTransacao = repository.save(contaPadraoAcessoTransacao);
        return new ContaPadraoAcessoTransacaoDTO(contaPadraoAcessoTransacao);
    }

    private ContaPadraoAcessoTransacao criarContaPadraoAcessoTransacaoAPartirDTO(ContaPadraoAcessoTransacaoDTO dto) {
        ContaPadraoAcessoTransacao contaPadraoAcessoTransacao = new ContaPadraoAcessoTransacao();

        ContaPadraoAcesso contaPadraoAcesso = contaPadraoAcessoRepository.findById(dto.getContaPadraoAcessoId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta padrão de acesso"));
        
        Transacao transacao = transacaoRepository.findById(dto.getTransacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar transação"));
        
        BeanUtils.copyProperties(dto, contaPadraoAcessoTransacao, "idContaPadraoAcessoTransacao");
        contaPadraoAcessoTransacao.setContaPadraoAcesso(contaPadraoAcesso);
        contaPadraoAcessoTransacao.setTransacao(transacao);
        
        return contaPadraoAcessoTransacao;
    }

    @Transactional
    public ContaPadraoAcessoTransacaoDTO atualizar(ContaPadraoAcessoTransacaoDTO dto) {
        ContaPadraoAcessoTransacao contaPadraoAcessoTransacao = repository.findById(dto.getIdContaPadraoAcessoTransacao())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar transação para atualizar"));
        atualizarDados(contaPadraoAcessoTransacao, dto);
        contaPadraoAcessoTransacao = repository.save(contaPadraoAcessoTransacao);
        return new ContaPadraoAcessoTransacaoDTO(contaPadraoAcessoTransacao);
    }

    private void atualizarDados(ContaPadraoAcessoTransacao destino, ContaPadraoAcessoTransacaoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idContaPadraoAcessoTransacao");
        
        ContaPadraoAcesso contaPadraoAcesso = contaPadraoAcessoRepository.findById(origem.getContaPadraoAcessoId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta padrão de acesso"));
        
        Transacao transacao = transacaoRepository.findById(origem.getTransacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar transação"));

        destino.setContaPadraoAcesso(contaPadraoAcesso);
        destino.setTransacao(transacao);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }
}
