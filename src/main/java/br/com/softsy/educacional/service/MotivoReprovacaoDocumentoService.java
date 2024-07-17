package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.MotivoReprovacaoCandidatoDTO;
import br.com.softsy.educacional.dto.MotivoReprovacaoDocumentoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.MotivoReprovacaoCandidato;
import br.com.softsy.educacional.model.MotivoReprovacaoDocumento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.MotivoReprovacaoDocumentoRepository;

@Service
public class MotivoReprovacaoDocumentoService {

    @Autowired
    private MotivoReprovacaoDocumentoRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<MotivoReprovacaoDocumentoDTO> listarTudo() {
        List<MotivoReprovacaoDocumento> motivos = repository.findAll();
        return motivos.stream()
                .map(MotivoReprovacaoDocumentoDTO::new)
                .collect(Collectors.toList());
    }
    
	@Transactional(readOnly = true)
	public MotivoReprovacaoDocumentoDTO buscarPorId(Long id) {
		MotivoReprovacaoDocumento motivo = repository.getReferenceById(id);
		return new MotivoReprovacaoDocumentoDTO(motivo);
	}

    @Transactional(readOnly = true)
    public List<MotivoReprovacaoDocumentoDTO> buscarPorIdConta(Long idConta) {
        List<MotivoReprovacaoDocumento> motivos = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar motivos por id de conta"));
        return motivos.stream()
                .map(MotivoReprovacaoDocumentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MotivoReprovacaoDocumentoDTO salvar(MotivoReprovacaoDocumentoDTO dto) {
        MotivoReprovacaoDocumento motivo = criarMotivoReprovacaoDocumentoAPartirDTO(dto);
        motivo = repository.save(motivo);
        return new MotivoReprovacaoDocumentoDTO(motivo);
    }

    @Transactional
    public MotivoReprovacaoDocumentoDTO atualizar(MotivoReprovacaoDocumentoDTO dto) {
        MotivoReprovacaoDocumento motivo = repository.findById(dto.getIdMotivoReprovacaoDocumento())
                .orElseThrow(() -> new IllegalArgumentException("Motivo não encontrado"));
        atualizarDados(motivo, dto);
        motivo = repository.save(motivo);
        return new MotivoReprovacaoDocumentoDTO(motivo);
    }

    @Transactional
    public void ativarDesativar(char status, Long idMotivoReprovacaoDocumento) {
        MotivoReprovacaoDocumento motivo = repository.findById(idMotivoReprovacaoDocumento)
                .orElseThrow(() -> new IllegalArgumentException("Motivo não encontrado"));
        motivo.setAtivo(status);
        repository.save(motivo);
    }

    private MotivoReprovacaoDocumento criarMotivoReprovacaoDocumentoAPartirDTO(MotivoReprovacaoDocumentoDTO dto) {
        MotivoReprovacaoDocumento motivo = new MotivoReprovacaoDocumento();
        
        
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));
        
        motivo.setConta(conta);
        
        motivo.setMotivoReprovacaoDocumento(dto.getMotivoReprovacaoDocumento());
        motivo.setDataCadastro(LocalDateTime.now());
        motivo.setAtivo('S');
        motivo.setObrigatorio(dto.getObrigatorio());
        return motivo;
    }

    private void atualizarDados(MotivoReprovacaoDocumento destino, MotivoReprovacaoDocumentoDTO origem) {
        destino.setConta(contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada")));
        destino.setMotivoReprovacaoDocumento(origem.getMotivoReprovacaoDocumento());
        destino.setObrigatorio(origem.getObrigatorio());
    }
}