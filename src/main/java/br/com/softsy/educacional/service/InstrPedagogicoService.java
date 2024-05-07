package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.InstrPedagogicoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.InstrPedagogico;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.InstrPedagogicoRepository;

@Service
public class InstrPedagogicoService {

    @Autowired
    private InstrPedagogicoRepository repository;
    
	@Autowired 
	private ContaRepository contaRepository;

	@Transactional(readOnly = true)
	public List<InstrPedagogicoDTO> buscarPorIdConta(Long id) {
		List<InstrPedagogico> instrPedagogico = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar instrPedagogico por id de conta"));
		return instrPedagogico.stream()
				.map(InstrPedagogicoDTO::new)
				.collect(Collectors.toList());
	}

    @Transactional(readOnly = true)
    public InstrPedagogicoDTO buscarPorId(Long id) {
        InstrPedagogico instrucao = repository.getReferenceById(id);
        return new InstrPedagogicoDTO(instrucao);
    }

    @Transactional
    public InstrPedagogicoDTO salvar(InstrPedagogicoDTO dto) {

        InstrPedagogico instrucao = criarInstrucaoPedagogicaAPartirDTO(dto);

        instrucao = repository.save(instrucao);
        return new InstrPedagogicoDTO(instrucao);
    }


    private InstrPedagogico criarInstrucaoPedagogicaAPartirDTO(InstrPedagogicoDTO dto) {
        InstrPedagogico instrucao = new InstrPedagogico();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        BeanUtils.copyProperties(dto, instrucao, "idInstrPedagogico", "ativo", "dataCadastro");
        instrucao.setConta(conta);
        instrucao.setDataCadastro(LocalDateTime.now());
        instrucao.setAtivo('S');
        return instrucao;
    }

    @Transactional
    public void ativarDesativarInstrucaoPedagogica(char status, Long idInstrPedagogico) {
        InstrPedagogico instrucao = repository.getReferenceById(idInstrPedagogico);
        instrucao.setAtivo(status);
    }

    @Transactional
    public InstrPedagogicoDTO atualizar(InstrPedagogicoDTO dto) {
        InstrPedagogico instrucao = repository.getReferenceById(dto.getIdInstrPedagogico());
        atualizarDados(instrucao, dto);
        return new InstrPedagogicoDTO(instrucao);
    }

    private void atualizarDados(InstrPedagogico destino, InstrPedagogicoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idInstrPedagogico", "ativo", "dataCadastro");
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setConta(conta);
    }
}
