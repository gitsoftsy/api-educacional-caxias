package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.MarcaEquipamentoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.MarcaEquipamento;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.MarcaEquipamentoRepository;

@Service
public class MarcaEquipamentoService {

    @Autowired
    private MarcaEquipamentoRepository repository;
    
	@Autowired 
	private ContaRepository contaRepository;

	@Transactional(readOnly = true)
	public List<MarcaEquipamentoDTO> buscarPorIdConta(Long id) {
		List<MarcaEquipamento> marcaEquipamento = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar marcaEquipamento por id de conta"));
		return marcaEquipamento.stream()
				.map(MarcaEquipamentoDTO::new)
				.collect(Collectors.toList());
	}

    @Transactional(readOnly = true)
    public MarcaEquipamentoDTO buscarPorId(Long id) {
        return new MarcaEquipamentoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public MarcaEquipamentoDTO salvar(MarcaEquipamentoDTO dto) {

        MarcaEquipamento marcaEquipamento = criarMarcaEquipamentoAPartirDTO(dto);

        marcaEquipamento = repository.save(marcaEquipamento);
        return new MarcaEquipamentoDTO(marcaEquipamento);
    }


    private MarcaEquipamento criarMarcaEquipamentoAPartirDTO(MarcaEquipamentoDTO dto) {
        MarcaEquipamento marcaEquipamento = new MarcaEquipamento();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        BeanUtils.copyProperties(dto, marcaEquipamento, "idMarcaEquipamento", "ativo", "dataCadastro");
        marcaEquipamento.setConta(conta);
        marcaEquipamento.setDataCadastro(LocalDateTime.now());
        marcaEquipamento.setAtivo('S');
        return marcaEquipamento;
    }

    @Transactional
    public void ativarDesativar(Character status, Long idMarcaEquipamento) {
        MarcaEquipamento marcaEquipamento = repository.getReferenceById(idMarcaEquipamento);
        marcaEquipamento.setAtivo(status);
    }

    @Transactional
    public MarcaEquipamentoDTO atualizar(MarcaEquipamentoDTO dto) {
        MarcaEquipamento marcaEquipamento = repository.getReferenceById(dto.getIdMarcaEquipamento());
        atualizarDados(marcaEquipamento, dto);
        return new MarcaEquipamentoDTO(marcaEquipamento);
    }

    private void atualizarDados(MarcaEquipamento destino, MarcaEquipamentoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idMarcaEquipamento", "ativo", "dataCadastro");
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    	destino.setConta(conta);
    }
}