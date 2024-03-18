package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.MarcaEquipamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.MarcaEquipamento;
import br.com.softsy.educacional.repository.MarcaEquipamentoRepository;

@Service
public class MarcaEquipamentoService {

    @Autowired
    private MarcaEquipamentoRepository repository;

    public List<MarcaEquipamento> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public MarcaEquipamentoDTO buscarPorId(Long id) {
        return new MarcaEquipamentoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public MarcaEquipamentoDTO salvar(MarcaEquipamentoDTO dto) {
        validarMarcaEquipamento(dto.getMarcaEquipamento());

        MarcaEquipamento marcaEquipamento = criarMarcaEquipamentoAPartirDTO(dto);

        marcaEquipamento = repository.save(marcaEquipamento);
        return new MarcaEquipamentoDTO(marcaEquipamento);
    }

    private void validarMarcaEquipamento(String marcaEquipamento) {
        Optional<MarcaEquipamento> marcaEquipamentoExistente = repository.findByMarcaEquipamento(marcaEquipamento).stream().findFirst();
        if (marcaEquipamentoExistente.isPresent()) {
            throw new UniqueException("Essa marca de equipamento já existe.");
        }
    }

    private MarcaEquipamento criarMarcaEquipamentoAPartirDTO(MarcaEquipamentoDTO dto) {
        MarcaEquipamento marcaEquipamento = new MarcaEquipamento();
        BeanUtils.copyProperties(dto, marcaEquipamento, "idMarcaEquipamento", "ativo", "dataCadastro");
        marcaEquipamento.setDataCadastro(LocalDateTime.now());
        marcaEquipamento.setAtivo('S');
        return marcaEquipamento;
    }

    @Transactional
    public void ativarDesativar(Character status, Long idMarcaEquipamento) {
        MarcaEquipamento marcaEquipamento = repository.getReferenceById(idMarcaEquipamento);
        marcaEquipamento.setAtivo(status);
    }

    @Transactional
    public MarcaEquipamentoDTO atualizar(MarcaEquipamentoDTO dto) {
        MarcaEquipamento marcaEquipamento = repository.getReferenceById(dto.getIdMarcaEquipamento());
        atualizarDados(marcaEquipamento, dto);
        return new MarcaEquipamentoDTO(marcaEquipamento);
    }

    private void atualizarDados(MarcaEquipamento destino, MarcaEquipamentoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idMarcaEquipamento", "ativo", "dataCadastro");
    }
}