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

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorCursoDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorCursoDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.PagarmeRecebedorCursoService;

@RestController
@RequestMapping("/recebedorCurso")
public class PagarmeRecebedorCursoController {

	@Autowired
	private PagarmeRecebedorCursoService pagarmeRecebedorCursoService;

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<PagarmeRecebedorCursoDTO> recebedorUtm = pagarmeRecebedorCursoService.listarTudo();
			return ResponseEntity.ok(recebedorUtm);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@GetMapping("/{idPagarmeRecebedorCurso}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long idPagarmeRecebedorCurso) {
		try {
			PagarmeRecebedorCursoDTO recebedorUtm = pagarmeRecebedorCursoService.buscarPorId(idPagarmeRecebedorCurso);
			return ResponseEntity.ok(recebedorUtm);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
		}
	}
	
	@GetMapping("/curso/{idCurso}")
	public ResponseEntity<?> listarParceiroPorCurso(@PathVariable Long idCurso) {
	    try {
	        List<Map<String, Object>> recebedores = pagarmeRecebedorCursoService.listarParceiroPorCurso(idCurso);
	        return ResponseEntity.ok(recebedores);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
	    }
	}
	 
	
	@GetMapping("/recebedor/{idRecebedor}")
	public ResponseEntity<?> listarCursoPorParceiro(@PathVariable Long idRecebedor) {
	    try {
	        List<Map<String, Object>> recebedores = pagarmeRecebedorCursoService.listarCursoPorParceiro(idRecebedor);
	        return ResponseEntity.ok(recebedores);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(new AllResponse("Erro interno", new ArrayList<>()));
	    }
	}
	
	@PostMapping
	public ResponseEntity<PagarmeRecebedorCursoDTO> cadastrar(@RequestBody @Valid CadastroPagarmeRecebedorCursoDTO dto) {
		PagarmeRecebedorCursoDTO cadastroDTO = pagarmeRecebedorCursoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getIdPagarmeRecebedorCurso()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroPagarmeRecebedorCursoDTO dto) {
		try {
			return ResponseEntity.ok(pagarmeRecebedorCursoService.atualizar(dto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@PutMapping("/{idPagarmeRecebedorCurso}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idPagarmeRecebedorCurso) {
		try {
			pagarmeRecebedorCursoService.ativaDesativa('S', idPagarmeRecebedorCurso);
			return ResponseEntity.ok("O recebedor foi ativado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao ativar recebedor: " + e.getMessage());
		}
	}

	@PutMapping("/{idPagarmeRecebedorCurso}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idPagarmeRecebedorCurso) {
		try {
			pagarmeRecebedorCursoService.ativaDesativa('N', idPagarmeRecebedorCurso);
			return ResponseEntity.ok("O recebedor foi desativado.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao desativar recebedor: " + e.getMessage());
		}
	}

}
