package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import br.com.softsy.educacional.dto.AvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.AvisoDestinatarioService;

@RestController
@RequestMapping("/avisoDestinatario")
public class AvisoDestinatarioController {
	
	@Autowired
    private AvisoDestinatarioService service;

    @GetMapping
    public ResponseEntity<List<AvisoDestinatarioDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAvisoDestinatario}")
    public ResponseEntity<AvisoDestinatarioDTO> buscarPorId(@PathVariable Long idAvisoDestinatario) {
        return ResponseEntity.ok(service.buscarPorId(idAvisoDestinatario));
    }
    
    @GetMapping("/aluno/{idAvisoDestinatario}")
    public ResponseEntity<List<AvisoDestinatarioDTO>> buscarPorIdAluno(@PathVariable Long idAvisoDestinatario) {
        List<AvisoDestinatarioDTO> aviso = service.buscarPorIdAluno(idAvisoDestinatario);
        return ResponseEntity.ok(aviso);
    }
    
    @PostMapping
    public ResponseEntity<CadastroAvisoDestinatarioDTO> cadastrar(@RequestBody @Valid CadastroAvisoDestinatarioDTO dto) throws IOException {
    	CadastroAvisoDestinatarioDTO avisoDestinatarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(avisoDestinatarioDTO.getIdAvisoDestinatario()).toUri();
        return ResponseEntity.created(uri).body(avisoDestinatarioDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroAvisoDestinatarioDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
    
    @PutMapping("/atualizarDataLeitura/{idAvisoDestinatario}")
    public ResponseEntity<AvisoDestinatarioDTO> atualizarDataLeitura(
            @PathVariable Long idAvisoDestinatario,
            @RequestBody AvisoDestinatarioDTO dto) {

        try {
        	AvisoDestinatarioDTO avisoAtualizado = service.atualizarDataLeitura(idAvisoDestinatario, dto.getDataLeitura());

            return ResponseEntity.ok(avisoAtualizado);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    @GetMapping("/listarDestinatarios")
    public ResponseEntity<AllResponse> listarDestinatariosAviso(
            @RequestParam(value = "idAviso", required = false) Long idAviso)
    {
        if (idAviso == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = service.listaDestinatariosAviso(idAviso);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @DeleteMapping("/{idAvisoDestinatario}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAvisoDestinatario) {
        service.excluir(idAvisoDestinatario);
        return ResponseEntity.ok().build();
    }

}
