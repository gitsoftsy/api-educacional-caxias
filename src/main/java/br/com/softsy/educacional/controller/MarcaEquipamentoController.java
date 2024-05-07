package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.MarcaEquipamentoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.MarcaEquipamento;
import br.com.softsy.educacional.service.MarcaEquipamentoService;

@RestController
@RequestMapping("/marcaEquipamento")
public class MarcaEquipamentoController {

    @Autowired
    private MarcaEquipamentoService service;

	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<MarcaEquipamentoDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<MarcaEquipamentoDTO> marcaEquipamento = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(marcaEquipamento);
	}

    @GetMapping("/{idMarcaEquipamento}")
    public ResponseEntity<MarcaEquipamentoDTO> buscarPorId(@PathVariable Long idMarcaEquipamento) {
        return ResponseEntity.ok(service.buscarPorId(idMarcaEquipamento));
    }

    @PostMapping
    public ResponseEntity<MarcaEquipamentoDTO> cadastrar(@RequestBody @Valid MarcaEquipamentoDTO dto) {
        MarcaEquipamentoDTO marcaEquipamentoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(marcaEquipamentoDTO.getIdMarcaEquipamento()).toUri();
        return ResponseEntity.created(uri).body(marcaEquipamentoDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid MarcaEquipamentoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idMarcaEquipamento}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idMarcaEquipamento) {
        service.ativarDesativar('S', idMarcaEquipamento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idMarcaEquipamento}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idMarcaEquipamento) {
        service.ativarDesativar('N', idMarcaEquipamento);
        return ResponseEntity.ok().build();
    }
}
