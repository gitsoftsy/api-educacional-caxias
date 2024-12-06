package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
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

import br.com.softsy.educacional.dto.AvisoInternoDestinatarioRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoDestinatarioRespostaDTO;
import br.com.softsy.educacional.service.AvisoInternoDestinatarioRespostaService;

@RestController
@RequestMapping("/avisoInternoDestinatarioResposta")
public class AvisoInternoDestinatarioRespostaController {
	
	@Autowired
    private AvisoInternoDestinatarioRespostaService service;

    @GetMapping
    public ResponseEntity<List<AvisoInternoDestinatarioRespostaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAvisoInternoDestinatarioResposta}")
    public ResponseEntity<AvisoInternoDestinatarioRespostaDTO> buscarPorId(@PathVariable Long idAvisoInternoDestinatarioResposta) {
        return ResponseEntity.ok(service.buscarPorId(idAvisoInternoDestinatarioResposta));
    }
    
    @GetMapping("/avisoInternoDestinatario/{idAvisoInternoDestinatario}")
    public ResponseEntity<List<AvisoInternoDestinatarioRespostaDTO>> buscarPorIdAvisoDestinatario(@PathVariable Long idAvisoInternoDestinatario) {
        List<AvisoInternoDestinatarioRespostaDTO> avisoInterno = service.buscarPorIdAvisoInternoDestinatario(idAvisoInternoDestinatario);
        return ResponseEntity.ok(avisoInterno);
    }
    
    @PostMapping
    public ResponseEntity<CadastroAvisoInternoDestinatarioRespostaDTO> cadastrar(@RequestBody @Valid CadastroAvisoInternoDestinatarioRespostaDTO dto) throws IOException {
    	CadastroAvisoInternoDestinatarioRespostaDTO avisoInternoDestinatarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(avisoInternoDestinatarioDTO.getIdAvisoInternoDestinatarioResposta()).toUri();
        return ResponseEntity.created(uri).body(avisoInternoDestinatarioDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroAvisoInternoDestinatarioRespostaDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
    
	@PutMapping("/imagem/{id}")
    public ResponseEntity<AvisoInternoDestinatarioRespostaDTO> alterarImagemAviso(
            @PathVariable Long id,
            @RequestBody AvisoInternoDestinatarioRespostaDTO dto) {
        
        try {
        	AvisoInternoDestinatarioRespostaDTO avisoAtualizado = service.alterarInternoImagemAvisoDestResp(id, dto.getPathAnexo());
            return ResponseEntity.ok(avisoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping("/atualizarDataLeitura/{idAvisoInternoDestinatarioResposta}")
    public ResponseEntity<AvisoInternoDestinatarioRespostaDTO> atualizarDataLeitura(
            @PathVariable Long idAvisoInternoDestinatarioResposta,
            @RequestBody AvisoInternoDestinatarioRespostaDTO dto) {

        try {
        	AvisoInternoDestinatarioRespostaDTO avisoInternoAtualizado = service.atualizarDataLeitura(idAvisoInternoDestinatarioResposta, dto.getDataLeitura());

            return ResponseEntity.ok(avisoInternoAtualizado);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    @DeleteMapping("/{idAvisoInternoDestinatarioResposta}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAvisoInternoDestinatarioResposta) {
        service.excluir(idAvisoInternoDestinatarioResposta);
        return ResponseEntity.ok().build();
    }

}
