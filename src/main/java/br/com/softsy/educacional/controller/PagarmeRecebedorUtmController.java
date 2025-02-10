//package br.com.softsy.educacional.controller;
//
//import java.net.URI;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorUtmDTO;
//import br.com.softsy.educacional.dto.PagarmeRecebedorUtmDTO;
//import br.com.softsy.educacional.dto.TurmaDTO;
//import br.com.softsy.educacional.service.PagarmeRecebedorUtmService;
//
//@RestController
//@RequestMapping("/recebedorUtm")
//public class PagarmeRecebedorUtmController {
//
//	@Autowired
//	private PagarmeRecebedorUtmService pagarmeRecebedorUtmService;
//
//	@GetMapping
//	public ResponseEntity<List<PagarmeRecebedorUtmDTO>> listar() {
//		List<PagarmeRecebedorUtmDTO> recebedorUtm = pagarmeRecebedorUtmService.listarTudo();
//		return ResponseEntity.ok(recebedorUtm);
//	}
//
//	@GetMapping("/{idPagarmeRecebedorUtm}")
//	public ResponseEntity<PagarmeRecebedorUtmDTO> buscarPorId(@PathVariable Long idTurma) {
//		PagarmeRecebedorUtmDTO recebedorUtm = pagarmeRecebedorUtmService.buscarPorId(idTurma);
//		return ResponseEntity.ok(recebedorUtm);
//	}
//
//	@PostMapping
//	public ResponseEntity<PagarmeRecebedorUtmDTO> cadastrar(@RequestBody @Valid CadastroPagarmeRecebedorUtmDTO dto) {
//		PagarmeRecebedorUtmDTO cadastroDTO = pagarmeRecebedorUtmService.salvar(dto);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//				.buildAndExpand(cadastroDTO.getIdPagarmeRecebedorUtm()).toUri();
//		return ResponseEntity.created(uri).body(cadastroDTO);
//	}
//
//	@PutMapping
//	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroPagarmeRecebedorUtmDTO dto) {
//		return ResponseEntity.ok(pagarmeRecebedorUtmService.atualizar(dto));
//	}
//
//	@PutMapping("/{idPagarmeRecebedorUtm}/ativar")
//	public ResponseEntity<?> ativar(@PathVariable Long idPagarmeRecebedorUtm) {
//		pagarmeRecebedorUtmService.ativaDesativa('S', idPagarmeRecebedorUtm);
//		return ResponseEntity.ok().build();
//	}
//
//	@PutMapping("/{idPagarmeRecebedorUtm}/desativar")
//	public ResponseEntity<?> desatviar(@PathVariable Long idPagarmeRecebedorUtm) {
//		pagarmeRecebedorUtmService.ativaDesativa('N', idPagarmeRecebedorUtm);
//		return ResponseEntity.ok().build();
//	}
//
//}
