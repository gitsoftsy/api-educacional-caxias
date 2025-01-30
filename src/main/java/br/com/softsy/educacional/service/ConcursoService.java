package br.com.softsy.educacional.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroConcursoDTO;
import br.com.softsy.educacional.dto.ConcursoDTO;
import br.com.softsy.educacional.dto.EditalDTO;
import br.com.softsy.educacional.model.Concurso;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.PeriodoLetivo;
import br.com.softsy.educacional.repository.ConcursoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.PeriodoLetivoRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class ConcursoService {

    @Autowired
    private ConcursoRepository concursoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PeriodoLetivoRepository periodoLetivoRepository;

    @Transactional(readOnly = true)
    public List<ConcursoDTO> listarTudo() {
        List<Concurso> concursos = concursoRepository.findAll();
        return concursos.stream()
                .map(ConcursoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConcursoDTO buscarPorId(Long id) {
        return new ConcursoDTO(concursoRepository.getReferenceById(id));
    }
    
    public List<EditalDTO> buscarEditaisAtivosPorConta(Long idConta) {
        Optional<List<Concurso>> concursosAtivosOptional = concursoRepository.findByConta_IdContaAndAtivo(idConta, 'S');
        
        if (concursosAtivosOptional.isEmpty() || concursosAtivosOptional.get().isEmpty()) {
            throw new IllegalArgumentException("Nenhum concurso ativo encontrado para o idConta fornecido.");
        }

        List<Concurso> concursosAtivos = concursosAtivosOptional.get();
        List<EditalDTO> editaisDTO = new ArrayList<>();

        for (Concurso concurso : concursosAtivos) {
            EditalDTO editalDTO = new EditalDTO(concurso.getIdConcurso(), concurso.getConcurso(), concurso.getPathEdital());
            editaisDTO.add(editalDTO);
        }

        return editaisDTO;
    }

    
    @Transactional(readOnly = true)
    public List<ConcursoDTO> buscarPorIdConta(Long idConta) {
        List<Concurso> curso = concursoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID da conta"));
        return curso.stream()
                .map(ConcursoDTO::new)
                .collect(Collectors.toList());
    }
    
    
    @Transactional(readOnly = true)
    public List<ConcursoDTO> buscarPorIdContaEAtivo(Long idConta) {
        List<Concurso> curso = concursoRepository.findByConta_IdContaAndAtivo(idConta, 'S')
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso ativo por ID da conta"));
        return curso.stream()
                .map(ConcursoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CadastroConcursoDTO salvar(CadastroConcursoDTO dto) throws IOException {
    	
    	String base64 = "";
        Concurso concurso = criarConcursoAPartirDTO(dto);
        
        base64 = concurso.getPathEdital();
        
        concurso.setPathEdital(null);
        concurso = concursoRepository.save(concurso);
        
		String caminhoIMG = ImageManager.salvaArquivoConcurso(base64, concurso.getIdConcurso(),"docConcurso" + dto.getIdConcurso());

		
		concurso.setPathEdital(caminhoIMG);
		dto.setPathEdital(caminhoIMG);
		dto.setIdConcurso(concurso.getIdConcurso());
        
		atualizarDados(concurso, dto);
		
		CadastroConcursoDTO concursoCriado = new CadastroConcursoDTO(concurso);

		return concursoCriado;
    }
    
    
    @Transactional
    public CadastroConcursoDTO atualizar(CadastroConcursoDTO dto) throws IOException {
        Concurso concurso = concursoRepository.findById(dto.getIdConcurso())
                .orElseThrow(() -> new IllegalArgumentException("Concurso não encontrado"));

        if (dto.getPathEdital() != null && !dto.getPathEdital().isEmpty()) {
            if (concurso.getPathEdital() != null) {
                File arquivoExistente = new File(concurso.getPathEdital());
                if (arquivoExistente.exists()) {
                    arquivoExistente.delete();
                }
            }

            String caminhoIMG = ImageManager.salvaArquivoConcurso(dto.getPathEdital(), concurso.getIdConcurso(), "docConcurso" + dto.getIdConcurso());
            concurso.setPathEdital(caminhoIMG);
            dto.setPathEdital(caminhoIMG);
        } else {
            concurso.setPathEdital(null);
        }

        atualizarDados(concurso, dto);

        concurso = concursoRepository.save(concurso);

        CadastroConcursoDTO concursoAtualizado = new CadastroConcursoDTO(concurso);
        return concursoAtualizado;
    }

    private Concurso criarConcursoAPartirDTO(CadastroConcursoDTO dto) {
        Concurso concurso = new Concurso();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(dto.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));
        

        concurso.setConta(conta);
        concurso.setConcurso(dto.getConcurso());
        concurso.setPeriodoLetivo(periodoLetivo);
        concurso.setDataAbertura(dto.getDataAbertura());
        concurso.setDataFechamento(dto.getDataFechamento());
        concurso.setDataCadastro(LocalDateTime.now());
        concurso.setAtivo('S');
        concurso.setPathEdital(dto.getPathEdital());
        return concurso;
    }


    @Transactional
    public void ativaDesativa(char status, Long idConcurso) {
        Concurso concurso = concursoRepository.getReferenceById(idConcurso);
        concurso.setAtivo(status);
        concursoRepository.save(concurso);
    }

    private void atualizarDados(Concurso destino, CadastroConcursoDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        PeriodoLetivo periodoLetivo = periodoLetivoRepository.findById(origem.getPeriodoLetivoId())
                .orElseThrow(() -> new IllegalArgumentException("Período letivo não encontrado"));

        destino.setConta(conta);
        destino.setConcurso(origem.getConcurso());
        destino.setPeriodoLetivo(periodoLetivo);
        destino.setDataAbertura(origem.getDataAbertura());
        destino.setDataFechamento(origem.getDataFechamento());
        destino.setPathEdital(origem.getPathEdital());
    }
}