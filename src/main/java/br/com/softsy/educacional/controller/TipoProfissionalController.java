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

import br.com.softsy.educacional.dto.TipoProfissionalDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.TipoProfissional;
import br.com.softsy.educacional.service.TipoProfissionalService;

@RestController
@RequestMapping("/tipoProfissional")
public class TipoProfissionalController {

    @Autowired
    private TipoProfissionalService tipoProfissionalService;

	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<TipoProfissionalDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<TipoProfissionalDTO> tipoProfissinal = tipoProfissionalService.buscarPorIdConta(idConta);
		return ResponseEntity.ok(tipoProfissinal);
	}

    @GetMapping("/{idInstrPedagogico}")
    public ResponseEntity<TipoProfissionalDTO> buscarPorId(@PathVariable Long idInstrPedagogico) {
        return ResponseEntity.ok(tipoProfissionalService.buscarPorId(idInstrPedagogico));
    }

    @PostMapping
    public ResponseEntity<TipoProfissionalDTO> cadastrar(@RequestBody @Valid TipoProfissionalDTO dto) {
        TipoProfissionalDTO tipoProfissionalDTO = tipoProfissionalService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tipoProfissionalDTO.getIdTipoProfissional()).toUri();
        return ResponseEntity.created(uri).body(tipoProfissionalDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid TipoProfissionalDTO dto) {
        return ResponseEntity.ok(tipoProfissionalService.atualizar(dto));
    }

    @PutMapping("/{idInstrPedagogico}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idInstrPedagogico) {
        tipoProfissionalService.ativarDesativarTipoProfissional('S', idInstrPedagogico);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idInstrPedagogico}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idInstrPedagogico) {
        tipoProfissionalService.ativarDesativarTipoProfissional('N', idInstrPedagogico);
        return ResponseEntity.ok().build();
    }
}