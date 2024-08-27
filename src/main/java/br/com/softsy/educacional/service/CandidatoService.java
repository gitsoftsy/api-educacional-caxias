package br.com.softsy.educacional.service;


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

import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.CandidatoRelacionamento;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.MotivoReprovacaoCandidato;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.TipoIngresso;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.MotivoReprovacaoCandidatoRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.TipoIngressoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class CandidatoService {
	
    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private OfertaConcursoRepository ofertaConcursoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private OfertaConcursoRepository ofertaRepository;
    
    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MotivoReprovacaoCandidatoRepository motivoReprovacaoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Transactional(readOnly = true)
    public List<CandidatoDTO> listarTudo() {
        List<Candidato> concursos = candidatoRepository.findAll();
        return concursos.stream()
                .map(CandidatoDTO::new)
                .collect(Collectors.toList());
   }

    @Transactional(readOnly = true)
    public CandidatoDTO buscarPorId(Long id) {
        return new CandidatoDTO(candidatoRepository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<CandidatoDTO> buscarPorIdConta(Long idConta) {
        List<Candidato> curso = candidatoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar candidato por ID da conta"));
        return curso.stream()
                .map(CandidatoDTO::new)
                .collect(Collectors.toList());
    }
    
    public Map<String, Object> updateCandidatoOfertaConcurso(Long idCandidato, Long idOfertaConcurso) {
        Optional<Candidato> candidatoOptional = candidatoRepository.findById(idCandidato);
        if (candidatoOptional.isPresent()) {
            Candidato candidato = candidatoOptional.get();
            OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(idOfertaConcurso)
                                            .orElseThrow(() -> new RuntimeException("Oferta de concurso não encontrada"));
            candidato.setOfertaConcurso(ofertaConcurso);
            Candidato savedCandidato = candidatoRepository.save(candidato);

            CadastroCandidatoDTO updatedCandidatoDTO = new CadastroCandidatoDTO(savedCandidato);
            Map<String, Object> response = new HashMap<>();
            response.put("atualizado", true);
            response.put("candidato", updatedCandidatoDTO);
            return response;
        } else {
            throw new RuntimeException("Candidato não encontrado");
        }
    }

    @Transactional
    public CandidatoDTO salvar(CadastroCandidatoDTO dto) {
        Candidato candidato = criarCandidatoAPartirDTO(dto);
        candidato = candidatoRepository.save(candidato);
        return new CandidatoDTO(candidato);
    }

    @Transactional
    public CandidatoDTO atualizar(CadastroCandidatoDTO dto) {
    	Candidato candidato = candidatoRepository.findById(dto.getIdCandidato())
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
        atualizarDados(candidato, dto);
        candidato = candidatoRepository.save(candidato);
        return new CandidatoDTO(candidato);
    }


    public Candidato criarCandidatoAPartirDTO(CadastroCandidatoDTO dto) {
        Candidato candidato = new Candidato();

        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        OfertaConcurso ofertaConcurso = null;
        if (dto.getOfertaConcursoId() != null) {
        	ofertaConcurso = ofertaRepository.findById(dto.getOfertaConcursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Oferta concurso não encontrada"));
        }
        TipoIngresso tipoIngresso = null;
        if (dto.getTipoIngressoId() != null) {
        	tipoIngresso = tipoIngressoRepository.findById(dto.getTipoIngressoId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo ingresso não encontrado"));
        }
        
        Usuario usuarioAprovacao = null;
        if (dto.getUsuarioAprovacaoId() != null) {
        	usuarioAprovacao = usuarioRepository.findById(dto.getUsuarioAprovacaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário aprovação não encontrado"));
        }
        
        MotivoReprovacaoCandidato motivoReprovacao = null;
        if (dto.getMotivoReprovacaoCandidatoId() != null) {
        	motivoReprovacao = motivoReprovacaoRepository.findById(dto.getMotivoReprovacaoCandidatoId())
                    .orElseThrow(() -> new IllegalArgumentException("Motivo de reprovação não encontrado"));
        }

        candidato.setConta(conta);
        candidato.setPessoa(pessoa);
        candidato.setCandidato(dto.getCandidato());
        candidato.setOfertaConcurso(ofertaConcurso); 
        candidato.setTipoIngresso(tipoIngresso); 
        candidato.setClassificacao(dto.getClassificacao());
        candidato.setAluno(dto.getAluno());
        candidato.setAprovado(dto.getAprovado());
        candidato.setUsuarioAprovacao(usuarioAprovacao);
        candidato.setDescricaoReprovacao(dto.getDescricaoReprovacao());
        candidato.setMotivoReprovacaoCandidato(motivoReprovacao);

        return candidato;
    }
    private void atualizarDados(Candidato destino, CadastroCandidatoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idCandidato", "contaId", "pessoaId", "ofertaConcursoId", "tipoIngressoId", "classificacao", "aluno", "aprovado", "usuarioAprovadoId");

        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Pessoa pessoa = pessoaRepository.findById(origem.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        
        OfertaConcurso ofertaConcurso = null;
        if (origem.getOfertaConcursoId() != null) {
        	ofertaConcurso = ofertaRepository.findById(origem.getOfertaConcursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Oferta concurso não encontrada"));
        }
        TipoIngresso tipoIngresso = null;
        if (origem.getTipoIngressoId() != null) {
        	tipoIngresso = tipoIngressoRepository.findById(origem.getTipoIngressoId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo ingresso não encontrado"));
        }
        
        Usuario usuarioAprovacao = null;
        if (origem.getUsuarioAprovacaoId() != null) {
        	usuarioAprovacao = usuarioRepository.findById(origem.getUsuarioAprovacaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário aprovação não encontrado"));
        }
        
        MotivoReprovacaoCandidato motivoReprovacao = null;
        if (origem.getMotivoReprovacaoCandidatoId() != null) {
        	motivoReprovacao = motivoReprovacaoRepository.findById(origem.getMotivoReprovacaoCandidatoId())
                    .orElseThrow(() -> new IllegalArgumentException("Motivo de reprovação não encontrado"));
        }

        destino.setConta(conta);
        destino.setPessoa(pessoa);
        destino.setCandidato(origem.getCandidato());
        destino.setOfertaConcurso(ofertaConcurso);
        destino.setTipoIngresso(tipoIngresso);
        destino.setClassificacao(origem.getClassificacao());
        destino.setAluno(origem.getAluno());
        destino.setAprovado(origem.getAprovado());
        destino.setUsuarioAprovacao(usuarioAprovacao);
        destino.setDescricaoReprovacao(origem.getDescricaoReprovacao());
        destino.setMotivoReprovacaoCandidato(motivoReprovacao);
    }
    
    @Transactional
    public void aprovaReprova(char status, Long idCandidato) {
        Candidato candidato = candidatoRepository.getReferenceById(idCandidato);
        candidato.setAprovado(status);
    }
    
    @Transactional
    public void remover(Long idCandidato) {
    	candidatoRepository.deleteById(idCandidato);
    }
    

    public List<Map<String, Object>> obtemStepCandidato(Long idCandidato, String candidato, String rgNum, String cpfNum, String certNasc, String certCasamento) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_OBTEM_STEP_CANDIDATO(:pIdCandidato, :pCandidato, :pRgNum, :pCpfNum, :pCertNasc, :pCertCasamento)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdCandidato", idCandidato);
        query.setParameter("pCandidato", candidato);
        query.setParameter("pRgNum", rgNum);
        query.setParameter("pCpfNum", cpfNum);
        query.setParameter("pCertNasc", certNasc);
        query.setParameter("pCertCasamento", certCasamento);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idCandidato", result[0]);
            resultMap.put("candidato", result[1]);
            resultMap.put("step", result[2]);
            resultMap.put("temRelacionamento", result[3]);
            resultMap.put("temOfertaConcurso", result[4]);
            resultMap.put("enviouDocumentos", result[5]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    
    public List<Map<String, Object>> obtemStepCandidato(String candidato) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_DADOS_CANDIDATO_FINAL_RESERVA(:pCandidato)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pCandidato", candidato);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("candidato", result[0]);
            resultMap.put("turno", result[1]);
            resultMap.put("codCurso", result[2]);
            resultMap.put("nomeCurso", result[3]);
            resultMap.put("nomeEscola", result[4]);
            resultMap.put("tipoEscola", result[5]);
            resultMap.put("serie", result[6]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    
    public List<Map<String, Object>> obtemListaReservaDeVagas(Long idUsuario) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_RESERVA_DE_VAGAS(:pIdUsuario)");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Definir os parâmetros
        query.setParameter("pIdUsuario", idUsuario);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        // Mapear os resultados para um formato de mapa
        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idCandidato", result[0]);
            resultMap.put("candidato", result[1]);
            resultMap.put("nomeCompleto", result[2]);
            resultMap.put("idEscola", result[3]);
            resultMap.put("nomeEscola", result[4]);
            resultMap.put("idTurno", result[5]);
            resultMap.put("turno", result[6]);
            resultMap.put("serie", result[7]);
            resultMap.put("idTipoIngresso", result[8]);
            resultMap.put("tipoIngresso", result[9]);
            resultMap.put("aprovado", result[10]);
            resultMap.put("documentos", result[11]);
            resultMap.put("fichaMedica", result[12]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    public List<Map<String, Object>> listarReservaDeVagasExcel(Long idConta) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_RESERVA_DE_VAGAS_EXCEL(:pIdConta)");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("pIdConta", idConta);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("candidato", result[0]);
            resultMap.put("nomeCompleto", result[1]);
            resultMap.put("cpf", result[2]);
            resultMap.put("rgDataExpedicao", result[3]);
            resultMap.put("rgNum", result[4]);
            resultMap.put("rneOrgExpedidor", result[5]);
            resultMap.put("dtNascimento", result[6]);
            resultMap.put("sexo", result[7]);
            resultMap.put("nomePai", result[8]);
            resultMap.put("nomeMae", result[9]);
            resultMap.put("telefone", result[10]);
            resultMap.put("celular", result[11]);
            resultMap.put("nomeEscola", result[12]);
            resultMap.put("emailEscola", result[13]);
            resultMap.put("telefoneEscola", result[14]);
            resultMap.put("descricaoTelefone", result[15]);
            resultMap.put("turno", result[16]);
            resultMap.put("horaInicioTurno", result[17]);
            resultMap.put("horaFimTurno", result[18]);
            resultMap.put("serie", result[19]);
            resultMap.put("tipoIngresso", result[20]);
            resultMap.put("aprovado", result[21]);
            resultMap.put("documentos", result[22]);
            resultMap.put("fichaMedica", result[23]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    public List<Map<String, Object>> obtemListaReservaDeVagasPorDoc(Long idConta, Long idEscola, String rgNum, String cpfNum, String certNasc, String certCasamento) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_RESERVA_DE_VAGAS_FILTRO_DOC(:pIdConta, :pIdEscola, :pRgNum, :pCpfNum, :pCertNasc, :pCertCasamento)");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Definir os parâmetros
        query.setParameter("pIdConta", idConta);
        query.setParameter("pIdEscola", idEscola);
        query.setParameter("pRgNum", rgNum);
        query.setParameter("pCpfNum", cpfNum);
        query.setParameter("pCertNasc", certNasc);
        query.setParameter("pCertCasamento", certCasamento);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        // Mapear os resultados para um formato de mapa
        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idCandidato", result[0]);
            resultMap.put("candidato", result[1]);
            resultMap.put("nomeCompleto", result[2]);
            resultMap.put("idEscola", result[3]);
            resultMap.put("nomeEscola", result[4]);
            resultMap.put("idTurno", result[5]);
            resultMap.put("turno", result[6]);
            resultMap.put("serie", result[7]);
            resultMap.put("idTipoIngresso", result[8]);
            resultMap.put("tipoIngresso", result[9]);
            resultMap.put("aprovado", result[10]);
            resultMap.put("documentos", result[11]);
            resultMap.put("fichaMedica", result[12]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }

    
}
