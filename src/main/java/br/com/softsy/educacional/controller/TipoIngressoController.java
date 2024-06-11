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

import br.com.softsy.educacional.dto.TipoIngressoDTO;
import br.com.softsy.educacional.service.TipoIngressoService;

@RestController
@RequestMapping("/tiposIngresso")
public class TipoIngressoController {

    @Autowired
    private TipoIngressoService tipoIngressoService;

    @GetMapping
    public ResponseEntity<List<TipoIngressoDTO>> listar() {
        List<TipoIngressoDTO> tiposIngresso = tipoIngressoService.listarTudo();
        return ResponseEntity.ok(tiposIngresso);
    }
    
    @GetMapping("/{idTipoIngresso}")
    public ResponseEntity<TipoIngressoDTO> buscarPorId(@PathVariable Long idTipoIngresso) {
        TipoIngressoDTO tipoIngressoDTO = tipoIngressoService.buscarPorId(idTipoIngresso);
        return ResponseEntity.ok(tipoIngressoDTO);
    }

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<TipoIngressoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<TipoIngressoDTO> tiposIngresso = tipoIngressoService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(tiposIngresso);
    }

    @PostMapping
    public ResponseEntity<TipoIngressoDTO> cadastrar(@RequestBody @Valid TipoIngressoDTO dto) {
        TipoIngressoDTO tipoIngressoDTO = tipoIngressoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tipoIngressoDTO.getIdTipoIngresso()).toUri();
        return ResponseEntity.created(uri).body(tipoIngressoDTO);
    }

    @PutMapping
    public ResponseEntity<TipoIngressoDTO> atualizar(@RequestBody @Valid TipoIngressoDTO dto) {
        TipoIngressoDTO tipoIngressoDTO = tipoIngressoService.atualizar(dto);
        return ResponseEntity.ok(tipoIngressoDTO);
    }
    
    @PutMapping("/{idTipoIngresso}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idTipoIngresso) {
        tipoIngressoService.ativaDesativa('S', idTipoIngresso);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idTipoIngresso}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idTipoIngresso) {
        tipoIngressoService.ativaDesativa('N', idTipoIngresso);
        return ResponseEntity.ok().build();
    }
}