package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.CadastroEscolaTelefoneDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.dto.EscolaTelefoneDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaTelefone;
import br.com.softsy.educacional.model.TipoTelefone;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.EscolaTelefoneRepository;
import br.com.softsy.educacional.repository.TipoTelefoneRepository;

@Service
public class EscolaTelefoneService {

	@Autowired
	private EscolaTelefoneRepository repository;
	
	@Autowired
	private TipoTelefoneRepository tipoTelefoneRepository;
	
	@Autowired
	private EscolaRepository escolaRepository;
	

	public List<EscolaTelefoneDTO> listarTudo(){
		List<EscolaTelefone> escolaTelefone = repository.findAll();
		
		return escolaTelefone.stream().map(EscolaTelefoneDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public EscolaTelefoneDTO buscarPorId(Long id) {
		return new EscolaTelefoneDTO(repository.getReferenceById(id));
		
	}
	
	@Transactional(readOnly = true)
	public List<EscolaTelefoneDTO>buscarPorIdEscola(Long id) {
		List<EscolaTelefone> escolaTelefone = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar telefone por id de escola"));
		return escolaTelefone.stream()
				.map(EscolaTelefoneDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public CadastroEscolaTelefoneDTO salvar(CadastroEscolaTelefoneDTO dto) {
		
		EscolaTelefone escolaTelefone = criarEscolaTelefoneAPartirDTO(dto);
		
		escolaTelefone = repository.save(escolaTelefone);
		return new CadastroEscolaTelefoneDTO(escolaTelefone);
	}
	
	@Transactional
	public EscolaTelefoneDTO atualizar(CadastroEscolaTelefoneDTO dto) {
		EscolaTelefone escolaTelefone = repository.getReferenceById(dto.getIdTelefoneEscola());
		atualizaDados(escolaTelefone, dto);
		return new EscolaTelefoneDTO(escolaTelefone);
	}
	
	
	private EscolaTelefone criarEscolaTelefoneAPartirDTO(CadastroEscolaTelefoneDTO dto) {
		EscolaTelefone escolaTelefone = new EscolaTelefone();
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		TipoTelefone tipoTelefone = tipoTelefoneRepository.findById(dto.getTipoTelefoneId())
				.orElseThrow(() -> new IllegalArgumentException("Tipo não encontrado"));
		BeanUtils.copyProperties(dto, escolaTelefone, "idTelefoneEscola", "tipoTelefoneId", "escolaId");
		escolaTelefone.setEscola(escola);
		escolaTelefone.setTipoTelefone(tipoTelefone);
		return escolaTelefone;
	}
	
	private void atualizaDados(EscolaTelefone destino, CadastroEscolaTelefoneDTO origem) {
		
		BeanUtils.copyProperties(origem, destino, "idTelefoneEscola", "tipoTelefoneId", "escolaId");
		destino.setEscola(escolaRepository.findById(origem.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
		destino.setTipoTelefone(tipoTelefoneRepository.findById(origem.getTipoTelefoneId())
				.orElseThrow(() -> new IllegalArgumentException("Tipo telefone não encontrado")));
	}
	
	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}
}
