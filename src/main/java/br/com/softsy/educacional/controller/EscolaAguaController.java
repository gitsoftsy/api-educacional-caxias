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

import br.com.softsy.educacional.dto.EscolaAguaDTO;
import br.com.softsy.educacional.dto.EscolaInfraestruturaDTO;
import br.com.softsy.educacional.service.EscolaAguaService;

@RestController
@RequestMapping("/escolaAgua")
public class EscolaAguaController {

    @Autowired
    private EscolaAguaService escolaAguaService;

    @PostMapping
    public ResponseEntity<EscolaAguaDTO> cadastrar(@RequestBody @Valid EscolaAguaDTO dto) {
        EscolaAguaDTO escolaAguaDTO = escolaAguaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(escolaAguaDTO.getIdEscolaAgua()).toUri();
        return ResponseEntity.created(uri).body(escolaAguaDTO);
    }
    
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaAguaDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaAguaDTO> escolaAgua = escolaAguaService.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(escolaAgua);
	}

    @PutMapping
    public ResponseEntity<EscolaAguaDTO> atualizar(@RequestBody @Valid EscolaAguaDTO dto) {
        EscolaAguaDTO escolaAguaDTO = escolaAguaService.atualizar(dto);
        return ResponseEntity.ok(escolaAguaDTO);
    }

    @DeleteMapping("/{idEscolaAgua}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaAgua) {
        escolaAguaService.remover(idEscolaAgua);
        return ResponseEntity.ok().build();
    }
}