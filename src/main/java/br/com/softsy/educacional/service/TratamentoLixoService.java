package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TratamentoLixoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.TratamentoLixo;
import br.com.softsy.educacional.repository.TratamentoLixoRepository;

@Service
public class TratamentoLixoService {
	
	@Autowired
	private TratamentoLixoRepository repository;
	
	public List<TratamentoLixo> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public TratamentoLixoDTO buscarPorId(Long id){
		return new TratamentoLixoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TratamentoLixoDTO salvar(TratamentoLixoDTO dto) {
		validarTratamentoLixo(dto.getTratamentoLixo());
		
		TratamentoLixo tratamentoLixo = criarTratamentoLixoAPartirDTO(dto);
		
		tratamentoLixo = repository.save(tratamentoLixo);
		return new TratamentoLixoDTO(tratamentoLixo);
	}
	
	private void validarTratamentoLixo(String tratamentoLixo) {
		Optional<TratamentoLixo> TratamentoLixoExistente = repository.findByTratamentoLixo(tratamentoLixo).stream().findFirst();
		if(TratamentoLixoExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private TratamentoLixo criarTratamentoLixoAPartirDTO(TratamentoLixoDTO dto) {
		TratamentoLixo tratamentoLixo = new TratamentoLixo();
		BeanUtils.copyProperties(dto, tratamentoLixo, "idTratamentoLixo", "ativo", "dataCadastro");
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
		
	}

}
