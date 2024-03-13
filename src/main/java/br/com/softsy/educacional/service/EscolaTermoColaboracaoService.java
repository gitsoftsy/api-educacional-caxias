package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaTermoColaboracaoDTO;
import br.com.softsy.educacional.dto.EscolaTermoColaboracaoDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaTermoColaboracao;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.EscolaTermoColaboracaoRepository;

@Service
public class EscolaTermoColaboracaoService {
	
	@Autowired 
	private EscolaTermoColaboracaoRepository repository;

	@Autowired 
	private EscolaRepository escolaRepository;
	
	
	public List<EscolaTermoColaboracaoDTO> listarTudo(){
		List<EscolaTermoColaboracao> escolaTermoColaboracao = repository.findAll();
		
		return escolaTermoColaboracao.stream().map(EscolaTermoColaboracaoDTO::new).collect(Collectors.toList());
	}

	
	@Transactional(readOnly = true)
	public EscolaTermoColaboracaoDTO buscarPorId(Long id) {
		return new EscolaTermoColaboracaoDTO(repository.getReferenceById(id));
	}
	
	
	@Transactional
	public CadastroEscolaTermoColaboracaoDTO salvar(CadastroEscolaTermoColaboracaoDTO dto) {
		
		EscolaTermoColaboracao escolaTermoColaboracao = criarEscolaTermoColaboracaoAPartirDTO(dto);
		
		escolaTermoColaboracao = repository.save(escolaTermoColaboracao);
		return new CadastroEscolaTermoColaboracaoDTO(escolaTermoColaboracao);
	}
	
	@Transactional
	public EscolaTermoColaboracaoDTO atualizar(CadastroEscolaTermoColaboracaoDTO dto) {
		EscolaTermoColaboracao escolaTermoColaboracao = repository.getReferenceById(dto.getIdEscolaTermoColaboracao());
		atualizaDados(escolaTermoColaboracao, dto);
		return new EscolaTermoColaboracaoDTO(escolaTermoColaboracao);
	}
	
	private EscolaTermoColaboracao criarEscolaTermoColaboracaoAPartirDTO(CadastroEscolaTermoColaboracaoDTO dto) {
		EscolaTermoColaboracao escolaTermoColaboracao = new EscolaTermoColaboracao();
		Escola escola = escolaRepository.findById(dto.getEscolaId()).orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		escolaTermoColaboracao.setEscola(escola);
		escolaTermoColaboracao.setDataCadastro(LocalDateTime.now());
		escolaTermoColaboracao.setDataValidade(LocalDateTime.now());
		return escolaTermoColaboracao;
		
	}
	
	private void atualizaDados(EscolaTermoColaboracao destino, CadastroEscolaTermoColaboracaoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idEscolaTermoColaboracao","dataCadastro","dataCadastro","dataValidade", "coordenador", "termoColaboracao", "idEscola");
	}
	
	@Transactional(readOnly = true)
	public List<EscolaTermoColaboracaoDTO>buscarPorIdEscola(Long id) {
		List<EscolaTermoColaboracao> escolaTratamento = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar o Termo por id de escola"));
		return escolaTratamento.stream()
				.map(EscolaTermoColaboracaoDTO::new)
				.collect(Collectors.toList());
	}

}
