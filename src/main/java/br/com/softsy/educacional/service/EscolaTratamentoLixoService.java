package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaTelefoneDTO;
import br.com.softsy.educacional.dto.CadastroEscolaTratamentoLixoDTO;
import br.com.softsy.educacional.dto.EscolaTelefoneDTO;
import br.com.softsy.educacional.dto.EscolaTratamentoLixoDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaTelefone;
import br.com.softsy.educacional.model.EscolaTratamentoLixo;
import br.com.softsy.educacional.model.TratamentoLixo;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.EscolaTratamentoLixoRepository;
import br.com.softsy.educacional.repository.TratamentoLixoRepository;

@Service
public class EscolaTratamentoLixoService {

	@Autowired
	private EscolaTratamentoLixoRepository repository;
	@Autowired
	private EscolaRepository escolaRepository;
	@Autowired
	private TratamentoLixoRepository tratamentoRepository;
	
	public List<EscolaTratamentoLixoDTO> listarTudo(){
		List<EscolaTratamentoLixo> escolaTratamento = repository.findAll();
		
		return escolaTratamento.stream().map(EscolaTratamentoLixoDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<EscolaTratamentoLixoDTO>buscarPorIdEscola(Long id) {
		List<EscolaTratamentoLixo> escolaTratamento = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tratamento por id de escola"));
		return escolaTratamento.stream()
				.map(EscolaTratamentoLixoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public EscolaTratamentoLixoDTO salvar(CadastroEscolaTratamentoLixoDTO dto) {
		
		EscolaTratamentoLixo escolaTratamento = criarEscolaTratamentoAPartirDTO(dto);
		
		escolaTratamento = repository.save(escolaTratamento);
		return new EscolaTratamentoLixoDTO(escolaTratamento);
	}
	
	@Transactional
	public EscolaTratamentoLixoDTO atualizar(CadastroEscolaTratamentoLixoDTO dto) {
		EscolaTratamentoLixo escolaTratamento = repository.getReferenceById(dto.getIdEscolaTratamentoLixo());
		atualizaDados(escolaTratamento, dto);
		return new EscolaTratamentoLixoDTO(escolaTratamento);
	}
	
	private EscolaTratamentoLixo criarEscolaTratamentoAPartirDTO(CadastroEscolaTratamentoLixoDTO dto) {
		EscolaTratamentoLixo escolaTratamento = new EscolaTratamentoLixo();
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		TratamentoLixo tratamentoLixo = tratamentoRepository.findById(dto.getTratamentoLixoId())
				.orElseThrow(() -> new IllegalArgumentException("Tratamento não encontrado"));
		BeanUtils.copyProperties(dto, escolaTratamento, "escolaId", "tratamentoLixoId", "idEscolaTratamentoLixo");
		escolaTratamento.setEscola(escola);
		escolaTratamento.setTratamentoLixo(tratamentoLixo);
		return escolaTratamento;
	}
	
	private void atualizaDados(EscolaTratamentoLixo destino, CadastroEscolaTratamentoLixoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro", "idEscolaTratamentoLixo");
	}
	
	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}
}
