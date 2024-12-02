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

import br.com.softsy.educacional.dto.AvisoDTO;
import br.com.softsy.educacional.dto.AvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.AvisoDestinatarioRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioRespostaDTO;
import br.com.softsy.educacional.service.AvisoDestinatarioRespostaService;


@RestController
@RequestMapping("/avisoDestinatarioResposta")
public class AvisoDestinatarioRespostaController {
	
	@Autowired
    private AvisoDestinatarioRespostaService service;

    @GetMapping
    public ResponseEntity<List<AvisoDestinatarioRespostaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAvisoDestinatarioResposta}")
    public ResponseEntity<AvisoDestinatarioRespostaDTO> buscarPorId(@PathVariable Long idAvisoDestinatarioResposta) {
        return ResponseEntity.ok(service.buscarPorId(idAvisoDestinatarioResposta));
    }
    
    @GetMapping("/avisoDestinatario/{idAvisoDestinatario}")
    public ResponseEntity<List<AvisoDestinatarioRespostaDTO>> buscarPorIdAvisoDestinatario(@PathVariable Long idAvisoDestinatario) {
        List<AvisoDestinatarioRespostaDTO> aviso = service.buscarPorIdAvisoDestinatario(idAvisoDestinatario);
        return ResponseEntity.ok(aviso);
    }
    
    @PostMapping
    public ResponseEntity<CadastroAvisoDestinatarioRespostaDTO> cadastrar(@RequestBody @Valid CadastroAvisoDestinatarioRespostaDTO dto) throws IOException {
    	CadastroAvisoDestinatarioRespostaDTO avisoDestinatarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(avisoDestinatarioDTO.getIdAvisoDestinatarioResposta()).toUri();
        return ResponseEntity.created(uri).body(avisoDestinatarioDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroAvisoDestinatarioRespostaDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
    
	@PutMapping("/imagem/{id}")
    public ResponseEntity<AvisoDestinatarioRespostaDTO> alterarImagemAviso(
            @PathVariable Long id,
            @RequestBody AvisoDestinatarioRespostaDTO dto) {
        
        try {
        	AvisoDestinatarioRespostaDTO avisoAtualizado = service.alterarImagemAvisoDestResp(id, dto.getPathAnexo());
            return ResponseEntity.ok(avisoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping("/atualizarDataLeitura/{idAvisoDestinatarioResposta}")
    public ResponseEntity<AvisoDestinatarioRespostaDTO> atualizarDataLeitura(
            @PathVariable Long idAvisoDestinatarioResposta,
            @RequestBody AvisoDestinatarioRespostaDTO dto) {

        try {
        	AvisoDestinatarioRespostaDTO avisoAtualizado = service.atualizarDataLeitura(idAvisoDestinatarioResposta, dto.getDataLeitura());

            return ResponseEntity.ok(avisoAtualizado);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    @DeleteMapping("/{idAvisoDestinatarioResposta}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAvisoDestinatarioResposta) {
        service.excluir(idAvisoDestinatarioResposta);
        return ResponseEntity.ok().build();
    }
}
