package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.com.softsy.educacional.dto.CadastroNotaDTO;
import br.com.softsy.educacional.dto.NotaDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Nota;
import br.com.softsy.educacional.model.Prova;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.NotaRepository;
import br.com.softsy.educacional.repository.ProvaRepository;
import br.com.softsy.educacional.infra.exception.ContaNaoVinculadaException;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProvaRepository provaRepository;
    
    @Autowired
    private NotaLogService notaLogService;
    
    @Autowired
    private EntityManager entityManager;
    

    @Transactional(readOnly = true)
    public List<NotaDTO> listarTudo() {
        List<Nota> notas = repository.findAll();
        return notas.stream()
                .map(NotaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotaDTO buscarPorId(Long id) {
        Nota nota = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));
        return new NotaDTO(nota);
    }
    
    @Transactional(readOnly = true)
    public List<NotaDTO> buscarPorIdAluno(Long idConta) {
        List<Nota> notas = repository.findByAluno_IdAluno(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar nota por ID do aluno"));
        return notas.stream()
                .map(NotaDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public NotaDTO salvar(CadastroNotaDTO dto) throws IOException {
        Nota nota = criarNotaAPartirDTO(dto);
        nota = repository.save(nota);

        return new NotaDTO(nota);
    }

    private Nota criarNotaAPartirDTO(CadastroNotaDTO dto) {
        Nota nota = new Nota();

        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Prova prova = provaRepository.findById(dto.getProvaId())
                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

        BeanUtils.copyProperties(dto, nota, "idNota", "dataCadastro");

        nota.setAluno(aluno);
        nota.setProva(prova);
        nota.setDataCadastro(LocalDateTime.now());
        return nota;
    }

    @Transactional
    public NotaDTO atualizar(CadastroNotaDTO dto) throws IOException {
        Nota nota = repository.findById(dto.getIdNota())
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));

        atualizaDados(nota, dto);

        nota = repository.save(nota);

        return new NotaDTO(nota);
    }
    

    private void atualizaDados(Nota destino, CadastroNotaDTO origem) {
        Aluno aluno = alunoRepository.findById(origem.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Prova prova = provaRepository.findById(origem.getProvaId())
                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

        BeanUtils.copyProperties(origem, destino, "idNota", "dataCadastro");

        destino.setAluno(aluno);
        destino.setProva(prova);
    }
    
    public List<Map<String, Object>> listarNotasAluno(Long idAluno, Long idConta) throws Exception {
    	
    	  //Validar se existe o vinculo de aluno com conta, se não existir exibir mensagem de erro
    	  StringBuilder sql = new StringBuilder();
    	  sql.append("CALL PROC_VALIDA_ALUNO_CONTA(:pIdAluno, :pIdConta)");

    	  Query query = entityManager.createNativeQuery(sql.toString());

    	  query.setParameter("pIdAluno", idAluno);
    	  query.setParameter("pIdConta", idConta);

//    	  if (query.getResultList().isEmpty()) {
//    	   throw new ContaNaoVinculadaException("Aluno não vinculado a conta");
//    	  }
    	
    	
    	StringBuilder sql1 = new StringBuilder();
        sql1.append("CALL PROC_LISTA_NOTAS_ALUNO(:pIdAluno)");

        Query query1 = entityManager.createNativeQuery(sql1.toString());
        query1.setParameter("pIdAluno", idAluno);
     

        List<Object[]> resultList = query1.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idMatricula", result[0]);
            resultMap.put("idTurma", result[1]);
            resultMap.put("nomeTurma", result[2]);
            resultMap.put("idDisciplina", result[3]);
            resultMap.put("nome", result[4]);
            resultMap.put("docDiscip", result[5]);
            resultMap.put("idProva", result[6]);
            resultMap.put("nomeAbreviadao", result[7]);
            resultMap.put("descricao", result[8]);
            resultMap.put("dataDivulgacao", result[9]);
            resultMap.put("dataAgendaProva", result[10]);
            resultMap.put("ordem", result[11]);
            resultMap.put("tipoConceito", result[12]);
            resultMap.put("conceitoMax", result[13]);
            resultMap.put("dataLimiteRevisao", result[14]);
            resultMap.put("ehSimulado", result[15]);
            resultMap.put("formula", result[16]);
            resultMap.put("idNota", result[17]);
            resultMap.put("dataCadastro", result[18]);
            resultMap.put("nota", result[19]);
            resultMap.put("compareceu", result[20]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    
    
    
    
    
 
}