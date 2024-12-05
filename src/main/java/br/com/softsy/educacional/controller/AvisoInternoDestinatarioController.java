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
import br.com.softsy.educacional.dto.AvisoInternoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoDestinatarioDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.AvisoInternoDestinatarioService;

@RestController
@RequestMapping("/avisoInternoDestinatario")
public class AvisoInternoDestinatarioController {

	@Autowired
    private AvisoInternoDestinatarioService service;

    @GetMapping
    public ResponseEntity<List<AvisoInternoDestinatarioDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAvisoInternoDestinatario}")
    public ResponseEntity<AvisoInternoDestinatarioDTO> buscarPorId(@PathVariable Long idAvisoInternoDestinatario) {
        return ResponseEntity.ok(service.buscarPorId(idAvisoInternoDestinatario));
    }
    
    @GetMapping("/usuario/{idUsuarioDestinatario}")
    public ResponseEntity<List<AvisoInternoDestinatarioDTO>> buscarPorIdUsuario(@PathVariable Long idUsuarioDestinatario) {
        List<AvisoInternoDestinatarioDTO> avisoInternoDestinatario = service.buscarPorIdUsuario(idUsuarioDestinatario);
        return ResponseEntity.ok(avisoInternoDestinatario);
    }
    
    @GetMapping("/professor/{idProfessorDestinatario}")
    public ResponseEntity<List<AvisoInternoDestinatarioDTO>> buscarPorIdProfessor(@PathVariable Long idProfessorDestinatario) {
        List<AvisoInternoDestinatarioDTO> avisoInternoDestinatario = service.buscarPorIdUsuario(idProfessorDestinatario);
        return ResponseEntity.ok(avisoInternoDestinatario);
    }
    
    @PostMapping
    public ResponseEntity<CadastroAvisoInternoDestinatarioDTO> cadastrar(@RequestBody @Valid CadastroAvisoInternoDestinatarioDTO dto) throws IOException {
    	CadastroAvisoInternoDestinatarioDTO avisoInternoDestinatarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(avisoInternoDestinatarioDTO.getIdAvisoInternoDestinatario()).toUri();
        return ResponseEntity.created(uri).body(avisoInternoDestinatarioDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroAvisoInternoDestinatarioDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
    
    @PutMapping("/atualizarDataLeitura/{idAvisoInternoDestinatario}")
    public ResponseEntity<AvisoInternoDestinatarioDTO> atualizarDataLeitura(
            @PathVariable Long idAvisoInternoDestinatario,
            @RequestBody AvisoInternoDestinatarioDTO dto) {

        try {
        	AvisoInternoDestinatarioDTO avisoInternoAtualizado = service.atualizarDataLeitura(idAvisoInternoDestinatario, dto.getDataLeitura());

            return ResponseEntity.ok(avisoInternoAtualizado);

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
            @RequestParam(value = "idAviso", required = false) Long idAvisoInterno)
    {
        if (idAvisoInterno == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = service.listaDestinatariosAvisoInterno(idAvisoInterno);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @DeleteMapping("/{idAvisoInternoDestinatario}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAvisoInternoDestinatario) {
        service.excluir(idAvisoInternoDestinatario);
        return ResponseEntity.ok().build();
    }

}
