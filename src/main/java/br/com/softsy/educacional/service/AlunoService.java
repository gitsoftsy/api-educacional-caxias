package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AlunoDTO;
import br.com.softsy.educacional.dto.CadastroAlunoDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CurriculoRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.SerieRepository;
import br.com.softsy.educacional.repository.SituacaoAlunoRepository;
import br.com.softsy.educacional.repository.TipoMatriculaRepository;
import br.com.softsy.educacional.repository.TurnoRepository;


@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private SituacaoAlunoRepository situacaoAlunoRepository;
    
    @Autowired
    private TipoMatriculaRepository tipoMatriculaRepository;
    
    @Autowired
    private CurriculoRepository curriculoRepository;
    
	@Autowired
	private PasswordEncrypt encrypt;
	
	@Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<AlunoDTO> listarTudo() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());
    }
    
    

    @Transactional(readOnly = true)
    public AlunoDTO buscarPorId(Long id) {
        return new AlunoDTO(alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado")));
    }

    @Transactional(readOnly = true)
    public List<AlunoDTO> buscarPorIdConta(Long idConta) {
        List<Aluno> alunos = alunoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar aluno por ID da conta"));
        return alunos.stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlunoDTO salvar(CadastroAlunoDTO dto) {
    	Aluno aluno = criarAlunoAPartirDTO(dto);
        
        aluno.setSenha(encrypt.hashPassword(dto.getSenha()));
        aluno = alunoRepository.save(aluno);
        
        prematriculaAluno(aluno.getIdAluno(), dto.getTipoMatriculaId(), null);
        
        Optional<Candidato> candidatos = candidatoRepository.findById(dto.getCandidatoId());
        Candidato candidato = candidatos.get();
        candidato.setAluno(aluno.getIdAluno());

        candidatoRepository.save(candidato);
        
        return new AlunoDTO(aluno);
    }

    @Transactional
    public AlunoDTO atualizar(CadastroAlunoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getIdAluno())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        atualizaDados(aluno, dto);
        aluno = alunoRepository.save(aluno);
        return new AlunoDTO(aluno);
    }

    @Transactional
    public void excluir(Long id) {
        alunoRepository.deleteById(id);
    }

    private Aluno criarAlunoAPartirDTO(CadastroAlunoDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setConta(contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada")));
        aluno.setCurso(cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado")));
        aluno.setEscola(escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        aluno.setSerie(serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada")));
        aluno.setTurno(turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado")));
        aluno.setPessoa(pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada")));
        aluno.setCandidato(candidatoRepository.findById(dto.getCandidatoId())
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado")));
        aluno.setSituacaoAluno(situacaoAlunoRepository.findById(dto.getSituacaoAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Situação do Aluno não encontrada")));
        aluno.setCurriculo(curriculoRepository.findById(dto.getCurriculoId())
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado.")));
    
        Character geraPreMatricula = dto.getGeraPrematricula();
        Long tipoMatriculaId = dto.getTipoMatriculaId();

        try {
            validarGeraPrematricula(geraPreMatricula); 
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro: " + e.getMessage());
        }

        if ('S' == Character.toUpperCase(geraPreMatricula) && tipoMatriculaId == null) {
            throw new IllegalArgumentException("O campo 'tipoMatriculaId' é obrigatório quando 'geraPreMatricula' é 'S'.");
        }
        if('S' == Character.toUpperCase(geraPreMatricula)) {
        	tipoMatriculaRepository.findById(dto.getTipoMatriculaId())
            .orElseThrow(() -> new IllegalArgumentException("Tipo de Matricula não encontrada"));
        }
      
        aluno.setDataCadastro(dto.getDataCadastro() != null ? dto.getDataCadastro() : LocalDateTime.now());
        aluno.setAluno(dto.getAluno());
        aluno.setEmailInterno(dto.getEmailInterno());
        aluno.setSenha(dto.getSenha());
        
        return aluno;
    }

    private void atualizaDados(Aluno aluno, CadastroAlunoDTO dto) {
        aluno.setConta(contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada")));
        aluno.setCurso(cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado")));
        aluno.setEscola(escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        aluno.setSerie(serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada")));
        aluno.setTurno(turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado")));
        aluno.setPessoa(pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada")));
        aluno.setCandidato(candidatoRepository.findById(dto.getCandidatoId())
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado")));
        aluno.setSituacaoAluno(situacaoAlunoRepository.findById(dto.getSituacaoAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Situação do Aluno não encontrada")));
        aluno.setCurriculo(curriculoRepository.findById(dto.getCurriculoId())
                .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado.")));
        
        BeanUtils.copyProperties(dto, aluno, "idUsuario", "ativo", "dataCadastro", "senha", "emailInterno");
        aluno.setAluno(dto.getAluno());

        if(dto.getSenha() != null) {
        	aluno.setSenha(encrypt.hashPassword(dto.getSenha()));
		}
    }
    
    public void validarGeraPrematricula(Character geraPrematricula) {
        if (geraPrematricula == null || (geraPrematricula != 'S' && geraPrematricula != 'N')) {
            throw new IllegalArgumentException("O campo 'geraPrematricula' deve ser 'S' ou 'N'.");
        }
    }
    
    public List<Map<String, Object>> listarAlunosSemPrematricula(Long idTurma) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTAR_ALUNOS_SEM_PREMATRICULA(:pIdTurma)");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("pIdTurma", idTurma);
     

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idAluno", result[0]);
            resultMap.put("idConta", result[1]);
            resultMap.put("idCurso", result[2]);
            resultMap.put("idEscola", result[3]);
            resultMap.put("idSerie", result[4]);
            resultMap.put("idTurno", result[5]);
            resultMap.put("idPessoa", result[6]);
            resultMap.put("idCandidato", result[7]);
            resultMap.put("idSituacaoAluno", result[8]);
            resultMap.put("dataCadastro", result[9]);
            resultMap.put("aluno", result[10]);
            resultMap.put("emailInterno", result[11]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    public List<Map<String, Object>> listarAlunosTurmaProva(Long idTurma) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTAR_ALUNOS_TURMA_PROVAS(:pIdTurma)");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("pIdTurma", idTurma);

        List<Object[]> resultList = query.getResultList();
        Map<Long, Map<String, Object>> alunoMap = new LinkedHashMap<>();

        for (Object[] result : resultList) {
            Long idAluno = ((Number) result[0]).longValue();

            if (!alunoMap.containsKey(idAluno)) {
                Map<String, Object> alunoData = new LinkedHashMap<>();
                alunoData.put("idAluno", idAluno);
                alunoData.put("aluno", result[1]);
                alunoData.put("nomeCompleto", result[2]);
                alunoData.put("emailInterno", result[3]);
                alunoData.put("lstProva", new ArrayList<Map<String, Object>>());
                alunoMap.put(idAluno, alunoData);
            }

            Map<String, Object> prova = new LinkedHashMap<>();
            prova.put("idProva", result[4]);
            prova.put("nota", result[5]);
            prova.put("nomeAbreviado", result[6]);
            prova.put("descricao", result[7]);
            prova.put("conceitoMax", result[8]);
            prova.put("tipoConceito", result[9]);


            ((List<Map<String, Object>>) alunoMap.get(idAluno).get("lstProva")).add(prova);
        }

        return new ArrayList<>(alunoMap.values());
    }
    
    
    public List<Map<String, Object>> listarTurmaDisciplinaAluno(Long idAluno) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_TURMAS_MATRICULA_ALUNO(:pIdAluno)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdAluno", idAluno);
     

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idTurma", result[0]);
            resultMap.put("nomeTurma", result[1]);
            resultMap.put("idDisciplina", result[2]);
            resultMap.put("codDisciplina", result[3]);
            resultMap.put("nome", result[4]);
            resultMap.put("ano", result[5]);
            resultMap.put("periodo", result[6]);
            resultMap.put("tipoPeriocidade", result[7]);
            resultMap.put("aluasPrevistas", result[8]);
            resultMap.put("aulasDadas", result[9]);
            resultMap.put("idProfessor", result[10]);
            resultMap.put("nomeCompleto", result[11]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    public void prematriculaAluno(Long idAluno, Long idTipoMatricula, Long idUsuario) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_PREMATRICULA_ALUNO(:pIdAluno, :pIdTipoMatricula, :pIdUsuario)");

        Query query = entityManager.createNativeQuery(sql.toString());

      
        query.setParameter("pIdAluno", idAluno);
        query.setParameter("pIdTipoMatricula", idTipoMatricula);
        query.setParameter("pIdUsuario", idUsuario);

  
        query.executeUpdate();
    }

  
    public List<Map<String, Object>> filtrarAlunos(String matricula, String nome, String cpf, Long idEscola, Long idCurso) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_FILTRAR_ALUNOS(:pMatricula, :pNome, :pCpf, :pIdEscola, :pIdCurso)");

        Query query = entityManager.createNativeQuery(sql.toString());
      
        query.setParameter("pMatricula", matricula != null ? matricula : null);
        query.setParameter("pNome", nome != null ? nome : null);
        query.setParameter("pCpf", cpf != null ? cpf : null);
        query.setParameter("pIdEscola", idEscola != null ? idEscola : null);
        query.setParameter("pIdCurso", idCurso != null ? idCurso : null);
        
		if (matricula == null && nome == null && cpf == null && idEscola == null && idCurso == null) {
			throw new Exception("É necessário informar ao menos um parâmetro na requisição.");
		}

		if (nome != null && nome.trim().length() < 3) {
			throw new Exception("O nome deve ter ao menos 3 caracteres para o filtro.");
		}

		if (cpf != null) {
			String cpfLimpo = cpf.replaceAll("[^\\d]", "");
			if (cpfLimpo.length() < 5) {
				throw new Exception("O CPF deve ter ao menos 5 caracteres para o filtro.");
			}
			cpf = cpfLimpo;
		}

        
        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        
        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idAluno", result[0]);
            resultMap.put("nomeCompleto", result[1]);
            resultMap.put("cpf", result[2]);
            resultMap.put("idConta", result[3]);
            resultMap.put("idCurso", result[4]);
            resultMap.put("idEscola", result[5]);
            resultMap.put("idSerie", result[6]);
            resultMap.put("idTurno", result[7]);
            resultMap.put("idPessoa", result[8]);
            resultMap.put("idCandidato", result[9]);
            resultMap.put("idSituacaoAluno", result[10]);
            resultMap.put("dataCadastro", result[11]);
            resultMap.put("aluno", result[12]);
            resultMap.put("emailInterno", result[13]);
            resultMap.put("idCurriculo", result[14]);
            resultMap.put("nomeEscola", result[15]);
            resultMap.put("nomeCurso", result[16]);
            resultMap.put("serie", result[17]);
            resultMap.put("turno", result[18]);
            resultMap.put("situacaoAluno", result[19]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }

    public List<Map<String, Object>> listarDisciplinasDisponivesPrematrcula(Long idAluno, Long idSerie, Long idPeriodoLetivo, Long idEscola) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_DISCIPLINAS_DISPONIVEIS_PREMATRICULA(:pIdAluno, :pIdSerie, :pIdPeriodoLetivo, :pIdEscola)");
 
        Query query = entityManager.createNativeQuery(sql.toString());
 
        query.setParameter("pIdAluno", idAluno);
        query.setParameter("pIdSerie", idSerie);
        query.setParameter("pIdPeriodoLetivo", idPeriodoLetivo);
        query.setParameter("pIdEscola", idEscola);
     
 
        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();
 
        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idEscola", result[0]);
            resultMap.put("nomeEscola", result[1]);
            resultMap.put("idTurma", result[2]);
            resultMap.put("nomeTurma", result[3]);
            resultMap.put("idDisciplina", result[4]);
            resultMap.put("codDiscip", result[5]);
            resultMap.put("nomeDisciplina", result[6]);
            resultMap.put("idGradeCurricular", result[7]);
            resultMap.put("obs", result[8]);
            mappedResultList.add(resultMap);
        }
 
        return mappedResultList;
    }


    private List<Map<String, Object>> mapResults(List<Object[]> resultList) {
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idAuno", result[0]);
            resultMap.put("aluno", result[1]);
            resultMap.put("nomeCompleto", result[2]);
            resultMap.put("cpf", result[3]);
            resultMap.put("idEscola", result[4]);
            resultMap.put("nomeEscola", result[5]);
            resultMap.put("idCurso", result[6]);
            resultMap.put("nome", result[7]);
            resultMap.put("idSerie", result[8]);
            resultMap.put("serie", result[9]);
            resultMap.put("idTurno", result[10]);
            resultMap.put("turno", result[11]);
            resultMap.put("idSituacaoAluno", result[12]);
            resultMap.put("situacaoAluno", result[13]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }

    public String limparCaracteresEspeciais(String valor) {
        if (valor != null) {
            return valor.replaceAll("[^a-zA-Z0-9]", "").trim();
        }
        return null;
    }
   
}