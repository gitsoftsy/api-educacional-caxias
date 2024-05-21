package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ContaPadraoAcessoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.ContaPadraoAcesso;
import br.com.softsy.educacional.repository.ContaPadraoAcessoRepository;
import br.com.softsy.educacional.repository.ContaRepository;

@Service
public class ContaPadraoAcessoService {

    @Autowired
    private ContaPadraoAcessoRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<ContaPadraoAcessoDTO> buscarPorIdConta(Long idConta) {
        List<ContaPadraoAcesso> contaPadraoAcessos = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar contas padrão de acesso por ID da conta"));
        return contaPadraoAcessos.stream()
                .map(ContaPadraoAcessoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContaPadraoAcessoDTO buscarPorId(Long id) {
        ContaPadraoAcesso contaPadraoAcesso = repository.getReferenceById(id);
        return new ContaPadraoAcessoDTO(contaPadraoAcesso);
    }

    @Transactional
    public ContaPadraoAcessoDTO salvar(ContaPadraoAcessoDTO dto) {
        ContaPadraoAcesso contaPadraoAcesso = criarContaPadraoAcessoAPartirDTO(dto);
        contaPadraoAcesso = repository.save(contaPadraoAcesso);
        return new ContaPadraoAcessoDTO(contaPadraoAcesso);
    }

    private ContaPadraoAcesso criarContaPadraoAcessoAPartirDTO(ContaPadraoAcessoDTO dto) {
        ContaPadraoAcesso contaPadraoAcesso = new ContaPadraoAcesso();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta"));
        BeanUtils.copyProperties(dto, contaPadraoAcesso, "idContaPadraoAcesso", "ativo", "dataCadastro");
        contaPadraoAcesso.setAtivo('S');
        contaPadraoAcesso.setDtCadastro(LocalDateTime.now());
        contaPadraoAcesso.setConta(conta);
        return contaPadraoAcesso;
    }

    @Transactional
    public ContaPadraoAcessoDTO atualizar(ContaPadraoAcessoDTO dto) {
        ContaPadraoAcesso contaPadraoAcesso = repository.getReferenceById(dto.getIdContaPadraoAcesso());
        atualizarDados(contaPadraoAcesso, dto);
        return new ContaPadraoAcessoDTO(contaPadraoAcesso);
    }

    private void atualizarDados(ContaPadraoAcesso destino, ContaPadraoAcessoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idContaPadraoAcesso", "ativo", "dtCadastro");
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar conta"));
        destino.setConta(conta);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }
}