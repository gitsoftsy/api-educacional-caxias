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

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorUtmDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.service.PagarmeRecebedorEscolaService;

@RestController
@RequestMapping("/recebedorUtm")
public class PagarmeRecebedorEscolaController {

	@Autowired
	private PagarmeRecebedorEscolaService pagarmeRecebedorEscolaService;

	@GetMapping
	public ResponseEntity<List<PagarmeRecebedorEscolaDTO>> listar() {
		List<PagarmeRecebedorEscolaDTO> recebedorEscola = pagarmeRecebedorEscolaService.listarTudo();
		return ResponseEntity.ok(recebedorEscola);
	}

	@GetMapping("/{idPagarmeRecebedorEscola}")
	public ResponseEntity<PagarmeRecebedorEscolaDTO> buscarPorId(@PathVariable Long idTurma) {
		PagarmeRecebedorEscolaDTO recebedorEscola = pagarmeRecebedorEscolaService.buscarPorId(idTurma);
		return ResponseEntity.ok(recebedorEscola);
	}

	@PostMapping
	public ResponseEntity<PagarmeRecebedorEscolaDTO> cadastrar(@RequestBody @Valid CadastroPagarmeRecebedorEscolaDTO dto) {
		PagarmeRecebedorEscolaDTO cadastroDTO = pagarmeRecebedorEscolaService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getIdPagarmeRecebedorEscola()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroPagarmeRecebedorEscolaDTO dto) {
		return ResponseEntity.ok(pagarmeRecebedorEscolaService.atualizar(dto));
	}

	@PutMapping("/{idPagarmeRecebedorEscola}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idPagarmeRecebedorEscola) {
		pagarmeRecebedorEscolaService.ativaDesativa('S', idPagarmeRecebedorEscola);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idPagarmeRecebedorEscola}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idPagarmeRecebedorEscola) {
		pagarmeRecebedorEscolaService.ativaDesativa('N', idPagarmeRecebedorEscola);
		return ResponseEntity.ok().build();
	}

}
