package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.PessoaFichaMedicaDTO;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.PessoaFichaMedica;
import br.com.softsy.educacional.repository.PessoaFichaMedicaRepository;
import br.com.softsy.educacional.repository.PessoaRepository;

@Service
public class PessoaFichaMedicaService {

    @Autowired
    private PessoaFichaMedicaRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaRepository responsavelPessoaRepository;

    public List<PessoaFichaMedicaDTO> listarTudo() {
        List<PessoaFichaMedica> fichas = repository.findAll();
        return fichas.stream().map(PessoaFichaMedicaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaFichaMedicaDTO buscarPorId(Long id) {
        return new PessoaFichaMedicaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public PessoaFichaMedicaDTO salvar(PessoaFichaMedicaDTO dto) {
        PessoaFichaMedica ficha = criarFichaMedicaAPartirDTO(dto);
        ficha = repository.save(ficha);
        return new PessoaFichaMedicaDTO(ficha);
    }

    @Transactional
    public PessoaFichaMedicaDTO atualizar(PessoaFichaMedicaDTO dto) {
        PessoaFichaMedica ficha = repository.findById(dto.getIdPessoaFichaMedica())
                .orElseThrow(() -> new IllegalArgumentException("Ficha médica não encontrada"));
        atualizaDados(ficha, dto);
        repository.save(ficha);
        return new PessoaFichaMedicaDTO(ficha);
    }

    public PessoaFichaMedica criarFichaMedicaAPartirDTO(PessoaFichaMedicaDTO dto) {
        PessoaFichaMedica ficha = new PessoaFichaMedica();

        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        Pessoa responsavelPessoa = null;
        if (dto.getResponsavelPessoaId() != null) {
            responsavelPessoa = responsavelPessoaRepository.findById(dto.getResponsavelPessoaId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        }

        BeanUtils.copyProperties(dto, ficha, "idPessoaFichaMedica", "dtCadastro");

        ficha.setPessoa(pessoa);
        ficha.setResponsavelPessoa(responsavelPessoa);
        ficha.setDtCadastro(LocalDateTime.now());

        return ficha;
    }

    private void atualizaDados(PessoaFichaMedica ficha, PessoaFichaMedicaDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        Pessoa responsavelPessoa = null;
        if (dto.getResponsavelPessoaId() != null) {
            responsavelPessoa = responsavelPessoaRepository.findById(dto.getResponsavelPessoaId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        }

        BeanUtils.copyProperties(dto, ficha, "idPessoaFichaMedica", "dtCadastro");

        ficha.setPessoa(pessoa);
        ficha.setResponsavelPessoa(responsavelPessoa);
    }
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}