package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AvisoDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDTO;
import br.com.softsy.educacional.service.AvisoService;

@RestController
@RequestMapping("/aviso")
public class AvisoController {

    @Autowired
    private AvisoService service;

    @GetMapping
    public ResponseEntity<List<AvisoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAviso}")
    public ResponseEntity<AvisoDTO> buscarPorId(@PathVariable Long idAviso) {
        return ResponseEntity.ok(service.buscarPorId(idAviso));
    }
    
    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<AvisoDTO>> buscarPorIdProfessor(@PathVariable Long idProfessor) {
        List<AvisoDTO> aviso = service.buscarPorIdProfessor(idProfessor);
        return ResponseEntity.ok(aviso);
    }
   
    
	@GetMapping("/{id}/logo")
    public ResponseEntity<String> getLogoById(@PathVariable("id") Long id) throws IOException {
		String logo = service.getLogoById(id);

       return ResponseEntity.ok(logo);
    }

    @PostMapping
    public ResponseEntity<CadastroAvisoDTO> cadastrar(@RequestBody @Valid CadastroAvisoDTO dto) throws IOException {
        CadastroAvisoDTO avisoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(avisoDTO.getIdAviso()).toUri();
        return ResponseEntity.created(uri).body(avisoDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroAvisoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
    
	@PutMapping("/imagem/{id}")
    public ResponseEntity<AvisoDTO> alterarImagemAviso(
            @PathVariable Long id,
            @RequestBody AvisoDTO dto) {
        
        try {
        	AvisoDTO avisoAtualizado = service.alterarImagemAviso(id, dto.getPathAnexo());
            return ResponseEntity.ok(avisoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
    
    @DeleteMapping("/{idAviso}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAviso) {
        service.excluir(idAviso);
        return ResponseEntity.ok().build();
    }

}