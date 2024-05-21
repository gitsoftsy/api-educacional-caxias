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

import br.com.softsy.educacional.dto.ContaPadraoAcessoDTO;
import br.com.softsy.educacional.service.ContaPadraoAcessoService;

@RestController
@RequestMapping("/contaPadraoAcessos")
public class ContaPadraoAcessoController {

    @Autowired
    private ContaPadraoAcessoService contaPadraoAcessoService;

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<ContaPadraoAcessoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<ContaPadraoAcessoDTO> contaPadraoAcessos = contaPadraoAcessoService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(contaPadraoAcessos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaPadraoAcessoDTO> buscarPorId(@PathVariable Long id) {
        ContaPadraoAcessoDTO contaPadraoAcesso = contaPadraoAcessoService.buscarPorId(id);
        return ResponseEntity.ok(contaPadraoAcesso);
    }

    @PostMapping
    public ResponseEntity<ContaPadraoAcessoDTO> cadastrar(@RequestBody @Valid ContaPadraoAcessoDTO dto) {
        ContaPadraoAcessoDTO contaPadraoAcessoDTO = contaPadraoAcessoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contaPadraoAcessoDTO.getIdContaPadraoAcesso()).toUri();
        return ResponseEntity.created(uri).body(contaPadraoAcessoDTO);
    }

    @PutMapping
    public ResponseEntity<ContaPadraoAcessoDTO> atualizar(@RequestBody @Valid ContaPadraoAcessoDTO dto) {
        ContaPadraoAcessoDTO contaPadraoAcessoDTO = contaPadraoAcessoService.atualizar(dto);
        return ResponseEntity.ok(contaPadraoAcessoDTO);
    }

    @DeleteMapping("/{idContaPadraoAcesso}")
    public ResponseEntity<?> excluir(@PathVariable Long idContaPadraoAcesso) {
        contaPadraoAcessoService.remover(idContaPadraoAcesso);
        return ResponseEntity.ok().build();
    }
}