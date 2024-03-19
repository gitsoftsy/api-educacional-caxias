package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEquipamentoDTO;
import br.com.softsy.educacional.dto.EquipamentoDTO;
import br.com.softsy.educacional.model.Equipamento;
import br.com.softsy.educacional.model.MarcaEquipamento;
import br.com.softsy.educacional.repository.EquipamentoRepository;
import br.com.softsy.educacional.repository.MarcaEquipamentoRepository;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository repository;
    
    @Autowired
    private MarcaEquipamentoRepository marcaRepository;

    public List<EquipamentoDTO> listarTodos() {
        List<Equipamento> equipamentos = repository.findAll();
        return equipamentos.stream()
                .map(EquipamentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EquipamentoDTO buscarPorId(Long id) {
        Equipamento equipamento = repository.getReferenceById(id);
        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public EquipamentoDTO salvar(CadastroEquipamentoDTO dto) {
        Equipamento equipamento = criarEquipamentoAPartirDTO(dto);
        equipamento = repository.save(equipamento);
        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public EquipamentoDTO atualizar(CadastroEquipamentoDTO dto) {
        Equipamento equipamento = repository.getReferenceById(dto.getIdEquipamento());
        atualizarDadosEquipamento(equipamento, dto);
        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public void ativarDesativarEquipamento(char status, Long idEquipamento) {
        Equipamento equipamento = repository.getReferenceById(idEquipamento);
        equipamento.setAtivo(status);
    }

    private Equipamento criarEquipamentoAPartirDTO(CadastroEquipamentoDTO dto) {
        Equipamento equipamento = new Equipamento();
        MarcaEquipamento marcaEquipamento = marcaRepository.findById(dto.getMarcaEquipamentoId())
        		.orElseThrow(() -> new IllegalArgumentException("Marca não encontrada"));
        BeanUtils.copyProperties(dto, equipamento, "idEquipamento", "dataCadastro");
        equipamento.setDataCadastro(LocalDateTime.now());
        equipamento.setAtivo('S');
        equipamento.setMarcaEquipamento(marcaEquipamento);
        return equipamento;
    }

    private void atualizarDadosEquipamento(Equipamento destino, CadastroEquipamentoDTO origem) {
    	destino.setMarcaEquipamento(marcaRepository.findById(origem.getMarcaEquipamentoId())
    			.orElseThrow(() -> new IllegalArgumentException("Marca não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idEquipamento", "dataCadastro", "ativo");
    }
}