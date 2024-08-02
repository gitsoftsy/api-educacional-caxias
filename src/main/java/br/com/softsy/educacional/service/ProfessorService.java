package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProfessorDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.dto.ProfessorDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.NivelEscolaridade;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.SituacaoProfessor;
import br.com.softsy.educacional.model.TipoEnsinoMedio;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.NivelEscolaridadeRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.SituacaoProfessorRepository;
import br.com.softsy.educacional.repository.TipoEnsinoMedioRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContaRepository contaRepository;
    
	@Autowired
	private PasswordEncrypt encrypt;

    public List<ProfessorDTO> listarTudo() {
        List<Professor> professores = professorRepository.findAll();

        return professores.stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProfessorDTO buscarPorId(Long id) {
        return new ProfessorDTO(professorRepository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<ProfessorDTO> buscarPorIdConta(Long idConta) {
        List<Professor> curso = professorRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar professor por ID da conta"));
        return curso.stream()
                .map(ProfessorDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CadastroProfessorDTO salvar(CadastroProfessorDTO dto) {

        Professor professor = criarProfessorAPartirDTO(dto);

        professor = professorRepository.save(professor);
        professor.setSenha(encrypt.hashPassword(dto.getSenha()));
        return new CadastroProfessorDTO(professor);
    }

    @Transactional
    public ProfessorDTO atualizar(CadastroProfessorDTO dto) {
        Professor professor = professorRepository.getReferenceById(dto.getIdProfessor());
        atualizaDados(professor, dto);
        return new ProfessorDTO(professor);
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        Professor professor = professorRepository.getReferenceById(id);
        professor.setAtivo(status);
    }

    private Professor criarProfessorAPartirDTO(CadastroProfessorDTO dto) {
        Professor professor = new Professor();
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        BeanUtils.copyProperties(dto, professor, "idProfessor", "pessoaId", "contaId",
                "dataCadastro", "ativo");

        professor.setPessoa(pessoa);
        professor.setConta(conta);
        professor.setAtivo('S');
        professor.setDataCadastro(LocalDateTime.now());

        return professor;
    }


    private void atualizaDados(Professor destino, CadastroProfessorDTO origem) {
    	
        Pessoa pessoa = pessoaRepository.findById(origem.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    	
        BeanUtils.copyProperties(origem, destino, "idProfessor", "pessoaId", "contaId", "ativo", "senha", "dataCadastro");
		if(origem.getSenha() != null) {
			destino.setSenha(encrypt.hashPassword(origem.getSenha()));
		}
        destino.setConta(conta);
        destino.setPessoa(pessoa);
    }
}
