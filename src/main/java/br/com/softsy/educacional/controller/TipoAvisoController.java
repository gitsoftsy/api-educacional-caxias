package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.TipoAvisoDTO;
import br.com.softsy.educacional.service.TipoAvisoService;

@RestController
@RequestMapping("/tipoAviso")
public class TipoAvisoController {

    @Autowired
    private TipoAvisoService service;

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<TipoAvisoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<TipoAvisoDTO> tipoAviso = service.buscarPorIdConta(idConta);
        return ResponseEntity.ok(tipoAviso);
    }

    @GetMapping
    public ResponseEntity<List<TipoAvisoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idTipoAviso}")
    public ResponseEntity<TipoAvisoDTO> buscarPorId(@PathVariable Long idTipoAviso) {
        return ResponseEntity.ok(service.buscarPorId(idTipoAviso));
    }

    @PostMapping
    public ResponseEntity<TipoAvisoDTO> cadastrar(@RequestBody @Valid TipoAvisoDTO dto) {
        TipoAvisoDTO tipoAvisoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tipoAvisoDTO.getIdTipoAviso()).toUri();
        return ResponseEntity.created(uri).body(tipoAvisoDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid TipoAvisoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{idTipoAviso}")
    public ResponseEntity<Void> excluir(@PathVariable Long idTipoAviso) {
        service.excluir(idTipoAviso);
        return ResponseEntity.ok().build();
    }
}