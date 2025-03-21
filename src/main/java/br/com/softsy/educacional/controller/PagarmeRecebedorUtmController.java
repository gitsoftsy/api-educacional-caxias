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

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorUtmDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorUtmDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.PagarmeRecebedorUtmService;

@RestController
@RequestMapping("/recebedorUtm")
public class PagarmeRecebedorUtmController {

	@Autowired
	private PagarmeRecebedorUtmService pagarmeRecebedorUtmService;

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<PagarmeRecebedorUtmDTO> recebedorUtm = pagarmeRecebedorUtmService.listarTudo();
			return ResponseEntity.ok(recebedorUtm);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@GetMapping("/{idPagarmeRecebedorUtm}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idPagarmeRecebedorUtm) {
		try {
			PagarmeRecebedorUtmDTO recebedorUtm = pagarmeRecebedorUtmService
					.buscarPorId(idPagarmeRecebedorUtm);
			return ResponseEntity.ok(recebedorUtm);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
		}
	}

	@GetMapping("/utm/{idUtm}")
	public ResponseEntity<?> listarParceiroPorUtm(@PathVariable Long idUtm) {
	    try {
	        List<Map<String, Object>> recebedores = pagarmeRecebedorUtmService.listarParceiroPorUtm(idUtm);
	        return ResponseEntity.ok(recebedores);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
	    }
	}
	 
	
	@GetMapping("/recebedor/{idRecebedor}")
	public ResponseEntity<?> listarUtmPorRecebedor(@PathVariable Long idRecebedor) {
	    try {
	        List<Map<String, Object>> recebedores = pagarmeRecebedorUtmService.listarUtmPorRecebedor(idRecebedor);
	        return ResponseEntity.ok(recebedores);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
	    }
	}
	 
	@PostMapping
	public ResponseEntity<PagarmeRecebedorUtmDTO> cadastrar(@RequestBody @Valid CadastroPagarmeRecebedorUtmDTO dto) {
		PagarmeRecebedorUtmDTO cadastroDTO = pagarmeRecebedorUtmService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getIdPagarmeRecebedorUtm()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroPagarmeRecebedorUtmDTO dto) {
		try {
			return ResponseEntity.ok(pagarmeRecebedorUtmService.atualizar(dto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@PutMapping("/{idPagarmeRecebedorUtm}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idPagarmeRecebedorUtm) {
		try {
			pagarmeRecebedorUtmService.ativaDesativa('S', idPagarmeRecebedorUtm);
			return ResponseEntity.ok("O recebedor foi ativado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao ativar recebedor: " + e.getMessage());
		}
	}

	@PutMapping("/{idPagarmeRecebedorUtm}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idPagarmeRecebedorUtm) {
		try {
			pagarmeRecebedorUtmService.ativaDesativa('N', idPagarmeRecebedorUtm);
			return ResponseEntity.ok("O recebedor foi desativado.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao desativar recebedor: " + e.getMessage());
		}
	}

}
