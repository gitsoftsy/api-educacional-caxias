package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.InstrPedagogicoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.InstrPedagogico;
import br.com.softsy.educacional.repository.InstrPedagogicoRepository;

@Service
public class InstrPedagogicoService {

    @Autowired
    private InstrPedagogicoRepository repository;

    public List<InstrPedagogicoDTO> listarTodos() {
        List<InstrPedagogico> instrucoes = repository.findAll();
        return instrucoes.stream()
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
        validarInstrucaoPedagogica(dto.getInstrPedagogico());

        InstrPedagogico instrucao = criarInstrucaoPedagogicaAPartirDTO(dto);

        instrucao = repository.save(instrucao);
        return new InstrPedagogicoDTO(instrucao);
    }

    private void validarInstrucaoPedagogica(String instrucao) {
        Optional<InstrPedagogico> instrucaoExistente = repository.findByInstrPedagogico(instrucao).stream().findFirst();
        if (instrucaoExistente.isPresent()) {
            throw new UniqueException("Essa instrução pedagógica já existe.");
        }
    }

    private InstrPedagogico criarInstrucaoPedagogicaAPartirDTO(InstrPedagogicoDTO dto) {
        InstrPedagogico instrucao = new InstrPedagogico();
        BeanUtils.copyProperties(dto, instrucao, "idInstrPedagogico", "ativo", "dataCadastro");
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
    }
}
