package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CurriculoRepository;
import br.com.softsy.educacional.repository.CursoRepository;

@Service
public class CurriculoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private CurriculoRepository curriculoRepository;
	
	 @Transactional(readOnly = true)
	    public List<CurriculoDTO> listarCurriculosAtivosPorContaECurso(Long idConta, Long idCurso) {
	        if (idConta == null || idCurso == null) {
	            throw new IllegalArgumentException("O ID da conta e o ID do curso são obrigatórios.");
	        }

	        Character ativo = 'S';  // Filtrar somente os currículos ativos

	        List<Curriculo> curriculos = curriculoRepository.findByConta_IdContaAndCurso_IdCursoAndAtivo(idConta, idCurso, ativo)
	            .orElseThrow(() -> new IllegalArgumentException(
	                "Nenhum currículo ativo encontrado para a conta e curso informados."
	            ));

	        return curriculos.stream()
	                .map(CurriculoDTO::new)  // Converter para DTO
	                .collect(Collectors.toList());
	    }

	@Transactional(readOnly = true)
	public List<CurriculoDTO> listarTudo() {
		List<Curriculo> curriculos = curriculoRepository.findAll();
		return curriculos.stream().map(CurriculoDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CurriculoDTO buscarPorId(Long id) {
		Curriculo curriculo = curriculoRepository.getReferenceById(id);
		return new CurriculoDTO(curriculo);
	}

	@Transactional
	public CurriculoDTO salvar(CurriculoDTO dto) {
		Curriculo curriculo = criarCurriculoAPartirDTO(dto);
		curriculo = curriculoRepository.save(curriculo);
		return new CurriculoDTO(curriculo);
	}
	
    @Transactional(readOnly = true)
    public List<CurriculoDTO> buscarPorIdCurso(Long idCurso) {
        List<Curriculo> curriculo = curriculoRepository.findByCurso_IdCurso(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar curriculo por ID do curso"));
        return curriculo.stream()
                .map(CurriculoDTO::new)
                .collect(Collectors.toList());
    }

	@Transactional
	public CurriculoDTO atualizar(CurriculoDTO dto) {
		Curriculo curriculo = curriculoRepository.findById(dto.getIdCurriculo())
				.orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));
		atualizarDados(curriculo, dto);
		return new CurriculoDTO(curriculo);
	}

	@Transactional
	public void ativaDesativa(char status, Long idCurriculo) {
		Curriculo curriculo = curriculoRepository.getReferenceById(idCurriculo);
		curriculo.setAtivo(status);
	}

	private Curriculo criarCurriculoAPartirDTO(CurriculoDTO dto) {
		Curriculo curriculo = new Curriculo();
		Curso curso = cursoRepository.findById(dto.getCursoId())
				.orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
		
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrado"));
		
	    if (dto.getDtHomologacao() != null && dto.getDtExtincao() != null &&
	            dto.getDtHomologacao().isAfter(dto.getDtExtincao())) {
	        throw new IllegalArgumentException("A data de homologação não pode ser maior que a data de extinção.");
	    }

	    if (dto.getPrazoIdeal() != null && dto.getPrazoMax() != null &&
	            dto.getPrazoIdeal() > dto.getPrazoMax()) {
	        throw new IllegalArgumentException("O prazo ideal não pode ser maior que o prazo máximo.");
	    }
		
		curriculo.setCurso(curso);
		curriculo.setConta(conta);
		curriculo.setCurriculo(dto.getCurriculo());
		curriculo.setDescricao(dto.getDescricao());
		curriculo.setDtHomologacao(dto.getDtHomologacao());
		curriculo.setDtExtincao(dto.getDtExtincao());
		curriculo.setPrazoIdeal(dto.getPrazoIdeal());
		curriculo.setPrazoMax(dto.getPrazoMax());
		curriculo.setCreditos(dto.getCreditos());
		curriculo.setAulasPrevistas(dto.getAulasPrevistas());
		curriculo.setDataCadastro(LocalDateTime.now());
		curriculo.setAtivo('S');
		return curriculo;
	}

	private void atualizarDados(Curriculo destino, CurriculoDTO origem) {
		Curso curso = cursoRepository.findById(origem.getCursoId())
				.orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
		
		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrado"));
		destino.setCurso(curso);
		destino.setConta(conta);
		destino.setCurriculo(origem.getCurriculo());
		destino.setDescricao(origem.getDescricao());
		destino.setDtHomologacao(origem.getDtHomologacao());
		destino.setDtExtincao(origem.getDtExtincao());
		destino.setPrazoIdeal(origem.getPrazoIdeal());
		destino.setPrazoMax(origem.getPrazoMax());
		destino.setCreditos(origem.getCreditos());
		destino.setAulasPrevistas(origem.getAulasPrevistas());
		destino.setDataCadastro(origem.getDataCadastro());

	}
}