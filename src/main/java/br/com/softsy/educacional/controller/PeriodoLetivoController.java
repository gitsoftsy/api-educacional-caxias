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

import br.com.softsy.educacional.dto.CadastroPeriodoLetivoDTO;
import br.com.softsy.educacional.dto.CargoProfessorDTO;
import br.com.softsy.educacional.dto.PeriodoLetivoDTO;
import br.com.softsy.educacional.service.PeriodoLetivoService;

@RestController
@RequestMapping("/periodoletivo")
public class PeriodoLetivoController {

    @Autowired
    private PeriodoLetivoService periodoLetivoService;

    @GetMapping
    public ResponseEntity<List<PeriodoLetivoDTO>> listar() {
        List<PeriodoLetivoDTO> periodosLetivos = periodoLetivoService.listarTudo();
        return ResponseEntity.ok(periodosLetivos);
    }
    
    @GetMapping("/{idPeriodoLetivo}")
    public ResponseEntity<PeriodoLetivoDTO> buscarPorId(@PathVariable Long idPeriodoLetivo) {
        return ResponseEntity.ok(periodoLetivoService.buscarPorId(idPeriodoLetivo));
    }

    @PostMapping
    public ResponseEntity<PeriodoLetivoDTO> cadastrar(@RequestBody @Valid CadastroPeriodoLetivoDTO dto) {
        PeriodoLetivoDTO periodoLetivoDTO = periodoLetivoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(periodoLetivoDTO.getIdPeriodoLetivo()).toUri();
        return ResponseEntity.created(uri).body(periodoLetivoDTO);
    }

    @PutMapping
    public ResponseEntity<PeriodoLetivoDTO> atualizar(@RequestBody @Valid CadastroPeriodoLetivoDTO dto) {
        PeriodoLetivoDTO periodoLetivoDTO = periodoLetivoService.atualizar(dto);
        return ResponseEntity.ok(periodoLetivoDTO);
    }
    
	@PutMapping("/{idPeriodoLetivo}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idPeriodoLetivo){
		periodoLetivoService.ativaDesativa('S', idPeriodoLetivo);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idPeriodoLetivo}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idPeriodoLetivo){
		periodoLetivoService.ativaDesativa('N', idPeriodoLetivo);
		return ResponseEntity.ok().build();
	}
}
