package br.com.softsy.educacional.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.dto.TipoAvisoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.TipoAviso;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.TipoAvisoRepository;

@Service
public class TipoAvisoService {

    @Autowired
    private TipoAvisoRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    public List<TipoAvisoDTO> listarTudo() {
        List<TipoAviso> tipoAvisoList = repository.findAll();
        return tipoAvisoList.stream()
                .map(TipoAvisoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public TipoAvisoDTO buscarPorId(Long id) {
        return new TipoAvisoDTO(repository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<TipoAvisoDTO> buscarPorIdConta(Long idConta) {
        List<TipoAviso> tipoAviso = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipo de aviso por ID da conta"));
        return tipoAviso.stream()
                .map(TipoAvisoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TipoAvisoDTO salvar(TipoAvisoDTO dto) {

        TipoAviso tipoAviso = criarTipoAvisoAPartirDTO(dto);

        tipoAviso = repository.save(tipoAviso);
        return new TipoAvisoDTO(tipoAviso);
    }

    @Transactional
    public TipoAvisoDTO atualizar(TipoAvisoDTO dto) {
        TipoAviso tipoAviso = repository.getReferenceById(dto.getIdTipoAviso());
        atualizarDados(tipoAviso, dto);
        return new TipoAvisoDTO(tipoAviso);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private TipoAviso criarTipoAvisoAPartirDTO(TipoAvisoDTO dto) {
        TipoAviso tipoAviso = new TipoAviso();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        tipoAviso.setConta(conta);
        tipoAviso.setDescricao(dto.getDescricao());
        return tipoAviso;
    }


    private void atualizarDados(TipoAviso destino, TipoAvisoDTO origem) {
        destino.setDescricao(origem.getDescricao());
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setConta(conta);
    }
}