package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProfessorFormacaoDTO;
import br.com.softsy.educacional.dto.ProfessorFormacaoDTO;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.ProfessorFormacao;
import br.com.softsy.educacional.repository.ModalidadeEscolaRepository;
import br.com.softsy.educacional.repository.ProfessorFormacaoRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;

@Service
public class ProfessorFormacaoService {

    @Autowired
    private ProfessorFormacaoRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ModalidadeEscolaRepository modalidadeEscolaRepository;

    @Transactional(readOnly = true)
    public List<ProfessorFormacaoDTO> listarTudo() {
        List<ProfessorFormacao> professorFormacoes = repository.findAll();
        return professorFormacoes.stream()
                .map(ProfessorFormacaoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProfessorFormacaoDTO> buscarPorIdProfessor(Long id) {
        List<ProfessorFormacao> professorFormacoes = repository.findByProfessor_IdProfessor(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar formações dos professores por ID"));
        return professorFormacoes.stream()
                .map(ProfessorFormacaoDTO::new)
                .collect(Collectors.toList());
    }
    
	@Transactional
	public void ativaDesativa(char status, Long idProfessorFormacao) {
		ProfessorFormacao professorFormacao = repository.getReferenceById(idProfessorFormacao);
		professorFormacao.setAtivo(status);
	}
	

    @Transactional
    public ProfessorFormacaoDTO salvar(CadastroProfessorFormacaoDTO dto) {
        ProfessorFormacao professorFormacao = criarProfessorFormacaoAPartirDTO(dto);
        professorFormacao = repository.save(professorFormacao);
        return new ProfessorFormacaoDTO(professorFormacao);
    }

    @Transactional
    public ProfessorFormacaoDTO atualizar(CadastroProfessorFormacaoDTO dto) {
        ProfessorFormacao professorFormacao = repository.getReferenceById(dto.getIdProfessorFormacao());
        atualizaDados(professorFormacao, dto);
        return new ProfessorFormacaoDTO(professorFormacao);
    }


    private ProfessorFormacao criarProfessorFormacaoAPartirDTO(CadastroProfessorFormacaoDTO dto) {
        ProfessorFormacao professorFormacao = new ProfessorFormacao();
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        ModalidadeEscola modalidadeEscola = modalidadeEscolaRepository.findById(dto.getModalidadeEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Modalidade de escola não encontrada"));
        professorFormacao.setProfessor(professor);
        professorFormacao.setModalidadeEscola(modalidadeEscola);
        professorFormacao.setNomeCurso(dto.getNomeCurso());
        professorFormacao.setIes(dto.getIes());
        professorFormacao.setAnoConclusao(dto.getAnoConclusao());
        professorFormacao.setCertificado(dto.getCertificado());
        professorFormacao.setDataCadastro(LocalDateTime.now());
        professorFormacao.setAtivo('S');
        return professorFormacao;
    }

    private void atualizaDados(ProfessorFormacao destino, CadastroProfessorFormacaoDTO origem) {
        destino.setProfessor(professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado")));
        destino.setModalidadeEscola(modalidadeEscolaRepository.findById(origem.getModalidadeEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Modalidade de escola não encontrada")));
        destino.setNomeCurso(origem.getNomeCurso());
        destino.setIes(origem.getIes());
        destino.setAnoConclusao(origem.getAnoConclusao());
        destino.setCertificado(origem.getCertificado());
        destino.setDataCadastro(LocalDateTime.now());
    }
}