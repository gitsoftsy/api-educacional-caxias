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

import br.com.softsy.educacional.dto.TurnoDTO;
import br.com.softsy.educacional.service.TurnoService;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService service;

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        List<TurnoDTO> turnos = service.listarTudo();
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/{idTurno}")
    public ResponseEntity<TurnoDTO> buscarPorId(@PathVariable Long idTurno) {
        TurnoDTO turnoDTO = service.buscarPorId(idTurno);
        return ResponseEntity.ok(turnoDTO);
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> cadastrar(@RequestBody @Valid TurnoDTO dto) {
        TurnoDTO turnoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(turnoDTO.getIdTurno()).toUri();
        return ResponseEntity.created(uri).body(turnoDTO);
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> atualizar(@RequestBody @Valid TurnoDTO dto) {
        TurnoDTO turnoDTO = service.atualizar(dto);
        return ResponseEntity.ok(turnoDTO);
    }

	@PutMapping("/{idTurno}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idTurno){
		service.ativarDesativar('S', idTurno);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idTurno}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTurno){
		service.ativarDesativar('N', idTurno);
		return ResponseEntity.ok().build();
	}
}