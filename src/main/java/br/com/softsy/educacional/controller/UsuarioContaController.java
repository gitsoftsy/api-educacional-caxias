package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.UsuarioContaDTO;
import br.com.softsy.educacional.service.UsuarioContaService;

@RestController
@RequestMapping("/usuarioContas")
@Validated
public class UsuarioContaController {

    @Autowired
    private UsuarioContaService usuarioContaService;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioContaDTO>> buscarPorIdUsuario(@PathVariable Long idUsuario) {
        List<UsuarioContaDTO> usuarioContas = usuarioContaService.buscarPorIdUsuario(idUsuario);
        return ResponseEntity.ok(usuarioContas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioContaDTO> buscarPorId(@PathVariable Long id) {
        UsuarioContaDTO usuarioConta = usuarioContaService.buscarPorId(id);
        return ResponseEntity.ok(usuarioConta);
    }

    @PostMapping
    public ResponseEntity<UsuarioContaDTO> cadastrar(@RequestBody @Valid UsuarioContaDTO dto) {
        UsuarioContaDTO usuarioContaDTO = usuarioContaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioContaDTO.getIdUsuarioConta()).toUri();
        return ResponseEntity.created(uri).body(usuarioContaDTO);
    }

    @PutMapping
    public ResponseEntity<UsuarioContaDTO> atualizar(@RequestBody @Valid UsuarioContaDTO dto) {
        UsuarioContaDTO usuarioContaDTO = usuarioContaService.atualizar(dto);
        return ResponseEntity.ok(usuarioContaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        usuarioContaService.remover(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> removerPorUsuarioId(@PathVariable Long usuarioId) {
        usuarioContaService.removerPorUsuarioId(usuarioId);
        return ResponseEntity.ok().build();
    }
}