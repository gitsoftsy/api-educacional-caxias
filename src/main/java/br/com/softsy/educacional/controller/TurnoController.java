package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
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

import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.dto.TurnoDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.TurnoService;
import br.com.softsy.educacional.service.TurmaService;

@RestController
@RequestMapping("/turno")
public class TurnoController {

	@Autowired
	private TurnoService service;

	@Autowired
	private TurmaService turmaService;

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

	@GetMapping("/conta/{idConta}/curso/{idCurso}/escola/{idEscola}/serie/{idSerie}")
	public List<Map<String, Object>> buscarTurnosPorIdContaAndIdCursoAndIdEscola(@PathVariable Long idConta,
			@PathVariable Long idCurso, @PathVariable Long idEscola, @PathVariable Long idSerie) {

		List<Object[]> turnos = entityManager
				.createQuery("SELECT DISTINCT t.idTurno, t.turno, t.horaInicio, t.horaFim " + "FROM Turno t "
						+ "JOIN t.ofertaConcurso oc " + "JOIN oc.concurso c " + "WHERE c.ativo = 'S' "
						+ "AND oc.ativo = 'S' " + "AND t.ativo = 'S' " + "AND c.conta.idConta = :idConta "
						+ "AND oc.curso.idCurso = :idCurso " + "AND oc.escola.idEscola = :idEscola "
						+ "AND oc.serie.idSerie = :idSerie", Object[].class)
				.setParameter("idConta", idConta).setParameter("idCurso", idCurso).setParameter("idEscola", idEscola)
				.setParameter("idSerie", idSerie).getResultList();

		List<Map<String, Object>> turnosJson = turnos.stream().map(turno -> {
			Map<String, Object> turnoJson = new HashMap<>();
			turnoJson.put("idTurno", turno[0]);
			turnoJson.put("turno", turno[1]);
			turnoJson.put("horaInicio", turno[2]);
			turnoJson.put("horaFim", turno[3]);
			return turnoJson;
		}).collect(Collectors.toList());

		return turnosJson;
	}

	@GetMapping("/conta/{idConta}/curso/{idCurso}")
	public List<Map<String, Object>> buscarTurnosPorIdContaAndIdCurso(@PathVariable Long idConta,
			@PathVariable Long idCurso) {
		List<Object[]> turnos = entityManager
				.createQuery("SELECT DISTINCT t.idTurno, t.turno, t.horaInicio, t.horaFim " + "FROM Turno t "
						+ "JOIN t.ofertaConcurso oc " + "JOIN oc.concurso c " + "WHERE c.ativo = 'S' "
						+ "AND oc.ativo = 'S' " + "AND t.ativo = 'S' " + "AND c.conta.idConta = :idConta "
						+ "AND oc.curso.idCurso = :idCurso", Object[].class)
				.setParameter("idConta", idConta).setParameter("idCurso", idCurso).getResultList();

		// TRANSFORMANDO EM JSON
		List<Map<String, Object>> turnosJson = turnos.stream().map(turno -> {
			Map<String, Object> turnoJson = new HashMap<>();
			turnoJson.put("idTurno", turno[0]);
			turnoJson.put("turno", turno[1]);
			turnoJson.put("horaInicio", turno[2]);
			turnoJson.put("horaFim", turno[3]);
			return turnoJson;
		}).collect(Collectors.toList());

		return turnosJson;
	}

