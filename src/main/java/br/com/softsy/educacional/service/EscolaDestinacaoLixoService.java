package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softsy.educacional.dto.CadastroEscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.dto.EscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.repository.DestinacaoLixoRepository;
import br.com.softsy.educacional.repository.EscolaDestinacaoLixoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaDestinacaoLixoService {

	@Autowired 
	private EscolaDestinacaoLixoRepository repository;
	
	@Autowired
	private EscolaRepository escolaRepository;
	
	@Autowired
	private DestinacaoLixoRepository destinacaoLixoRepository;
	
	@Transactional(readOnly = true)
	public List<EscolaDestinacaoLixoDTO> listarTudo() {
		List<EscolaDestinacaoLixo> escolasDestinacaoLixo = repository.findAll();
		return escolasDestinacaoLixo.stream()
				.map(EscolaDestinacaoLixoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<EscolaDestinacaoLixoDTO> buscarPorIdEscola(Long id) {
		List<EscolaDestinacaoLixo> escolasDestinacaoLixo = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinacao de lixo por id de escola"));
		return escolasDestinacaoLixo.stream()
				.map(EscolaDestinacaoLixoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public EscolaDestinacaoLixoDTO salvar(CadastroEscolaDestinacaoLixoDTO dto) {
		EscolaDestinacaoLixo escolaDestinacaoLixo = criarEscolaDestinacaoLixoAPartirDTO(dto);
		escolaDestinacaoLixo = repository.save(escolaDestinacaoLixo);
		return new EscolaDestinacaoLixoDTO(escolaDestinacaoLixo);
	}
	
	@Transactional
	public EscolaDestinacaoLixoDTO atualizar(CadastroEscolaDestinacaoLixoDTO dto) {
		EscolaDestinacaoLixo escolaDestinacaoLixo = repository.getReferenceById(dto.getIdEscolaDestinacaoLixo());
		atualizaDados(escolaDestinacaoLixo, dto);
		return new EscolaDestinacaoLixoDTO(escolaDestinacaoLixo);
	}
	
	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	private EscolaDestinacaoLixo criarEscolaDestinacaoLixoAPartirDTO(CadastroEscolaDestinacaoLixoDTO dto) {
		EscolaDestinacaoLixo escolaDestinacaoLixo = new EscolaDestinacaoLixo();
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		DestinacaoLixo destinacaoLixo = destinacaoLixoRepository.findById(dto.getDestinacaoLixoId())
				.orElseThrow(() -> new IllegalArgumentException("Destinação de lixo não encontrada"));
		escolaDestinacaoLixo.setEscola(escola);
		escolaDestinacaoLixo.setDestinacaoLixo(destinacaoLixo);
		return escolaDestinacaoLixo;
	}
	
	private void atualizaDados(EscolaDestinacaoLixo destino, CadastroEscolaDestinacaoLixoDTO origem) {
		destino.setEscola(escolaRepository.findById(origem.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
		destino.setDestinacaoLixo(destinacaoLixoRepository.findById(origem.getDestinacaoLixoId())
				.orElseThrow(() -> new IllegalArgumentException("Destinação de lixo não encontrada")));
	}
}

