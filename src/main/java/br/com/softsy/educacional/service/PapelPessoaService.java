package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.PapelPessoaDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.PapelPessoa;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.PapelPessoaRepository;

@Service
public class PapelPessoaService {

    @Autowired
    private PapelPessoaRepository papelPessoaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<PapelPessoaDTO> listarTudo() {
        List<PapelPessoa> papeisPessoa = papelPessoaRepository.findAll();
        return papeisPessoa.stream()
                .map(PapelPessoaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PapelPessoaDTO> buscarPorIdConta(Long idConta) {
        List<PapelPessoa> papeisPessoa = papelPessoaRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar papel pessoa por ID da conta"));
        return papeisPessoa.stream()
                .map(PapelPessoaDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PapelPessoaDTO buscarPorId(Long id) {
        PapelPessoa papelPessoa = papelPessoaRepository.getReferenceById(id);
        return new PapelPessoaDTO(papelPessoa);
    }

    @Transactional
    public PapelPessoaDTO salvar(PapelPessoaDTO dto) {
        PapelPessoa papelPessoa = criarPapelPessoaAPartirDTO(dto);
        papelPessoa = papelPessoaRepository.save(papelPessoa);
        return new PapelPessoaDTO(papelPessoa);
    }

    @Transactional
    public PapelPessoaDTO atualizar(PapelPessoaDTO dto) {
        PapelPessoa papelPessoa = papelPessoaRepository.findById(dto.getIdPapelPessoa())
                .orElseThrow(() -> new IllegalArgumentException("Papel pessoa não encontrado"));
        atualizarDados(papelPessoa, dto);
        return new PapelPessoaDTO(papelPessoa);
    }

    private PapelPessoa criarPapelPessoaAPartirDTO(PapelPessoaDTO dto) {
        PapelPessoa papelPessoa = new PapelPessoa();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        papelPessoa.setAtivo('S');
        papelPessoa.setDataCadastro(LocalDateTime.now());
        papelPessoa.setConta(conta);
        papelPessoa.setPapelPessoa(dto.getPapelPessoa());
        return papelPessoa;
    }

	@Transactional
	public void ativaDesativa(char status, Long id) {
		PapelPessoa papelPessoa = papelPessoaRepository.getReferenceById(id);
		papelPessoa.setAtivo(status);
	}

    private void atualizarDados(PapelPessoa destino, PapelPessoaDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setConta(conta);
        destino.setPapelPessoa(origem.getPapelPessoa());
    }
}