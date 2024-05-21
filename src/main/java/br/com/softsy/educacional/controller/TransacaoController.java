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

import br.com.softsy.educacional.dto.TransacaoDTO;
import br.com.softsy.educacional.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/modulo/{idModulo}")
    public ResponseEntity<List<TransacaoDTO>> buscarPorIdModulo(@PathVariable Long idModulo) {
        List<TransacaoDTO> transacoes = transacaoService.buscarPorIdModulo(idModulo);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> buscarPorId(@PathVariable Long id) {
        TransacaoDTO transacao = transacaoService.buscarPorId(id);
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    public ResponseEntity<TransacaoDTO> cadastrar(@RequestBody @Valid TransacaoDTO dto) {
        TransacaoDTO transacaoDTO = transacaoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transacaoDTO.getIdTransacao()).toUri();
        return ResponseEntity.created(uri).body(transacaoDTO);
    }

    @PutMapping
    public ResponseEntity<TransacaoDTO> atualizar(@RequestBody @Valid TransacaoDTO dto) {
        TransacaoDTO transacaoDTO = transacaoService.atualizar(dto);
        return ResponseEntity.ok(transacaoDTO);
    }
    
	@DeleteMapping("/{idTransacao}")
	public ResponseEntity<?> excluir(@PathVariable Long idTransacao) {
		transacaoService.remover(idTransacao);
		return ResponseEntity.ok().build();
	}
}