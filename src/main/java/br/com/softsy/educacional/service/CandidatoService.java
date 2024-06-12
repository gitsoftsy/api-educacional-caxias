package br.com.softsy.educacional.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.ContaRepository;


public class CandidatoService {
	
    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private ContaRepository contaRepository;

//   
//    @Transactional(readOnly = true)
//    public List<CandidatoDTO> listarTudo() {
//        List<Candidato> concursos = candidatoRepository.findAll();
//        return concursos.stream()
//                .map(CandidatoDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public CandidatoDTO buscarPorId(Long id) {
//        return new CandidatoDTO(candidatoRepository.getReferenceById(id));
//    }
//    
//    @Transactional(readOnly = true)
//    public List<CandidatoDTO> buscarPorIdConta(Long idConta) {
//        List<Candidato> curso = candidatoRepository.findByConta_IdConta(idConta)
//                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID da conta"));
//        return curso.stream()
//                .map(CandidatoDTO::new)
//                .collect(Collectors.toList());
//    }
//
    @Transactional
    public CandidatoDTO salvar(CadastroCandidatoDTO dto) {

    	Candidato candidato = criarCandidatoAPartirDTO(dto);
    	candidato = candidatoRepository.save(candidato);
        return new CandidatoDTO(candidato);
    }

//    @Transactional
//    public CandidatoDTO atualizar(CadastroConcursoDTO dto) {
//    	Candidato candidato = candidatoRepository.findById(dto.getIdConcurso())
//                .orElseThrow(() -> new IllegalArgumentException("Concurso não encontrado"));
//        atualizarDados(candidato, dto);
//        candidato = candidatoRepository.save(candidato);
//        return new CandidatoDTO(candidato);
//    }

    private Candidato criarCandidatoAPartirDTO(CadastroCandidatoDTO dto) {
        Candidato candidato = new Candidato();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
      
        

        candidato.setConta(conta);
        candidato.setCandidato(dto.getCandidato());
     
        candidato.setClassificacao(dto.getClassificacao());
        candidato.setAprovado(dto.getAprovado());
        return candidato;
    }


//    @Transactional
//    public void ativaDesativa(char status, Long idConcurso) {
//    	Candidato candidato = candidatoRepository.getReferenceById(idConcurso);
//    	candidato.setAtivo(status);
//        candidatoRepository.save(candidato);
//    }
//
//    private void atualizarDados(Candidato destino, CadastroCandidatoDTO origem) {
//        Conta conta = contaRepository.findById(origem.getContaId())
//                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
//     
//
//        destino.setConta(conta);
//        destino.setConcurso(origem.getConcurso());
//        destino.setDataAbertura(origem.getDataAbertura());
//        destino.setDataFechamento(origem.getDataFechamento());
//    }
}
