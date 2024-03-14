package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaModalidadeDTO;
import br.com.softsy.educacional.dto.EscolaModalidadeDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaModalidade;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.repository.EscolaModalidadeRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.ModalidadeEscolaRepository;

@Service
public class EscolaModalidadeService {

	
	@Autowired
	private EscolaModalidadeRepository repository;
	
	@Autowired
	private EscolaRepository escolaRepository;
	
	@Autowired
	private ModalidadeEscolaRepository modalidadeRepository;
	
	public List<EscolaModalidadeDTO> listarTudo(){
		List<EscolaModalidade> escolaModalidade = repository.findAll();
		
		return escolaModalidade.stream().map(EscolaModalidadeDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<EscolaModalidadeDTO>buscarPorIdEscola(Long id) {
		List<EscolaModalidade> escolaModalidade = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar modalidade por id de escola"));
		return escolaModalidade.stream()
				.map(EscolaModalidadeDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public EscolaModalidadeDTO salvar(CadastroEscolaModalidadeDTO dto) {
		
		EscolaModalidade escolaModalidade = criarEscolaModalidadeAPartirDTO(dto);
		
		escolaModalidade = repository.save(escolaModalidade);
		return new EscolaModalidadeDTO(escolaModalidade);
	}
	
	@Transactional
	public EscolaModalidadeDTO atualizar(CadastroEscolaModalidadeDTO dto) {
		EscolaModalidade escolaModalidade = repository.getReferenceById(dto.getIdEscolaModalidade());
		atualizaDados(escolaModalidade, dto);
		return new EscolaModalidadeDTO(escolaModalidade);
	}
	
	
	private EscolaModalidade criarEscolaModalidadeAPartirDTO(CadastroEscolaModalidadeDTO dto) {
		EscolaModalidade escolaModalidade = new EscolaModalidade();
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		ModalidadeEscola modalidadeEscola = modalidadeRepository.findById(dto.getModalidadeEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Modalidade não encontrada"));
		BeanUtils.copyProperties(dto, escolaModalidade, "escolaId", "modalidadeEscolaId", "idEscolaModalidade");
		escolaModalidade.setEscola(escola);
		escolaModalidade.setModalidadeEscola(modalidadeEscola);;
		return escolaModalidade;
	}
	
	private void atualizaDados(EscolaModalidade destino, CadastroEscolaModalidadeDTO origem) {
		BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro", "idEscolaModalidade");
	}
	
	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}
}