	@GetMapping("/series/conta/{idConta}/curso/{idCurso}/turno/{idTurno}")
	public List<Map<String, Object>> getSeriesOfertaConcurso(@PathVariable Long idConta, @PathVariable Long idCurso,
			@PathVariable Long idTurno) {
		List<Object> series = entityManager
				.createQuery("SELECT DISTINCT oc.serie " + "FROM OfertaConcurso oc " + "JOIN oc.concurso c "
						+ "WHERE c.ativo = 'S' " + "AND oc.ativo = 'S' " + "AND c.conta.idConta = :idConta "
						+ "AND oc.curso.idCurso = :idCurso " + "AND oc.turno.idTurno = :idTurno", Object.class)
				.setParameter("idConta", idConta).setParameter("idCurso", idCurso).setParameter("idTurno", idTurno)
				.getResultList();

		List<Map<String, Object>> seriesJson = series.stream().map(serie -> {
			Map<String, Object> serieJson = new HashMap<>();
			serieJson.put("serie", serie);
			return serieJson;
		}).collect(Collectors.toList());

		return seriesJson;
	}

	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<TurnoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
		List<TurnoDTO> turno = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(turno);
	}

	@GetMapping("ativos/conta/{idConta}")
	public ResponseEntity<?> listarTurnosContaAtiva(@PathVariable String idConta) {
		try {
			Long id = Long.parseLong(idConta);
			if (id <= 0) {
				return ResponseEntity.badRequest()
						.body(new AllResponse("O idConta não pode ser um número negativo ou zero!", new ArrayList<>()));
			}
			List<TurnoDTO> turnos = service.buscarTurnosAtivosPorIdConta(id);
			return ResponseEntity.ok(turnos);

		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest()
					.body(new AllResponse("O valor de idConta deve ser um número válido.", new ArrayList<>()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		}
	}

	@GetMapping("porPeriodoLetivo/{idPeriodoLetivo}")
	public ResponseEntity<?> listarTurnosPorPeriodoLetivo(@PathVariable String idPeriodoLetivo) {
		try {

			Long id = Long.parseLong(idPeriodoLetivo);
			if (id <= 0) {
				return ResponseEntity.badRequest().body(new AllResponse(
						"O idPeriodoLetivo não pode ser um número negativo ou zero!", new ArrayList<>()));
			}

			List<Map<String, Object>> turnos = turmaService.listarTurnosPorPeriodoLetivo(id);

			return ResponseEntity.ok(turnos);

		} catch (NumberFormatException e) {

			return ResponseEntity.badRequest()
					.body(new AllResponse("O valor de idPeriodoLetivo deve ser um número válido.", new ArrayList<>()));
		} catch (IllegalArgumentException e) {

			return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
		} catch (Exception e) {

			return ResponseEntity.status(500)
					.body(new AllResponse("Ocorreu um erro inesperado ao processar a solicitação.", new ArrayList<>()));
		}
	}

	@PostMapping
	public ResponseEntity<TurnoDTO> cadastrar(@RequestBody @Valid TurnoDTO dto) {
		TurnoDTO turnoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(turnoDTO.getIdTurno())
				.toUri();
		return ResponseEntity.created(uri).body(turnoDTO);
	}

	@PutMapping
	public ResponseEntity<TurnoDTO> atualizar(@RequestBody @Valid TurnoDTO dto) {
		TurnoDTO turnoDTO = service.atualizar(dto);
		return ResponseEntity.ok(turnoDTO);
	}

	@PutMapping("/{idTurno}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idTurno) {
		service.ativarDesativar('S', idTurno);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idTurno}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTurno) {
		service.ativarDesativar('N', idTurno);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/prematricula")
	public ResponseEntity<AllResponse> listarTurnoPorPeriodoSerieDisciplinaEscola(
			@RequestParam(value = "idPeriodoLetivo", required = false) Long idPeriodoLetivo,
			@RequestParam(value = "idEscola", required = false) Long idEscola,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idSerie", required = false) Long idSerie
	)
	{
 
		if (idPeriodoLetivo == null && idEscola == null && idDisciplina == null && idSerie == null) {
			return ResponseEntity.badRequest()
					.body(new AllResponse("Por favor, informe o parâmetro na requisição.", new ArrayList<>()));
		}
 
		List<Map<String, Object>> result = service.listarTurnoPorPeriodoSerieDisciplinaEscola(idPeriodoLetivo, idEscola, idDisciplina, idSerie);
 
		if (result.isEmpty()) {
			return ResponseEntity
					.ok(new AllResponse("Não existem resultados para essa requisição.", new ArrayList<>()));
		}
 
		return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
	}
}