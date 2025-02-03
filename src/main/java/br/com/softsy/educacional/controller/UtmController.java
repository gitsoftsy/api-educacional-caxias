package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.UtmDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.UtmService;

@RestController
@RequestMapping("/utm")
public class UtmController {

    @Autowired
    private UtmService utmService;

    @GetMapping
    public ResponseEntity<?> listarPorConta(@RequestHeader("idConta") Long idConta) {
        try {
            List<UtmDTO> utms = utmService.listarPorConta(idConta);
            return ResponseEntity.ok(utms);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }

    @GetMapping("/{idUtm}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idUtm, @RequestHeader("idConta") Long idConta) {
        try {
            UtmDTO utmDTO = utmService.buscarPorId(idUtm, idConta);
            return ResponseEntity.ok(utmDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<UtmDTO> cadastrar(@RequestBody @Valid UtmDTO dto) {
        UtmDTO utmDTO = utmService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(utmDTO.getIdUtm()).toUri();
        return ResponseEntity.created(uri).body(utmDTO);
    }

    @PutMapping
    public ResponseEntity<UtmDTO> atualizar(@RequestBody @Valid UtmDTO dto) {
        UtmDTO utmDTO = utmService.atualizar(dto);
        return ResponseEntity.ok(utmDTO);
    }
    
    @PutMapping("/{idUtm}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idUtm) {
    	utmService.ativaDesativa('S', idUtm);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idUtm}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idUtm) {
    	utmService.ativaDesativa('N', idUtm);
        return ResponseEntity.ok().build();
    }

}
