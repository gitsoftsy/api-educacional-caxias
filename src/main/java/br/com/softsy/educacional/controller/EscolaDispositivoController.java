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

import br.com.softsy.educacional.dto.EscolaDispositivoDTO;
import br.com.softsy.educacional.dto.EscolaInfraestruturaDTO;
import br.com.softsy.educacional.service.EscolaDispositivoService;

@RestController
@RequestMapping("/escolaDispositivo")
public class EscolaDispositivoController {

    @Autowired
    private EscolaDispositivoService escolaDispositivoService;

    @PostMapping
    public ResponseEntity<EscolaDispositivoDTO> cadastrar(@RequestBody @Valid EscolaDispositivoDTO dto) {
        EscolaDispositivoDTO escolaDispositivoDTO = escolaDispositivoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(escolaDispositivoDTO.getIdEscolaDispositivo()).toUri();
        return ResponseEntity.created(uri).body(escolaDispositivoDTO);
    }
    
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaDispositivoDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaDispositivoDTO> escolaDispositivo = escolaDispositivoService.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(escolaDispositivo);
	}

    @PutMapping
    public ResponseEntity<EscolaDispositivoDTO> atualizar(@RequestBody @Valid EscolaDispositivoDTO dto) {
        EscolaDispositivoDTO escolaDispositivoDTO = escolaDispositivoService.atualizar(dto);
        return ResponseEntity.ok(escolaDispositivoDTO);
    }

    @DeleteMapping("/{idEscolaDispositivo}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaDispositivo) {
        escolaDispositivoService.remover(idEscolaDispositivo);
        return ResponseEntity.ok().build();
    }
}