package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TratamentoLixoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TratamentoLixo;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TratamentoLixoRepository;

@Service
public class TratamentoLixoService {
	
	@Autowired
	private TratamentoLixoRepository repository;
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<TratamentoLixoDTO> buscarPorIdConta(Long id) {
		List<TratamentoLixo> tratamentoLixo = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tratamentoLixo por id de conta"));
		return tratamentoLixo.stream()
				.map(TratamentoLixoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public TratamentoLixoDTO buscarPorId(Long id){
		return new TratamentoLixoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TratamentoLixoDTO salvar(TratamentoLixoDTO dto) {

		TratamentoLixo tratamentoLixo = criarTratamentoLixoAPartirDTO(dto);
		
		tratamentoLixo = repository.save(tratamentoLixo);
		return new TratamentoLixoDTO(tratamentoLixo);
	}

	
	private TratamentoLixo criarTratamentoLixoAPartirDTO(TratamentoLixoDTO dto) {
		TratamentoLixo tratamentoLixo = new TratamentoLixo();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, tratamentoLixo, "idTratamentoLixo", "ativo", "dataCadastro");
		tratamentoLixo.setConta(conta);
		tratamentoLixo.setDataCadastro(LocalDateTime.now());
		tratamentoLixo.setAtivo('S');
		return tratamentoLixo;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idTratamentoLixo) {
		TratamentoLixo destinacao = repository.getReferenceById(idTratamentoLixo);
		destinacao.setAtivo(status);
	}
	
	@Transactional
	public TratamentoLixoDTO atualizar(TratamentoLixoDTO dto) {
		TratamentoLixo tratamentoLixo = repository.getReferenceById(dto.getIdTratamentoLixo());
		atualizaDados(tratamentoLixo, dto);
		return new TratamentoLixoDTO(tratamentoLixo);
	}
	
	private void atualizaDados(TratamentoLixo destino, TratamentoLixoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTratamentoLixo", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);;
		
	}

}
