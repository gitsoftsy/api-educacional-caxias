package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.dto.MotivoReprovacaoCandidatoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.Equipamento;
import br.com.softsy.educacional.model.MotivoReprovacaoCandidato;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.MotivoReprovacaoCandidatoRepository;

@Service
public class MotivoReprovacaoCandidatoService {

    @Autowired
    private MotivoReprovacaoCandidatoRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<MotivoReprovacaoCandidatoDTO> listarTudo() {
        List<MotivoReprovacaoCandidato> motivos = repository.findAll();
        return motivos.stream()
                .map(MotivoReprovacaoCandidatoDTO::new)
                .collect(Collectors.toList());
    }
    
	@Transactional(readOnly = true)
	public MotivoReprovacaoCandidatoDTO buscarPorId(Long id) {
		MotivoReprovacaoCandidato motivo = repository.getReferenceById(id);
		return new MotivoReprovacaoCandidatoDTO(motivo);
	}

    @Transactional(readOnly = true)
    public List<MotivoReprovacaoCandidatoDTO> buscarPorIdConta(Long idConta) {
        List<MotivoReprovacaoCandidato> motivos = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar motivos por id de conta"));
        return motivos.stream()
                .map(MotivoReprovacaoCandidatoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MotivoReprovacaoCandidatoDTO salvar(MotivoReprovacaoCandidatoDTO dto) {
        MotivoReprovacaoCandidato motivo = criarMotivoReprovacaoCandidatoAPartirDTO(dto);
        motivo = repository.save(motivo);
        return new MotivoReprovacaoCandidatoDTO(motivo);
    }

    @Transactional
    public MotivoReprovacaoCandidatoDTO atualizar(MotivoReprovacaoCandidatoDTO dto) {
        MotivoReprovacaoCandidato motivo = repository.findById(dto.getIdMotivoReprovacaoCandidato())
                .orElseThrow(() -> new IllegalArgumentException("Motivo não encontrado"));
        atualizarDados(motivo, dto);
        motivo = repository.save(motivo);
        return new MotivoReprovacaoCandidatoDTO(motivo);
    }

    @Transactional
    public void ativarDesativar(char status, Long idMotivoReprovacaoCandidato) {
        MotivoReprovacaoCandidato motivo = repository.findById(idMotivoReprovacaoCandidato)
                .orElseThrow(() -> new IllegalArgumentException("Motivo não encontrado"));
        motivo.setAtivo(status);
        repository.save(motivo);
    }

    private MotivoReprovacaoCandidato criarMotivoReprovacaoCandidatoAPartirDTO(MotivoReprovacaoCandidatoDTO dto) {
        MotivoReprovacaoCandidato motivo = new MotivoReprovacaoCandidato();
        
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));
        
        motivo.setConta(conta);
        motivo.setMotivoReprovacaoCandidato(dto.getMotivoReprovacaoCandidato());
        motivo.setDataCadastro(LocalDateTime.now());
        motivo.setAtivo('S');
        motivo.setObrigatorio(dto.getObrigatorio());
        return motivo;
    }

    private void atualizarDados(MotivoReprovacaoCandidato destino, MotivoReprovacaoCandidatoDTO origem) {
        destino.setConta(contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada")));
        destino.setMotivoReprovacaoCandidato(origem.getMotivoReprovacaoCandidato());
        destino.setObrigatorio(origem.getObrigatorio());
    }
}