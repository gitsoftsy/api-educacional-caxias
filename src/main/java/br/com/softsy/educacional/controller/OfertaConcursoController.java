package br.com.softsy.educacional.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDTO;
import br.com.softsy.educacional.service.OfertaConcursoService;

@RestController
@RequestMapping("/ofertasConcurso")
public class OfertaConcursoController {

    @Autowired
    private OfertaConcursoService ofertaConcursoService;
    
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<CadastroOfertaConcursoDTO>> listar() {
        List<CadastroOfertaConcursoDTO> ofertas = ofertaConcursoService.listarTudo();
        return ResponseEntity.ok(ofertas);
    }
    
    @GetMapping("/{idOfertaConcurso}")
    public ResponseEntity<CadastroOfertaConcursoDTO> buscarPorId(@PathVariable Long idOfertaConcurso) {
        CadastroOfertaConcursoDTO ofertaDto = ofertaConcursoService.buscarPorId(idOfertaConcurso);
        return ResponseEntity.ok(ofertaDto);
    }
    
    @GetMapping("/series/conta/{idConta}/curso/{idCurso}/escola/{idEscola}")
    public List<Map<String, Object>> getSeries(@PathVariable Long idConta, @PathVariable Long idCurso, @PathVariable Long idEscola) {
        List<Integer> series = entityManager.createQuery(
                "SELECT DISTINCT oc.serie " +
                        "FROM OfertaConcurso oc " +
                        "JOIN oc.concurso c " +
                        "WHERE c.ativo = 'S' " +
                        "AND oc.ativo = 'S' " +
                        "AND c.conta.idConta = :idConta " +
                        "AND oc.curso.idCurso = :idCurso " +
                        "AND oc.escola.idEscola = :idEscola", Integer.class)
                .setParameter("idConta", idConta)
                .setParameter("idCurso", idCurso)
                .setParameter("idEscola", idEscola)
                .getResultList();

        // TRANSFORMANDO EM JSON
        List<Map<String, Object>> seriesJson = series.stream()
                .map(serie -> {
                    Map<String, Object> serieJson = new HashMap<>();
                    serieJson.put("serie", serie);
                    return serieJson;
                })
                .collect(Collectors.toList());

        return seriesJson;
    }
    
    @GetMapping("/curso/{idCurso}/turno/{idTurno}/serie/{serie}/escola/{idEscola}")
    public ResponseEntity<Map<String, Object>> buscarIdOfertaConcurso(
        @PathVariable Long idCurso,
        @PathVariable Long idTurno,
        @PathVariable Integer serie,
        @PathVariable Long idEscola
    ) {
    	Long idOfertaConcurso = (Long) entityManager.createQuery(
            "SELECT oc.idOfertaConcurso " +
            "FROM OfertaConcurso oc " +
            "WHERE oc.curso.idCurso = :idCurso " +
            "AND oc.turno.idTurno = :idTurno " +
            "AND oc.serie = :serie " +
            "AND oc.escola.idEscola = :idEscola"
        )
        .setParameter("idCurso", idCurso)
        .setParameter("idTurno", idTurno)
        .setParameter("serie", serie)
        .setParameter("idEscola", idEscola)
        .getSingleResult();

        Map<String, Object> response = new HashMap<>();
        response.put("idOfertaCurso", idOfertaConcurso);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/concurso/{idConcurso}")
    public ResponseEntity<List<OfertaConcursoDTO>> buscarPorIdConcurso(@PathVariable Long idConcurso) {
        List<OfertaConcursoDTO> ofertas = ofertaConcursoService.buscarPorIdConcurso(idConcurso);
        return ResponseEntity.ok(ofertas);
    }

    @PostMapping
    public ResponseEntity<CadastroOfertaConcursoDTO> cadastrar(@RequestBody @Valid CadastroOfertaConcursoDTO dto) {
        CadastroOfertaConcursoDTO ofertaDTO = ofertaConcursoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ofertaDTO.getIdOfertaConcurso()).toUri();
        return ResponseEntity.created(uri).body(ofertaDTO);
    }

    @PutMapping
    public ResponseEntity<CadastroOfertaConcursoDTO> atualizar(@RequestBody @Valid CadastroOfertaConcursoDTO dto) {
        CadastroOfertaConcursoDTO ofertaDTO = ofertaConcursoService.atualizar(dto);
        return ResponseEntity.ok(ofertaDTO);
    }
    
    @PutMapping("/{idOfertaConcurso}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idOfertaConcurso) {
        ofertaConcursoService.ativaDesativa('S', idOfertaConcurso);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idOfertaConcurso}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idOfertaConcurso) {
        ofertaConcursoService.ativaDesativa('N', idOfertaConcurso);
        return ResponseEntity.ok().build();
    }
}