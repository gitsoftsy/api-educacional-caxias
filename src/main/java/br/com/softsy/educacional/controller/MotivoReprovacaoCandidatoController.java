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

import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.dto.MotivoReprovacaoCandidatoDTO;
import br.com.softsy.educacional.service.MotivoReprovacaoCandidatoService;

@RestController
@RequestMapping("/motivoReprovacaoCandidato")
public class MotivoReprovacaoCandidatoController {

    @Autowired
    private MotivoReprovacaoCandidatoService service;

    @GetMapping
    public ResponseEntity<List<MotivoReprovacaoCandidatoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idMotivoReprovacaoCandidato}")
    public ResponseEntity<MotivoReprovacaoCandidatoDTO> buscarPorId(@PathVariable Long idMotivoReprovacaoCandidato) {
    	MotivoReprovacaoCandidatoDTO motivoDTO = service.buscarPorId(idMotivoReprovacaoCandidato);
        return ResponseEntity.ok(motivoDTO);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<MotivoReprovacaoCandidatoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<MotivoReprovacaoCandidatoDTO> motivos = service.buscarPorIdConta(idConta);
        return ResponseEntity.ok(motivos);
    }

    @PostMapping
    public ResponseEntity<MotivoReprovacaoCandidatoDTO> cadastrar(@RequestBody @Valid MotivoReprovacaoCandidatoDTO dto) {
        MotivoReprovacaoCandidatoDTO novoMotivo = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoMotivo.getIdMotivoReprovacaoCandidato()).toUri();
        return ResponseEntity.created(uri).body(novoMotivo);
    }

    @PutMapping
    public ResponseEntity<MotivoReprovacaoCandidatoDTO> atualizar(@RequestBody @Valid MotivoReprovacaoCandidatoDTO dto) {
        MotivoReprovacaoCandidatoDTO motivoAtualizado = service.atualizar(dto);
        return ResponseEntity.ok(motivoAtualizado);
    }

    @PutMapping("/{idMotivoReprovacaoCandidato}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idMotivoReprovacaoCandidato) {
        service.ativarDesativar('S', idMotivoReprovacaoCandidato);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idMotivoReprovacaoCandidato}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idMotivoReprovacaoCandidato) {
        service.ativarDesativar('N', idMotivoReprovacaoCandidato);
        return ResponseEntity.ok().build();
    }
}