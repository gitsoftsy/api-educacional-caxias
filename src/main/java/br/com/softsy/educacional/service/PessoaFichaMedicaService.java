package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.LinguaEnsinoDTO;
import br.com.softsy.educacional.dto.PessoaFichaMedicaDTO;
import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.PessoaFichaMedica;
import br.com.softsy.educacional.repository.PessoaFichaMedicaRepository;
import br.com.softsy.educacional.repository.PessoaRepository;

@Service
public class PessoaFichaMedicaService {

    @Autowired
    private PessoaFichaMedicaRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaRepository responsavelPessoaRepository;
    
    @Autowired
    private EntityManager entityManager;

    public List<PessoaFichaMedicaDTO> listarTudo() {
        List<PessoaFichaMedica> fichas = repository.findAll();
        return fichas.stream().map(PessoaFichaMedicaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaFichaMedicaDTO buscarPorId(Long id) {
        return new PessoaFichaMedicaDTO(repository.getReferenceById(id));
    }
    
	@Transactional(readOnly = true)
	public List<PessoaFichaMedicaDTO> buscarPorIdPessoa(Long id) {
		List<PessoaFichaMedica> fichas = repository.findByPessoa_IdPessoa(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar ficha médica por id de pessoa"));
		return fichas.stream()
				.map(PessoaFichaMedicaDTO::new)
				.collect(Collectors.toList());
	}
    

    @Transactional
    public PessoaFichaMedicaDTO salvar(PessoaFichaMedicaDTO dto) {
        PessoaFichaMedica ficha = criarFichaMedicaAPartirDTO(dto);
        ficha = repository.save(ficha);
        return new PessoaFichaMedicaDTO(ficha);
    }

    @Transactional
    public PessoaFichaMedicaDTO atualizar(PessoaFichaMedicaDTO dto) {
        PessoaFichaMedica ficha = repository.findById(dto.getIdPessoaFichaMedica())
                .orElseThrow(() -> new IllegalArgumentException("Ficha médica não encontrada"));
        atualizaDados(ficha, dto);
        repository.save(ficha);
        return new PessoaFichaMedicaDTO(ficha);
    }

    public PessoaFichaMedica criarFichaMedicaAPartirDTO(PessoaFichaMedicaDTO dto) {
        PessoaFichaMedica ficha = new PessoaFichaMedica();

        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        Pessoa responsavelPessoa = null;
        if (dto.getResponsavelPessoaId() != null) {
            responsavelPessoa = responsavelPessoaRepository.findById(dto.getResponsavelPessoaId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        }

        BeanUtils.copyProperties(dto, ficha, "idPessoaFichaMedica", "dtCadastro");

        ficha.setPessoa(pessoa);
        ficha.setResponsavelPessoa(responsavelPessoa);
        ficha.setDtCadastro(LocalDateTime.now());

        return ficha;
    }

    private void atualizaDados(PessoaFichaMedica ficha, PessoaFichaMedicaDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        Pessoa responsavelPessoa = null;
        if (dto.getResponsavelPessoaId() != null) {
            responsavelPessoa = responsavelPessoaRepository.findById(dto.getResponsavelPessoaId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        }

        BeanUtils.copyProperties(dto, ficha, "idPessoaFichaMedica", "dtCadastro");

        ficha.setPessoa(pessoa);
        ficha.setResponsavelPessoa(responsavelPessoa);
    }
    
    public List<Map<String, Object>> listaFichaMedicaResponsavel(Long idResponsavelPessoa) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_FICHA_MEDICA_RESPONSAVEL(:pIdResponsavelPessoa)");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Definir os parâmetros
        query.setParameter("pIdResponsavelPessoa", idResponsavelPessoa);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        // Mapear os resultados para um formato de mapa
        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idPessoaFichaMedica", result[0]);
            resultMap.put("idPessoa", result[1]);
            resultMap.put("dataCadastro", result[2]);
            resultMap.put("idResponsavelPessoa", result[3]);
            resultMap.put("peso", result[4]);
            resultMap.put("altura", result[5]);
            resultMap.put("tipoSanguineo", result[6]);
            resultMap.put("aceitaTransfusao", result[7]);
            resultMap.put("numeroCartSus", result[8]);
            resultMap.put("planoSaude", result[9]);
            resultMap.put("numeroCarteirinha", result[10]);
            resultMap.put("psEmergenciaCep", result[11]);
            resultMap.put("psEmergenciaEndereco", result[12]);
            resultMap.put("psEmergenciaNumero", result[13]);
            resultMap.put("psEmergenciaComplemento", result[14]);
            resultMap.put("psEmergenciaBairro", result[15]);
            resultMap.put("psEmergenciaMunicipio", result[16]);
            resultMap.put("psEmergenciaDistrito", result[17]);
            resultMap.put("psEmergenciaUf", result[18]);
            resultMap.put("psEmergenciaTelefone", result[19]);
            resultMap.put("alergia", result[20]);
            resultMap.put("descricaoAlergia", result[21]);
            resultMap.put("tratamentoMedico", result[22]);
            resultMap.put("descricaoTratamentoMedico", result[23]);
            resultMap.put("comorbidades", result[24]);
            resultMap.put("descricaoComorbidades", result[25]);
            resultMap.put("outrasDoencas", result[26]);
            resultMap.put("nomeCompleto", result[27]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}