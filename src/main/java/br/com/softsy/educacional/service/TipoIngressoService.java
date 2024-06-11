package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoIngressoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.TipoIngresso;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.TipoIngressoRepository;

@Service
public class TipoIngressoService {

    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<TipoIngressoDTO> listarTudo() {
        List<TipoIngresso> tipoIngressos = tipoIngressoRepository.findAll();
        return tipoIngressos.stream()
                .map(TipoIngressoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TipoIngressoDTO> buscarPorIdConta(Long idConta) {
        List<TipoIngresso> tipoIngressos = tipoIngressoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipo de ingresso por ID da conta"));
        return tipoIngressos.stream()
                .map(TipoIngressoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public TipoIngressoDTO buscarPorId(Long id) {
        TipoIngresso tipoIngresso = tipoIngressoRepository.getReferenceById(id);
        return new TipoIngressoDTO(tipoIngresso);
    }

    @Transactional
    public TipoIngressoDTO salvar(TipoIngressoDTO dto) {
        
        TipoIngresso tipoIngresso = criarTipoIngressoAPartirDTO(dto);
        tipoIngresso = tipoIngressoRepository.save(tipoIngresso);
        return new TipoIngressoDTO(tipoIngresso);
    }

    @Transactional
    public TipoIngressoDTO atualizar(TipoIngressoDTO dto) {
        TipoIngresso tipoIngresso = tipoIngressoRepository.findById(dto.getIdTipoIngresso())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de ingresso não encontrado"));
        atualizarDados(tipoIngresso, dto);
        return new TipoIngressoDTO(tipoIngresso);
    }

    private TipoIngresso criarTipoIngressoAPartirDTO(TipoIngressoDTO dto) {
        TipoIngresso tipoIngresso = new TipoIngresso();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        tipoIngresso.setConta(conta);
        tipoIngresso.setTipoIngresso(dto.getTipoIngresso());
        tipoIngresso.setAtivo('S');
        tipoIngresso.setDataCadastro(LocalDateTime.now());
        return tipoIngresso;
    }

    @Transactional
    public void ativaDesativa(char status, Long idTipoIngresso) {
        TipoIngresso tipoIngresso = tipoIngressoRepository.getReferenceById(idTipoIngresso);
        tipoIngresso.setAtivo(status);
    }

    private void atualizarDados(TipoIngresso destino, TipoIngressoDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setConta(conta);
        destino.setTipoIngresso(origem.getTipoIngresso());
    }
}
