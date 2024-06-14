package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.model.CaminhoImagemRequest;
import br.com.softsy.educacional.service.EscolaService;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
	
	@Autowired
	private EscolaService service;
	
    @PersistenceContext
    private EntityManager entityManager;
	
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<EscolaDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<EscolaDTO> escola = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(escola);
	}
	
	@GetMapping("/{id}/logo")
    public ResponseEntity<String> getLogoById(@PathVariable("id") Long id, @RequestBody CaminhoImagemRequest request) throws IOException {
        String caminho = request.getCaminho();
		String logo = service.getLogoById(id, caminho);

       return ResponseEntity.ok(logo);
    }
	
    @GetMapping("/conta/{idConta}/curso/{idCurso}/turno/{idTurno}/serie/{serie}")
    public List<Map<String, Object>> getEscolasOfertaConcurso(
            @PathVariable Long idConta,
            @PathVariable Long idCurso,
            @PathVariable Long idTurno,
            @PathVariable Integer serie) {

        List<Object[]> escolas = entityManager.createQuery(
                "SELECT DISTINCT e.idEscola, e.nomeEscola " +
                        "FROM Escola e " +
                        "JOIN e.ofertaConcurso oc " +
                        "JOIN oc.concurso c " +
                        "WHERE c.ativo = 'S' " +
                        "AND oc.ativo = 'S' " +
                        "AND e.ativo = 'S' " +
                        "AND c.conta.idConta = :idConta " +
                        "AND oc.curso.idCurso = :idCurso " +
                        "AND oc.turno.idTurno = :idTurno " +
                        "AND oc.serie = :serie", Object[].class)
                .setParameter("idConta", idConta)
                .setParameter("idCurso", idCurso)
                .setParameter("idTurno", idTurno)
                .setParameter("serie", serie)
                .getResultList();

        // Mapeando os resultados para objetos JSON
        List<Map<String, Object>> escolasJson = escolas.stream()
                .map(escola -> {
                    Map<String, Object> escolaJson = new HashMap<>();
                    escolaJson.put("idEscola", escola[0]);
                    escolaJson.put("nomeEscola", escola[1]);
                    return escolaJson;
                })
                .collect(Collectors.toList());

        return escolasJson;
    }
	
    @GetMapping("/ativos/{idConta}/{idCurso}")
    public List<Map<String, Object>> buscarEscolaPorIdContaEIdCurso(@PathVariable Long idConta, @PathVariable Long idCurso) {
        List<Object[]> escolas = entityManager.createQuery(
                "SELECT DISTINCT e.idEscola, e.nomeEscola " +
                        "FROM Escola e " +
                        "JOIN e.ofertaConcurso oc " +
                        "JOIN oc.concurso c " +
                        "WHERE c.ativo = 'S' " +
                        "AND oc.ativo = 'S' " +
                        "AND e.ativo = 'S' " +
                        "AND c.conta.idConta = :idConta " +
                        "AND oc.curso.idCurso = :idCurso", Object[].class)
                .setParameter("idConta", idConta)
                .setParameter("idCurso", idCurso)
                .getResultList();

        // TRANSFORMANDO EM JSON
        List<Map<String, Object>> escolasJson = escolas.stream()
                .map(escola -> {
                    Map<String, Object> escolaJson = new HashMap<>();
                    escolaJson.put("idEscola", escola[0]);
                    escolaJson.put("nomeEscola", escola[1]);
                    return escolaJson;
                })
                .collect(Collectors.toList());

        return escolasJson;
    }
	
	@PostMapping
	public ResponseEntity<CadastroEscolaDTO> cadastrar(@RequestBody @Valid CadastroEscolaDTO dto){
		CadastroEscolaDTO cadastroDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getIdEscola()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}
	
	@GetMapping("/ativos")
	public ResponseEntity<List<EscolaDTO>> listarAtivos(){
		return ResponseEntity.ok(service.listarAtivos());
	}
	
	@GetMapping("/{idEscola}")
	public ResponseEntity<EscolaDTO> buscarPorId(@PathVariable Long idEscola){
		return ResponseEntity.ok(service.buscarPorId(idEscola));
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idEscola}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idEscola){
		service.ativaDesativa('S', idEscola);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idEscola}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idEscola){
		service.ativaDesativa('N', idEscola);
		return ResponseEntity.ok().build();
	}

}
