package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AreaConhecimentoDTO;
import br.com.softsy.educacional.dto.FeriadoContaDTO;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.FeriadoConta;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.FeriadoContaRepository;

@Service
public class FeriadoContaService {

    @Autowired
    private FeriadoContaRepository repository;
    
    @Autowired
    private ContaRepository contaRepository;


    @Transactional(readOnly = true)
    public List<FeriadoContaDTO> listarTudo() {
        List<FeriadoConta> feriadosConta = repository.findAll();
        return feriadosConta.stream()
                .map(FeriadoContaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FeriadoContaDTO buscarPorId(Long id) {
        return new FeriadoContaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public FeriadoContaDTO salvar(FeriadoContaDTO dto) {
        FeriadoConta feriadoConta = criarFeriadoContaAPartirDTO(dto);
        feriadoConta = repository.save(feriadoConta);
        return new FeriadoContaDTO(feriadoConta);
    }
    
    @Transactional(readOnly = true)
    public List<FeriadoContaDTO> buscarPorIdConta(Long idConta) {
        List<FeriadoConta> feriadoConta = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar feriado por ID da conta"));
        return feriadoConta.stream()
                .map(FeriadoContaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FeriadoContaDTO atualizar(FeriadoContaDTO dto) {
        FeriadoConta feriadoConta = repository.getReferenceById(dto.getIdFeriadoConta());
        atualizaDados(feriadoConta, dto);
        return new FeriadoContaDTO(feriadoConta);
    }

    private FeriadoConta criarFeriadoContaAPartirDTO(FeriadoContaDTO dto) {
        FeriadoConta feriadoConta = new FeriadoConta();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("conta não encontrada"));
        BeanUtils.copyProperties(dto, feriadoConta, "idFeriadoConta", "dataCadastro");
        feriadoConta.setConta(conta);
        feriadoConta.setDataCadastro(LocalDateTime.now());
        feriadoConta.setAtivo('S');
        return feriadoConta;
    }

    private void atualizaDados(FeriadoConta destino, FeriadoContaDTO origem) {
        destino.setConta(contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idFeriadoConta", "dataCadastro", "ativo");
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        FeriadoConta feriadoConta = repository.getReferenceById(id);
        feriadoConta.setAtivo(status);
    }
}
