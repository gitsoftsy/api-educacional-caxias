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

import br.com.softsy.educacional.dto.ContaPadraoAcessoTransacaoDTO;
import br.com.softsy.educacional.service.ContaPadraoAcessoTransacaoService;

@RestController
@RequestMapping("/contaPadraoAcessoTransacoes")
public class ContaPadraoAcessoTransacaoController {

    @Autowired
    private ContaPadraoAcessoTransacaoService contaPadraoAcessoTransacaoService;

    @GetMapping("/contaPadraoAcesso/{idContaPadraoAcesso}")
    public ResponseEntity<List<ContaPadraoAcessoTransacaoDTO>> buscarPorIdContaPadraoAcesso(@PathVariable Long idContaPadraoAcesso) {
        List<ContaPadraoAcessoTransacaoDTO> contaPadraoAcessoTransacoes = contaPadraoAcessoTransacaoService.buscarPorIdContaPadraoAcesso(idContaPadraoAcesso);
        return ResponseEntity.ok(contaPadraoAcessoTransacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaPadraoAcessoTransacaoDTO> buscarPorId(@PathVariable Long id) {
        ContaPadraoAcessoTransacaoDTO contaPadraoAcessoTransacao = contaPadraoAcessoTransacaoService.buscarPorId(id);
        return ResponseEntity.ok(contaPadraoAcessoTransacao);
    }

    @PostMapping
    public ResponseEntity<List<ContaPadraoAcessoTransacaoDTO>> cadastrar(@RequestBody @Valid List<ContaPadraoAcessoTransacaoDTO> dtos) {
        List<ContaPadraoAcessoTransacaoDTO> contaPadraoAcessoTransacaoDTOs = contaPadraoAcessoTransacaoService.salvarLista(dtos);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(contaPadraoAcessoTransacaoDTOs);
    }

    @PutMapping
    public ResponseEntity<ContaPadraoAcessoTransacaoDTO> atualizar(@RequestBody @Valid ContaPadraoAcessoTransacaoDTO dto) {
        ContaPadraoAcessoTransacaoDTO contaPadraoAcessoTransacaoDTO = contaPadraoAcessoTransacaoService.atualizar(dto);
        return ResponseEntity.ok(contaPadraoAcessoTransacaoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        contaPadraoAcessoTransacaoService.remover(id);
        return ResponseEntity.ok().build();
    }
}