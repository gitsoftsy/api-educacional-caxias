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

import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.FonteEnergiaEletricaDTO;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.FonteEnergiaEletrica;
import br.com.softsy.educacional.service.FonteEnergiaEletricaService;

@RestController
@RequestMapping("/fonteEnergia")
public class FonteEnergiaEletricaController {
	
	@Autowired FonteEnergiaEletricaService service;
	
	@GetMapping("/conta/{idConta}")
		public ResponseEntity<List<FonteEnergiaEletricaDTO>> buscarPorIdConta(@PathVariable Long idConta){
			List<FonteEnergiaEletricaDTO> fonteEnergiaEletrica = service.buscarPorIdConta(idConta);
			return ResponseEntity.ok(fonteEnergiaEletrica);
		}
	
	@GetMapping("/{idFonteEnergiaEletrica}")
	public ResponseEntity<FonteEnergiaEletricaDTO> buscarPorId(@PathVariable Long idFonteEnergiaEletrica){
		return ResponseEntity.ok(service.buscarPorId(idFonteEnergiaEletrica));
	}
	
	@PostMapping
	public ResponseEntity<FonteEnergiaEletricaDTO> cadastrar(@RequestBody @Valid FonteEnergiaEletricaDTO dto){
		FonteEnergiaEletricaDTO FonteDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FonteDTO.getIdFonteEnergiaEletrica()).toUri();
		return ResponseEntity.created(uri).body(FonteDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid FonteEnergiaEletricaDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idFonteEnergiaEletrica}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idFonteEnergiaEletrica){
		service.ativaDesativa('S', idFonteEnergiaEletrica);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("/{idFonteEnergiaEletrica}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idFonteEnergiaEletrica){
		service.ativaDesativa('N', idFonteEnergiaEletrica);
		return ResponseEntity.ok().build();	
	}

}
