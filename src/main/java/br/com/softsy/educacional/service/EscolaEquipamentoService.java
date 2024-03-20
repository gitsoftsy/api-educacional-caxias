package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaEquipamentoDTO;
import br.com.softsy.educacional.dto.EscolaEquipamentoDTO;
import br.com.softsy.educacional.model.Equipamento;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaEquipamento;
import br.com.softsy.educacional.repository.EquipamentoRepository;
import br.com.softsy.educacional.repository.EscolaEquipamentoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaEquipamentoService {

    @Autowired
    private EscolaEquipamentoRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Transactional(readOnly = true)
    public List<EscolaEquipamentoDTO> listarTudo() {
        List<EscolaEquipamento> escolasEquipamento = repository.findAll();
        return escolasEquipamento.stream()
                .map(EscolaEquipamentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaEquipamentoDTO> buscarPorIdEscola(Long id) {
        List<EscolaEquipamento> escolasEquipamento = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar equipamentos por id de escola"));
        return escolasEquipamento.stream()
                .map(EscolaEquipamentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaEquipamentoDTO salvar(CadastroEscolaEquipamentoDTO dto) {
        EscolaEquipamento escolaEquipamento = criarEscolaEquipamentoAPartirDTO(dto);
        escolaEquipamento = repository.save(escolaEquipamento);
        return new EscolaEquipamentoDTO(escolaEquipamento);
    }

    @Transactional
    public EscolaEquipamentoDTO atualizar(CadastroEscolaEquipamentoDTO dto) {
        EscolaEquipamento escolaEquipamento = repository.getReferenceById(dto.getIdEscolaEquipamento());
        atualizarDados(escolaEquipamento, dto);
        return new EscolaEquipamentoDTO(escolaEquipamento);
    }

    @Transactional
    public void ativarDesativar(char status, Long idEscolaEquipamento) {
        EscolaEquipamento escolaEquipamento = repository.getReferenceById(idEscolaEquipamento);
        escolaEquipamento.setAtivo(status);
    }

    private EscolaEquipamento criarEscolaEquipamentoAPartirDTO(CadastroEscolaEquipamentoDTO dto) {
        EscolaEquipamento escolaEquipamento = new EscolaEquipamento();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        Equipamento equipamento = equipamentoRepository.findById(dto.getEquipamentoId())
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));
        escolaEquipamento.setEscola(escola);
        escolaEquipamento.setEquipamento(equipamento);
        escolaEquipamento.setQuantidade(dto.getQuantidade());
        escolaEquipamento.setDataCadastro(LocalDateTime.now());
        escolaEquipamento.setAtivo('S');
        return escolaEquipamento;
    }

    private void atualizarDados(EscolaEquipamento destino, CadastroEscolaEquipamentoDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setEquipamento(equipamentoRepository.findById(origem.getEquipamentoId())
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado")));
        destino.setQuantidade(origem.getQuantidade());
    }
}