package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AreaConhecimentoDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.repository.AreaConhecimentoRepository;
import br.com.softsy.educacional.repository.ContaRepository;

@Service
public class AreaConhecimentoService {

    @Autowired
    private AreaConhecimentoRepository repository;
    
	@Autowired 
	private ContaRepository contaRepository;

    public List<AreaConhecimentoDTO> listarTudo() {
        List<AreaConhecimento> areaConhecimentoList = repository.findAll();
        return areaConhecimentoList.stream()
                .map(AreaConhecimentoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AreaConhecimentoDTO buscarPorId(Long id) {
        return new AreaConhecimentoDTO(repository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<AreaConhecimentoDTO> buscarPorIdConta(Long idConta) {
        List<AreaConhecimento> areaConhecimento = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar área de conhecimento por ID da conta"));
        return areaConhecimento.stream()
                .map(AreaConhecimentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AreaConhecimentoDTO salvar(AreaConhecimentoDTO dto) {
        validarAreaConhecimento(dto.getAreaConhecimento());

        AreaConhecimento areaConhecimento = criarAreaConhecimentoAPartirDTO(dto);

        areaConhecimento = repository.save(areaConhecimento);
        return new AreaConhecimentoDTO(areaConhecimento);
    }

    @Transactional
    public AreaConhecimentoDTO atualizar(AreaConhecimentoDTO dto) {
        AreaConhecimento areaConhecimento = repository.getReferenceById(dto.getIdAreaConhecimento());
            atualizarDados(areaConhecimento, dto);
            return new AreaConhecimentoDTO(areaConhecimento);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private AreaConhecimento criarAreaConhecimentoAPartirDTO(AreaConhecimentoDTO dto) {
        AreaConhecimento areaConhecimento = new AreaConhecimento();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        areaConhecimento.setConta(conta);
        areaConhecimento.setAreaConhecimento(dto.getAreaConhecimento());
        areaConhecimento.setDataCadastro(LocalDateTime.now());
        return areaConhecimento;
    }

    private void validarAreaConhecimento(String areaConhecimento) {
        Optional<AreaConhecimento> areaConhecimentoExistente = repository.findByAreaConhecimento(areaConhecimento).stream().findFirst();
        if (areaConhecimentoExistente.isPresent()) {
            throw new UniqueException("Essa área de conhecimento já existe.");
        }
    }

    private void atualizarDados(AreaConhecimento destino, AreaConhecimentoDTO origem) {
        destino.setAreaConhecimento(origem.getAreaConhecimento());
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        destino.setConta(conta);
    }
}
