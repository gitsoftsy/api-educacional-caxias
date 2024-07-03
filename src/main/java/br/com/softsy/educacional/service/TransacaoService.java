package br.com.softsy.educacional.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.DisciplinaDTO;
import br.com.softsy.educacional.dto.TransacaoDTO;
import br.com.softsy.educacional.model.Disciplina;
import br.com.softsy.educacional.model.Modulo;
import br.com.softsy.educacional.model.Transacao;
import br.com.softsy.educacional.repository.ModuloRepository;
import br.com.softsy.educacional.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;
    
    @Autowired
    private ModuloRepository moduloRepository;
    
	@Transactional(readOnly = true)
	public List<TransacaoDTO> listarTudo() {
		List<Transacao> disciplinas = repository.findAll();
		return disciplinas.stream().map(TransacaoDTO::new).collect(Collectors.toList());
	}

    @Transactional(readOnly = true)
    public List<TransacaoDTO> buscarPorIdModulo(Long idModulo) {
        List<Transacao> transacoes = repository.findByModulo_IdModulo(idModulo)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar transações por ID do módulo"));
        return transacoes.stream()
                .map(TransacaoDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> listarAcessosUsuarios(Long idUsuario) {
        List<Object[]> resultList = repository.listaAcessosUsuarios(idUsuario);
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idModulo", result[0]);
            resultMap.put("modulo", result[1]);
            resultMap.put("iconeModulo", result[2]);
            resultMap.put("idTransacao", result[3]);
            resultMap.put("nome", result[4]);
            resultMap.put("url", result[5]);
            resultMap.put("idCodHtml", result[6]);
            resultMap.put("acessa", result[7]);
            resultMap.put("altera", result[8]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }

    @Transactional(readOnly = true)
    public TransacaoDTO buscarPorId(Long id) {
        return new TransacaoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public TransacaoDTO salvar(TransacaoDTO dto) {
        Transacao transacao = criarTransacaoAPartirDTO(dto);
        transacao = repository.save(transacao);
        return new TransacaoDTO(transacao);
    }

    private Transacao criarTransacaoAPartirDTO(TransacaoDTO dto) {
        Transacao transacao = new Transacao();
        Modulo modulo = moduloRepository.findById(dto.getModuloId())
        		.orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar transações por modulo"));
        BeanUtils.copyProperties(dto, transacao, "idTransacao");
        transacao.setModulo(modulo);
        return transacao;
    }

    @Transactional
    public TransacaoDTO atualizar(TransacaoDTO dto) {
        Transacao transacao = repository.getReferenceById(dto.getIdTransacao());
        atualizarDados(transacao, dto);
        return new TransacaoDTO(transacao);
    }

    private void atualizarDados(Transacao destino, TransacaoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idTransacao");
        Modulo modulo = moduloRepository.findById(origem.getModuloId())
        		.orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar transações por modulo"));
        destino.setModulo(modulo);
    }
    
	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}
}