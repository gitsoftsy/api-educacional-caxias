package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProfessorDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.dto.ProfessorDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.NivelEscolaridade;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.SituacaoProfessor;
import br.com.softsy.educacional.model.TipoEnsinoMedio;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.NivelEscolaridadeRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.SituacaoProfessorRepository;
import br.com.softsy.educacional.repository.TipoEnsinoMedioRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
	@Autowired
	private PasswordEncrypt encrypt;

    public List<ProfessorDTO> listarTudo() {
        List<Professor> professores = professorRepository.findAll();

        return professores.stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProfessorDTO buscarPorId(Long id) {
        return new ProfessorDTO(professorRepository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<ProfessorDTO> buscarPorUsuario(String usuario) {
    	List<Professor> professor = professorRepository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar professor por usuário"));
        return professor.stream()
                .map(ProfessorDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProfessorDTO> buscarPorMatricula(String matricula) {
    	List<Professor> professor = professorRepository.findByMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar professor por matricula"));
        return professor.stream()
                .map(ProfessorDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProfessorDTO> buscarPorIdConta(Long idConta) {
        List<Professor> professor = professorRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar professor por ID da conta"));
        return professor.stream()
                .map(ProfessorDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CadastroProfessorDTO salvar(CadastroProfessorDTO dto) {

        Professor professor = criarProfessorAPartirDTO(dto);

        professor = professorRepository.save(professor);
        professor.setSenha(encrypt.hashPassword(dto.getSenha()));
        return new CadastroProfessorDTO(professor);
    }

    @Transactional
    public ProfessorDTO atualizar(CadastroProfessorDTO dto) {
        Professor professor = professorRepository.getReferenceById(dto.getIdProfessor());
        atualizaDados(professor, dto);
        return new ProfessorDTO(professor);
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        Professor professor = professorRepository.getReferenceById(id);
        professor.setAtivo(status);
    }

    public Professor criarProfessorAPartirDTO(CadastroProfessorDTO dto) {
        Professor professor = new Professor();
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        BeanUtils.copyProperties(dto, professor, "idProfessor", "pessoaId", "contaId",
                "dataCadastro", "ativo");

        professor.setPessoa(pessoa);
        professor.setConta(conta);
        professor.setAtivo('S');
        professor.setDataCadastro(LocalDateTime.now());

        return professor;
    }
    
    public List<Map<String, Object>> listaDisciplinasProfessor(Long idProfessor) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_DISCIPLINAS_PROFESSOR(:pIdProfessor)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdProfessor", idProfessor);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idDisciplina", result[0]);
            resultMap.put("dataCadastro", result[1]);
            resultMap.put("idConta", result[2]);
            resultMap.put("idAreaConhecimento", result[3]);
            resultMap.put("codDiscip", result[4]);
            resultMap.put("nome", result[5]);
            resultMap.put("creditos", result[6]);
            resultMap.put("horasAula", result[7]);
            resultMap.put("horasLab", result[8]);
            resultMap.put("horasEstagio", result[9]);
            resultMap.put("horasAtiv", result[10]);
            resultMap.put("horasAno", result[11]);
            resultMap.put("horasSemanal", result[12]);
            resultMap.put("ativo", result[13]);
            resultMap.put("idProfessorDisciplina", result[14]);
            resultMap.put("ativoProfessorDisciplina", result[15]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }

    
    public List<Map<String, Object>> listaEscolasProfessor(Long idProfessor) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_ESCOLAS_PROFESSOR(:pIdProfessor)");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Definir os parâmetros
        query.setParameter("pIdProfessor", idProfessor);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        // Mapear os resultados para um formato de mapa
        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idEscola", result[0]);
            resultMap.put("idConta", result[1]);
            resultMap.put("dataCadastro", result[2]);
            resultMap.put("ativo", result[3]);
            resultMap.put("nomeEscola", result[4]);
            resultMap.put("logoEscola", result[5]);
            resultMap.put("tipoEscola", result[6]);
            resultMap.put("idCategoriaEscolaPrivada", result[7]);
            resultMap.put("cnpj", result[8]);
            resultMap.put("codigoInep", result[9]);
            resultMap.put("cep", result[10]);
            resultMap.put("endereco", result[11]);
            resultMap.put("numero", result[12]);
            resultMap.put("complemento", result[13]);
            resultMap.put("bairro", result[14]);
            resultMap.put("municipio", result[15]);
            resultMap.put("distrito", result[16]);
            resultMap.put("uf", result[17]);
            resultMap.put("numCme", result[18]);
            resultMap.put("numParecerCme", result[19]);
            resultMap.put("latitude", result[20]);
            resultMap.put("longitude", result[21]);
            resultMap.put("idLocalizacao", result[22]);
            resultMap.put("idEntidadeSuperior", result[23]);
            resultMap.put("email", result[24]);
            resultMap.put("idSituacaoFuncionamento", result[25]);
            resultMap.put("educacaoIndigena", result[26]);
            resultMap.put("exameSelecao", result[27]);
            resultMap.put("compartilhaEspaco", result[28]);
            resultMap.put("usaEspacoEntornoEscolar", result[29]);
            resultMap.put("pppAtualizado12Meses", result[30]);
            resultMap.put("idFormaOcupacaoPredio", result[31]);
            resultMap.put("idZoneamento", result[32]);
            resultMap.put("idOrgaoPublico", result[33]);
            resultMap.put("idDependenciaAdm", result[34]);
            resultMap.put("idProfessorEscola", result[35]);
            resultMap.put("ativoProfessorEscola", result[36]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    public List<Map<String, Object>> filtrarProfessor(String cpf, String nome, String matricula) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_FILTRAR_PROFESSOR(:pCpf, :pNome, :pMatricula)");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Definir os parâmetros
        query.setParameter("pCpf", cpf);
        query.setParameter("pNome", nome);
        query.setParameter("pMatricula", matricula);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        // Mapear os resultados para um formato de mapa
        for (Object[] result : resultList) {
        	Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("matricula", result[0]);
            resultMap.put("codigoInep", result[1]);
            resultMap.put("idProfessor", result[2]);
            resultMap.put("usuario", result[3]);
            resultMap.put("ativo", result[4]);
            resultMap.put("emailInstitucional", result[5]);
            resultMap.put("nomeCompleto", result[6]);
            resultMap.put("cpf", result[7]);
            resultMap.put("email", result[8]);
            resultMap.put("celular", result[9]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    
    public List<Map<String, Object>> filtrarProfessorPorEscolaEDisciplina(Long idEscola, Long idDisciplina) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_FILTRAR_PROFESSOR_ESCOLA_DISCIPLINA(:pIdEscola, :pIdDisciplina)");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Definir os parâmetros
        query.setParameter("pIdEscola", idEscola);
        query.setParameter("pIdDisciplina", idDisciplina);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        // Mapear os resultados para um formato de mapa
        for (Object[] result : resultList) {
        	Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("matricula", result[0]);
            resultMap.put("codigoInep", result[1]);
            resultMap.put("idProfessor", result[2]);
            resultMap.put("usuario", result[3]);
            resultMap.put("ativo", result[4]);
            resultMap.put("emailInstitucional", result[5]);
            resultMap.put("nomeCompleto", result[6]);
            resultMap.put("cpf", result[7]);
            resultMap.put("email", result[8]);
            resultMap.put("celular", result[9]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
     

    private void atualizaDados(Professor destino, CadastroProfessorDTO origem) {
    	
        Pessoa pessoa = pessoaRepository.findById(origem.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    	
        BeanUtils.copyProperties(origem, destino, "idProfessor", "pessoaId", "contaId", "ativo", "senha", "dataCadastro");
		if(origem.getSenha() != null) {
			destino.setSenha(encrypt.hashPassword(origem.getSenha()));
		}
        destino.setConta(conta);
        destino.setPessoa(pessoa);
    }
}
