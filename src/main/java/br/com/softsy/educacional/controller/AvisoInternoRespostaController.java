package br.com.softsy.educacional.controller;

import br.com.softsy.educacional.dto.AvisoInternoRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoRespostaDTO;
import br.com.softsy.educacional.service.AvisoInternoRespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avisoInternoResposta")
public class AvisoInternoRespostaController {

    @Autowired
    private AvisoInternoRespostaService avisoInternoRespostaService;

    @GetMapping
    public ResponseEntity<List<AvisoInternoRespostaDTO>> listarTudo() {
        List<AvisoInternoRespostaDTO> avisoInternoRespostas = avisoInternoRespostaService.listarTudo();
        return ResponseEntity.ok(avisoInternoRespostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvisoInternoRespostaDTO> buscarPorId(@PathVariable Long id) {
        try {
            AvisoInternoRespostaDTO aviso = avisoInternoRespostaService.buscarPorId(id);
            return ResponseEntity.ok(aviso);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso interno resposta não encontrado", e);
        }
    }

    @PostMapping
    public ResponseEntity<CadastroAvisoInternoRespostaDTO> salvar(@RequestBody CadastroAvisoInternoRespostaDTO dto) {
        try {
            CadastroAvisoInternoRespostaDTO avisoInternoRespostaSalvo = avisoInternoRespostaService.salvar(dto);
            return new ResponseEntity<>(avisoInternoRespostaSalvo, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar aviso interno resposta", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvisoInternoRespostaDTO> atualizar(@PathVariable Long id, @RequestBody CadastroAvisoInternoRespostaDTO dto) {
        dto.setIdAvisoInternoResposta(id);
        try {
            AvisoInternoRespostaDTO avisoInternoRespostaAtualizado = avisoInternoRespostaService.atualizar(dto);
            return ResponseEntity.ok(avisoInternoRespostaAtualizado);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso interno resposta não encontrado para atualização", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            avisoInternoRespostaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso interno resposta não encontrado para exclusão", e);
        }
    }
}
