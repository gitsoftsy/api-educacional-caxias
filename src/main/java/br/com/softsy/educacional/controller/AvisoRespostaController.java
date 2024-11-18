package br.com.softsy.educacional.controller;


import br.com.softsy.educacional.dto.AvisoRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoRespostaDTO;
import br.com.softsy.educacional.service.AvisoRespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avisoResposta")
public class AvisoRespostaController {

    @Autowired
    private AvisoRespostaService avisoRespostaService;

    @GetMapping
    public ResponseEntity<List<AvisoRespostaDTO>> listarTudo() {
        List<AvisoRespostaDTO> avisoRespostas = avisoRespostaService.listarTudo();
        return ResponseEntity.ok(avisoRespostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvisoRespostaDTO> buscarPorId(@PathVariable Long id) {
        try {
        	AvisoRespostaDTO aviso = avisoRespostaService.buscarPorId(id);
            return ResponseEntity.ok(aviso);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso não encontrado", e);
        }
    }


    @PostMapping
    public ResponseEntity<CadastroAvisoRespostaDTO> salvar(@RequestBody CadastroAvisoRespostaDTO dto) {
        try {
            CadastroAvisoRespostaDTO avisoRespostaSalvo = avisoRespostaService.salvar(dto);
            return new ResponseEntity<>(avisoRespostaSalvo, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar aviso resposta", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<AvisoRespostaDTO> atualizar(@PathVariable Long id, @RequestBody CadastroAvisoRespostaDTO dto) {
        dto.setIdAvisoResposta(id);
        try {
        	AvisoRespostaDTO avisoRespostaAtualizado = avisoRespostaService.atualizar(dto);
            return ResponseEntity.ok(avisoRespostaAtualizado);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso não encontrado para atualização", e);
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
        	avisoRespostaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso não encontrado para exclusão", e);
        }
    }
}
