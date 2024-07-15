package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.softsy.educacional.dto.DisciplinaDTO;
import br.com.softsy.educacional.dto.TransacaoDTO;
import br.com.softsy.educacional.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;
    
    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> listar() {
        List<TransacaoDTO> disciplinas = transacaoService.listarTudo();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/modulo/{idModulo}")
    public ResponseEntity<List<TransacaoDTO>> buscarPorIdModulo(@PathVariable Long idModulo) {
        List<TransacaoDTO> transacoes = transacaoService.buscarPorIdModulo(idModulo);
        return ResponseEntity.ok(transacoes);
    }
    
    @GetMapping("/{idUsuario}/acessos")
    public ResponseEntity<?> listarAcessosUsuarios(@PathVariable Long idUsuario) {
        List<Map<String, Object>> result = transacaoService.listarAcessosUsuarios(idUsuario);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum acesso encontrado para o usuário informado.");
        }
        return ResponseEntity.ok(result);
    }
   
    
    @GetMapping("/{idUsuario}/acessos/transacao/{idTransacao}")
    public ResponseEntity<?> listarAcessosUsuariosTransacao(@PathVariable Long idUsuario, @PathVariable Long idTransacao) {
        List<Map<String, Object>> result = transacaoService.listarAcessosUsuariosTransacao(idUsuario, idTransacao);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum acesso encontrado para o usuário ou transação informado.");
        }
        return ResponseEntity.ok(result);
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