package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.MotivoReprovacaoCandidatoDTO;
import br.com.softsy.educacional.dto.MotivoReprovacaoDocumentoDTO;
import br.com.softsy.educacional.service.MotivoReprovacaoDocumentoService;

@RestController
@RequestMapping("/motivoReprovacaoDocumento")
public class MotivoReprovacaoDocumentoController {

    @Autowired
    private MotivoReprovacaoDocumentoService service;

    @GetMapping
    public ResponseEntity<List<MotivoReprovacaoDocumentoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }
    
    @GetMapping("/{idMotivoReprovacaoDocumento}")
    public ResponseEntity<MotivoReprovacaoDocumentoDTO> buscarPorId(@PathVariable Long idMotivoReprovacaoDocumento) {
    	MotivoReprovacaoDocumentoDTO motivoDTO = service.buscarPorId(idMotivoReprovacaoDocumento);
        return ResponseEntity.ok(motivoDTO);
    }

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<MotivoReprovacaoDocumentoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<MotivoReprovacaoDocumentoDTO> motivos = service.buscarPorIdConta(idConta);
        return ResponseEntity.ok(motivos);
    }

    @PostMapping
    public ResponseEntity<MotivoReprovacaoDocumentoDTO> cadastrar(@RequestBody @Valid MotivoReprovacaoDocumentoDTO dto) {
        MotivoReprovacaoDocumentoDTO novoMotivo = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoMotivo.getIdMotivoReprovacaoDocumento()).toUri();
        return ResponseEntity.created(uri).body(novoMotivo);
    }

    @PutMapping
    public ResponseEntity<MotivoReprovacaoDocumentoDTO> atualizar(@RequestBody @Valid MotivoReprovacaoDocumentoDTO dto) {
        MotivoReprovacaoDocumentoDTO motivoAtualizado = service.atualizar(dto);
        return ResponseEntity.ok(motivoAtualizado);
    }

    @PutMapping("/{idMotivoReprovacaoDocumento}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idMotivoReprovacaoDocumento) {
        service.ativarDesativar('S', idMotivoReprovacaoDocumento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idMotivoReprovacaoDocumento}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idMotivoReprovacaoDocumento) {
        service.ativarDesativar('N', idMotivoReprovacaoDocumento);
        return ResponseEntity.ok().build();
    }
}