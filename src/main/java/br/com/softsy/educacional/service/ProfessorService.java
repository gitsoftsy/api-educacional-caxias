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
import br.com.softsy.educacional.dto.ProfessorDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.NivelEscolaridade;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.SituacaoProfessor;
import br.com.softsy.educacional.model.TipoEnsinoMedio;
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
    private SituacaoProfessorRepository situacaoProfessorRepository;

    @Autowired
    private NivelEscolaridadeRepository nivelEscolaridadeRepository;

    @Autowired
    private TipoEnsinoMedioRepository tipoEnsinoMedioRepository;

    public List<ProfessorDTO> listarTudo() {
        List<Professor> professores = professorRepository.findAll();

        return professores.stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProfessorDTO buscarPorId(Long id) {
        return new ProfessorDTO(professorRepository.getReferenceById(id));
    }

    @Transactional
    public CadastroProfessorDTO salvar(CadastroProfessorDTO dto) {
    	validarMatricula(dto.getMatricula());
        validarCodigoInep(dto.getCodigoInep());

        Professor professor = criarProfessorAPartirDTO(dto);

        professor = professorRepository.save(professor);
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
        SituacaoProfessor situacaoProfessor = situacaoProfessorRepository.findById(dto.getSituacaoProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Situação do professor não encontrada"));
        NivelEscolaridade nivelEscolaridade = nivelEscolaridadeRepository.findById(dto.getNivelEscolaridadeId())
                .orElseThrow(() -> new IllegalArgumentException("Nível de escolaridade não encontrado"));
        TipoEnsinoMedio tipoEnsinoMedio = tipoEnsinoMedioRepository.findById(dto.getTipoEnsinoMedioId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de ensino médio não encontrado"));

        BeanUtils.copyProperties(dto, professor, "idProfessor", "pessoaId", "situacaoProfessorId", "nivelEscolaridadeId",
                "tipoEnsinoMedioId");

        professor.setPessoa(pessoa);
        professor.setSituacaoProfessor(situacaoProfessor);
        professor.setNivelEscolaridade(nivelEscolaridade);
        professor.setTipoEnsinoMedio(tipoEnsinoMedio);
        professor.setAtivo('S');
        professor.setDataCadastro(LocalDateTime.now());

        return professor;
    }

    private void validarCodigoInep(String codigoInep) {
        Optional<Professor> professorExistente = professorRepository.findByCodigoInep(codigoInep).stream().findFirst();
        if (professorExistente.isPresent()) {
            throw new UniqueException("Já existe um professor com o código Inep fornecido.");
        }
    }
    
    private void validarMatricula(String matricula) {
        Optional<Professor> matriculaExistente = professorRepository.findByMatricula(matricula).stream().findFirst();
        if (matriculaExistente.isPresent()) {
            throw new UniqueException("Já existe um professor com a matrícula fornecida.");
        }
    }

    private void atualizaDados(Professor destino, CadastroProfessorDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idProfessor", "pessoaId", "situacaoProfessorId", "nivelEscolaridadeId",
                "tipoEnsinoMedioId");
    }
}
