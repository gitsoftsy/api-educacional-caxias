package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaEsgotoDTO;
import br.com.softsy.educacional.dto.EscolaInfraestruturaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaEsgoto;
import br.com.softsy.educacional.model.EscolaInfraestrutura;
import br.com.softsy.educacional.repository.EscolaEsgotoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaEsgotoService {

    @Autowired
    private EscolaEsgotoRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional
    public EscolaEsgotoDTO salvar(EscolaEsgotoDTO dto) {
        EscolaEsgoto escolaEsgoto = criarEscolaEsgotoAPartirDTO(dto);
        escolaEsgoto = repository.save(escolaEsgoto);
        return new EscolaEsgotoDTO(escolaEsgoto);
    }
    
	@Transactional(readOnly = true)
	public List<EscolaEsgotoDTO> buscarPorIdEscola(Long id) {
		List<EscolaEsgoto> escolasDestinacaoLixo = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar esgoto por id de escola"));
		return escolasDestinacaoLixo.stream()
				.map(EscolaEsgotoDTO::new)
				.collect(Collectors.toList());
	}

    @Transactional
    public EscolaEsgotoDTO atualizar(EscolaEsgotoDTO dto) {
        EscolaEsgoto escolaEsgoto = repository.getReferenceById(dto.getIdEscolaEsgoto());
        atualizarDados(escolaEsgoto, dto);
        return new EscolaEsgotoDTO(escolaEsgoto);
    }

    private EscolaEsgoto criarEscolaEsgotoAPartirDTO(EscolaEsgotoDTO dto) {
        EscolaEsgoto escolaEsgoto = new EscolaEsgoto();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        escolaEsgoto.setEscola(escola);
        escolaEsgoto.setRedePublica(dto.getRedePublica());
        escolaEsgoto.setFossa(dto.getFossa());
        escolaEsgoto.setInexistente(dto.getInexistente());
        return escolaEsgoto;
    }
    
    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void atualizarDados(EscolaEsgoto destino, EscolaEsgotoDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setRedePublica(origem.getRedePublica());
        destino.setFossa(origem.getFossa());
        destino.setInexistente(origem.getInexistente());
    }
}