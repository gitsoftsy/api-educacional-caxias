package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.UtmDTO;
import br.com.softsy.educacional.model.CargoProfessor;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Utm;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.UtmRepository;

@Service
public class UtmService {

    @Autowired
    private UtmRepository utmRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<UtmDTO> listarPorConta(Long idConta) {
        List<Utm> utms = utmRepository.findByContaIdContaAndAtivo(idConta, 'S');
        return utms.stream().map(UtmDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UtmDTO buscarPorId(Long idUtm, Long idConta) {
        Utm utm = utmRepository.findById(idUtm)
                .orElseThrow(() -> new IllegalArgumentException("UTM não encontrada."));

        if (!utm.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A UTM informada não pertence à conta fornecida.");
        }

        return new UtmDTO(utm);
    }

    @Transactional
    public UtmDTO salvar(UtmDTO dto) {
        Utm utm = criarUtmAPartirDTO(dto);
        utm = utmRepository.save(utm);
        return new UtmDTO(utm);
    }

    @Transactional
    public UtmDTO atualizar(UtmDTO dto) {
        Utm utm = utmRepository.findById(dto.getIdUtm())
                .orElseThrow(() -> new IllegalArgumentException("UTM não encontrada."));
        atualizarDados(utm, dto);
        utm = utmRepository.save(utm);
        return new UtmDTO(utm);
    }

    private Utm criarUtmAPartirDTO(UtmDTO dto) {
        Utm utm = new Utm();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        utm.setConta(conta);
        utm.setUtmNome(dto.getUtmNome());
        utm.setDescricao(dto.getDescricao());
        utm.setDataCadastro(LocalDateTime.now());
        utm.setAtivo('S');

        return utm;
    }

    private void atualizarDados(Utm destino, UtmDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        destino.setConta(conta);
        destino.setUtmNome(origem.getUtmNome());
        destino.setDescricao(origem.getDescricao());
    }
    
    @Transactional
    public void ativaDesativa(char status, Long idUtm) {
    	Utm utm = utmRepository.getReferenceById(idUtm);
    	utm.setAtivo(status);
    }

}