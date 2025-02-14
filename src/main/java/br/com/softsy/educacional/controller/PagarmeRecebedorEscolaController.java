package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import br.com.softsy.educacional.dto.PagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.PagarmeRecebedorEscolaService;

@RestController
@RequestMapping("/recebedorEscola")
public class PagarmeRecebedorEscolaController {

	@Autowired
	private PagarmeRecebedorEscolaService pagarmeRecebedorEscolaService;

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<PagarmeRecebedorEscolaDTO> recebedorEscola = pagarmeRecebedorEscolaService.listarTudo();
			return ResponseEntity.ok(recebedorEscola);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@GetMapping("/{idPagarmeRecebedorEscola}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idPagarmeRecebedorEscola) {
		try {
			PagarmeRecebedorEscolaDTO recebedorEscola = pagarmeRecebedorEscolaService
					.buscarPorId(idPagarmeRecebedorEscola);
			return ResponseEntity.ok(recebedorEscola);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
		}
	}

	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<?> listarParceiroPorEscola(@PathVariable Long idEscola) {
	    try {
	        List<Map<String, Object>> recebedores = pagarmeRecebedorEscolaService.listarParceiroPorEscola(idEscola);
	        return ResponseEntity.ok(recebedores);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
	    }
	}
	 
	
	@GetMapping("/recebedor/{idRecebedor}")
	public ResponseEntity<?> listarEscolaPorRecebedor(@PathVariable Long idRecebedor) {
	    try {
	        List<Map<String, Object>> recebedores = pagarmeRecebedorEscolaService.listarEscolaPorRecebedor(idRecebedor);
	        return ResponseEntity.ok(recebedores);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
	    }
	}

	@PostMapping
	public ResponseEntity<PagarmeRecebedorEscolaDTO> cadastrar(
			@RequestBody @Valid CadastroPagarmeRecebedorEscolaDTO dto) {
		PagarmeRecebedorEscolaDTO cadastroDTO = pagarmeRecebedorEscolaService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getIdPagarmeRecebedorEscola()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroPagarmeRecebedorEscolaDTO dto) {
		try {
			return ResponseEntity.ok(pagarmeRecebedorEscolaService.atualizar(dto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@PutMapping("/{idPagarmeRecebedorEscola}/ativar")
	public ResponseEntity<String> ativar(@PathVariable Long idPagarmeRecebedorEscola) {
		try {
			pagarmeRecebedorEscolaService.ativaDesativa('S', idPagarmeRecebedorEscola);
			return ResponseEntity.ok("O recebedor foi ativado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao ativar recebedor: " + e.getMessage());
		}
	}

	@PutMapping("/{idPagarmeRecebedorEscola}/desativar")
	public ResponseEntity<String> desativar(@PathVariable Long idPagarmeRecebedorEscola) {
		try {
			pagarmeRecebedorEscolaService.ativaDesativa('N', idPagarmeRecebedorEscola);
			return ResponseEntity.ok("O recebedor foi desativado.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao desativar recebedor: " + e.getMessage());
		}
	}
}
