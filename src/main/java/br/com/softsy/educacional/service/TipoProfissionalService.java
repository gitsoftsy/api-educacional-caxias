package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoProfissionalDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TipoProfissional;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TipoProfissionalRepository;

@Service
public class TipoProfissionalService {

    @Autowired
    private TipoProfissionalRepository repository;
    
	@Autowired 
	private ContaRepository contaRepository;

	@Transactional(readOnly = true)
	public List<TipoProfissionalDTO> buscarPorIdConta(Long id) {
		List<TipoProfissional> zoenamento = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipoProfissional por id de conta"));
		return zoenamento.stream()
				.map(TipoProfissionalDTO::new)
				.collect(Collectors.toList());
	}

    @Transactional(readOnly = true)
    public TipoProfissionalDTO buscarPorId(Long id) {
        TipoProfissional tipoProfissional = repository.getReferenceById(id);
        return new TipoProfissionalDTO(tipoProfissional);
    }

    @Transactional
    public TipoProfissionalDTO salvar(TipoProfissionalDTO dto) {

        TipoProfissional tipoProfissional = criarTipoProfissionalAPartirDTO(dto);

        tipoProfissional = repository.save(tipoProfissional);
        return new TipoProfissionalDTO(tipoProfissional);
    }


    private TipoProfissional criarTipoProfissionalAPartirDTO(TipoProfissionalDTO dto) {
        TipoProfissional tipoProfissional = new TipoProfissional();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        BeanUtils.copyProperties(dto, tipoProfissional, "idTipoProfissional", "ativo", "dataCadastro");
        tipoProfissional.setConta(conta);
        tipoProfissional.setDataCadastro(LocalDateTime.now());
        tipoProfissional.setAtivo('S');
        return tipoProfissional;
    }

    @Transactional
    public void ativarDesativarTipoProfissional(char status, Long idTipoProfissional) {
        TipoProfissional tipoProfissional = repository.getReferenceById(idTipoProfissional);
        tipoProfissional.setAtivo(status);
    }

    @Transactional
    public TipoProfissionalDTO atualizar(TipoProfissionalDTO dto) {
        TipoProfissional tipoProfissional = repository.getReferenceById(dto.getIdTipoProfissional());
        atualizarDados(tipoProfissional, dto);
        return new TipoProfissionalDTO(tipoProfissional);
    }

    private void atualizarDados(TipoProfissional destino, TipoProfissionalDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idTipoProfissional", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setConta(conta);
    }
}
