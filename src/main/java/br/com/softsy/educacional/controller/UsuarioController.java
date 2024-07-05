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

import br.com.softsy.educacional.dto.ListaUsuarioContaDTO;
import br.com.softsy.educacional.dto.UsuarioDTO;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaUsuarioContaDTO> buscarPorId(@PathVariable Long id) {
        ListaUsuarioContaDTO listaUsuarioContaDTO = service.buscarPorId(id);
        return ResponseEntity.ok(listaUsuarioContaDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO usuarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioDTO.getIdUsuario()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizar(@RequestBody @Valid UsuarioDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idUsuario}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idUsuario) {
        service.ativaDesativa('S', idUsuario);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idUsuario}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idUsuario) {
        service.ativaDesativa('N', idUsuario);
        return ResponseEntity.ok().build();
    }
}