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

import br.com.softsy.educacional.dto.TurnoDTO;
import br.com.softsy.educacional.service.TurnoService;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService service;

    @PersistenceContext
    private EntityManager entityManager;
    
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        List<TurnoDTO> turnos = service.listarTudo();
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/{idTurno}")
    public ResponseEntity<TurnoDTO> buscarPorId(@PathVariable Long idTurno) {
        TurnoDTO turnoDTO = service.buscarPorId(idTurno);
        return ResponseEntity.ok(turnoDTO);
    }
    
    @GetMapping("/conta/{idConta}/curso/{idCurso}/escola/{idEscola}/serie/{serie}")
    public List<Map<String, Object>> buscarTurnosPorIdContaAndIdCursoAndIdEscola(
            @PathVariable Long idConta,
            @PathVariable Long idCurso,
            @PathVariable Long idEscola,
            @PathVariable Integer serie) {

        List<Object[]> turnos = entityManager.createQuery(
                "SELECT DISTINCT t.idTurno, t.turno, t.horaInicio, t.horaFim " +
                        "FROM Turno t " +
                        "JOIN t.ofertaConcurso oc " +
                        "JOIN oc.concurso c " +
                        "WHERE c.ativo = 'S' " +
                        "AND oc.ativo = 'S' " +
                        "AND t.ativo = 'S' " +
                        "AND c.conta.idConta = :idConta " +
                        "AND oc.curso.idCurso = :idCurso " +
                        "AND oc.escola.idEscola = :idEscola " +
                        "AND oc.serie = :serie", Object[].class)
                .setParameter("idConta", idConta)
                .setParameter("idCurso", idCurso)
                .setParameter("idEscola", idEscola)
                .setParameter("serie", serie)
                .getResultList();

        // TRANSFORMANDO EM JSON
        List<Map<String, Object>> turnosJson = turnos.stream()
                .map(turno -> {
                    Map<String, Object> turnoJson = new HashMap<>();
                    turnoJson.put("idTurno", turno[0]);
                    turnoJson.put("turno", turno[1]);
                    turnoJson.put("horaInicio", turno[2]);
                    turnoJson.put("horaFim", turno[3]);
                    return turnoJson;
                })
                .collect(Collectors.toList());

        return turnosJson;
    }
    
    @GetMapping("/conta/{idConta}/curso/{idCurso}")
    public List<Map<String, Object>> buscarTurnosPorIdContaAndIdCurso(@PathVariable Long idConta, @PathVariable Long idCurso) {
        List<Object[]> turnos = entityManager.createQuery(
                "SELECT DISTINCT t.idTurno, t.turno, t.horaInicio, t.horaFim " +
                        "FROM Turno t " +
                        "JOIN t.ofertaConcurso oc " +
                        "JOIN oc.concurso c " +
                        "WHERE c.ativo = 'S' " +
                        "AND oc.ativo = 'S' " +
                        "AND t.ativo = 'S' " +
                        "AND c.conta.idConta = :idConta " +
                        "AND oc.curso.idCurso = :idCurso", Object[].class)
                .setParameter("idConta", idConta)
                .setParameter("idCurso", idCurso)
                .getResultList();

        // TRANSFORMANDO EM JSON
        List<Map<String, Object>> turnosJson = turnos.stream()
                .map(turno -> {
                    Map<String, Object> turnoJson = new HashMap<>();
                    turnoJson.put("idTurno", turno[0]);
                    turnoJson.put("turno", turno[1]);
                    turnoJson.put("horaInicio", turno[2]);
                    turnoJson.put("horaFim", turno[3]);
                    return turnoJson;
                })
                .collect(Collectors.toList());

        return turnosJson;
    }
    

    @GetMapping("/series/conta/{idConta}/curso/{idCurso}/turno/{idTurno}")
    public List<Map<String, Object>> getSeriesOfertaConcurso(@PathVariable Long idConta, @PathVariable Long idCurso, @PathVariable Long idTurno) {
        List<Integer> series = entityManager.createQuery(
                "SELECT DISTINCT oc.serie " +
                        "FROM OfertaConcurso oc " +
                        "JOIN oc.concurso c " +
                        "WHERE c.ativo = 'S' " +
                        "AND oc.ativo = 'S' " +
                        "AND c.conta.idConta = :idConta " +
                        "AND oc.curso.idCurso = :idCurso " +
                        "AND oc.turno.idTurno = :idTurno", Integer.class)
                .setParameter("idConta", idConta)
                .setParameter("idCurso", idCurso)
                .setParameter("idTurno", idTurno)
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
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<TurnoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<TurnoDTO> turno = service.buscarPorIdConta(idConta);
        return ResponseEntity.ok(turno);
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> cadastrar(@RequestBody @Valid TurnoDTO dto) {
        TurnoDTO turnoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(turnoDTO.getIdTurno()).toUri();
        return ResponseEntity.created(uri).body(turnoDTO);
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> atualizar(@RequestBody @Valid TurnoDTO dto) {
        TurnoDTO turnoDTO = service.atualizar(dto);
        return ResponseEntity.ok(turnoDTO);
    }

	@PutMapping("/{idTurno}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idTurno){
		service.ativarDesativar('S', idTurno);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idTurno}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTurno){
		service.ativarDesativar('N', idTurno);
		return ResponseEntity.ok().build();
	}
}