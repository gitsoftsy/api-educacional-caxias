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

import br.com.softsy.educacional.dto.ProvedorInternetDTO;
import br.com.softsy.educacional.model.ProvedorInternet;
import br.com.softsy.educacional.service.ProvedorInternetService;

@RestController
@RequestMapping("/provedorInternet")
public class ProvedorInternetController {

    @Autowired
    private ProvedorInternetService service;

    @GetMapping
    public ResponseEntity<List<ProvedorInternet>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idProvedorInternet}")
    public ResponseEntity<ProvedorInternetDTO> buscarPorId(@PathVariable Long idProvedorInternet) {
        return ResponseEntity.ok(service.buscarPorId(idProvedorInternet));
    }

    @PostMapping
    public ResponseEntity<ProvedorInternetDTO> cadastrar(@RequestBody @Valid ProvedorInternetDTO dto) {
        ProvedorInternetDTO provedorInternetDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(provedorInternetDTO.getIdProvedorInternet()).toUri();
        return ResponseEntity.created(uri).body(provedorInternetDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid ProvedorInternetDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idProvedorInternet}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idProvedorInternet) {
        service.ativaDesativa('S', idProvedorInternet);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProvedorInternet}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idProvedorInternet) {
        service.ativaDesativa('N', idProvedorInternet);
        return ResponseEntity.ok().build();
    }
}