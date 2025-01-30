package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ConcursoDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.dto.SerieDTO;
import br.com.softsy.educacional.model.Concurso;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.Serie;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private SerieRepository serieRepository;
    
    @Transactional(readOnly = true)
	public List<SerieDTO> listarSeriesAtivasPorConta(Long idConta) {
	    if (idConta == null) {
	        throw new IllegalArgumentException("O ID da conta é obrigatório.");
	    }
	    Character ativo = 'S'; 
	    List<Serie> series = serieRepository.findActiveSerieByConta_IdContaAndAtivo(idConta, ativo)
	        .orElseThrow(() -> new IllegalArgumentException(
	            "Nenhuma serie ativa encontrada para a conta informada."
	        ));
	    if (series.isEmpty()) {
	        throw new IllegalArgumentException("Nenhuma serie ativa encontrada para a conta informada.");
	    }

	    return series.stream()
	            .map(SerieDTO::new)
	            .collect(Collectors.toList());
	}

    @Transactional(readOnly = true)
    public SerieDTO buscarPorId(Long id) {
        Serie serie = serieRepository.getReferenceById(id);
        return new SerieDTO(serie);
    }
    
    @Transactional(readOnly = true)
    public List<SerieDTO> buscarPorIdConta(Long idConta) {
        List<Serie> curso = serieRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar serie por ID da conta"));
        return curso.stream()
                .map(SerieDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SerieDTO salvar(SerieDTO dto) {
        Serie serie = criarSerieAPartirDTO(dto);
        serie = serieRepository.save(serie);
        return new SerieDTO(serie);
    }

    @Transactional
    public SerieDTO atualizar(SerieDTO dto) {
        Serie serie = serieRepository.findById(dto.getIdSerie())
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada"));
        atualizarDados(serie, dto);
        serie = serieRepository.save(serie);
        return new SerieDTO(serie);
    }

    @Transactional
    public void ativaDesativa(char status, Long idSerie) {
        Serie serie = serieRepository.getReferenceById(idSerie);
        serie.setAtivo(status);
    }

    private Serie criarSerieAPartirDTO(SerieDTO dto) {
        Serie serie = new Serie();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        serie.setConta(conta);
        serie.setSerie(dto.getSerie());
        serie.setDescricao(dto.getDescricao());
        serie.setDataCadastro(LocalDateTime.now());
        serie.setAtivo('S');
        return serie;
    }

    private void atualizarDados(Serie destino, SerieDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        BeanUtils.copyProperties(origem, destino, "dataCadastro", "ativo");
        destino.setConta(conta);
        destino.setSerie(origem.getSerie());
        destino.setDescricao(origem.getDescricao());
    }
}