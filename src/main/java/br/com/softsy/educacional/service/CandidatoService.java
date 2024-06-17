package br.com.softsy.educacional.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.TipoIngresso;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.TipoIngressoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class CandidatoService {
	
    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private OfertaConcursoRepository ofertaConcursoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private OfertaConcursoRepository ofertaRepository;
    
    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional(readOnly = true)
    public List<CandidatoDTO> listarTudo() {
        List<Candidato> concursos = candidatoRepository.findAll();
        return concursos.stream()
                .map(CandidatoDTO::new)
                .collect(Collectors.toList());
   }

    @Transactional(readOnly = true)
    public CandidatoDTO buscarPorId(Long id) {
        return new CandidatoDTO(candidatoRepository.getReferenceById(id));
    }
    
    @Transactional(readOnly = true)
    public List<CandidatoDTO> buscarPorIdConta(Long idConta) {
        List<Candidato> curso = candidatoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID da conta"));
        return curso.stream()
                .map(CandidatoDTO::new)
                .collect(Collectors.toList());
    }
    
    public Map<String, Object> updateCandidatoOfertaConcurso(Long idCandidato, Long idOfertaConcurso) {
        Optional<Candidato> candidatoOptional = candidatoRepository.findById(idCandidato);
        if (candidatoOptional.isPresent()) {
            Candidato candidato = candidatoOptional.get();
            OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(idOfertaConcurso)
                                            .orElseThrow(() -> new RuntimeException("Oferta de concurso não encontrada"));
            candidato.setOfertaConcurso(ofertaConcurso);
            Candidato savedCandidato = candidatoRepository.save(candidato);

            CadastroCandidatoDTO updatedCandidatoDTO = new CadastroCandidatoDTO(savedCandidato);
            Map<String, Object> response = new HashMap<>();
            response.put("atualizado", true);
            response.put("candidato", updatedCandidatoDTO);
            return response;
        } else {
            throw new RuntimeException("Candidato não encontrado");
        }
    }

    @Transactional
    public CandidatoDTO salvar(CadastroCandidatoDTO dto) {
        Candidato candidato = criarCandidatoAPartirDTO(dto);
        candidato = candidatoRepository.save(candidato);
        return new CandidatoDTO(candidato);
    }

    @Transactional
    public CandidatoDTO atualizar(CadastroCandidatoDTO dto) {
    	Candidato candidato = candidatoRepository.findById(dto.getIdCandidato())
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
        atualizarDados(candidato, dto);
        candidato = candidatoRepository.save(candidato);
        return new CandidatoDTO(candidato);
    }


    public Candidato criarCandidatoAPartirDTO(CadastroCandidatoDTO dto) {
        Candidato candidato = new Candidato();

      
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        OfertaConcurso ofertaConcurso = ofertaRepository.findById(dto.getOfertaConcursoId())
                .orElseThrow(() -> new IllegalArgumentException("Oferta não encontrada")); 
        TipoIngresso tipoIngresso = tipoIngressoRepository.findById(dto.getTipoIngressoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Ingresso não encontrado"));
        Usuario usuarioAprovacao = usuarioRepository.findById(dto.getUsuarioAprovacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")); 

 
        candidato.setConta(conta);
        candidato.setPessoa(pessoa);
        candidato.setCandidato(dto.getCandidato());
        candidato.setOfertaConcurso(ofertaConcurso);
        candidato.setTipoIngresso(tipoIngresso);
        candidato.setClassificacao(dto.getClassificacao());
        candidato.setAluno(dto.getAluno());
        candidato.setAprovado(dto.getAprovado());
        candidato.setUsuarioAprovacao(usuarioAprovacao);

        return candidato;
    }

    private void atualizarDados(Candidato destino, CadastroCandidatoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idCandidato", "contaId", "pessoaId", "ofertaConcursoId", "tipoIngressoId", "classificacao", "aluno", "aprovado", "usuarioAprovadoId");

        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Pessoa pessoa = pessoaRepository.findById(origem.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        OfertaConcurso ofertaConcurso = ofertaRepository.findById(origem.getOfertaConcursoId())
                .orElse(null);
        TipoIngresso tipoIngresso = tipoIngressoRepository.findById(origem.getTipoIngressoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Ingresso não encontrado"));
        Usuario usuarioAprovacao = usuarioRepository.findById(origem.getUsuarioAprovacaoId())
                .orElse(null); 

        destino.setConta(conta);
        destino.setPessoa(pessoa);
        destino.setCandidato(origem.getCandidato());
        destino.setOfertaConcurso(ofertaConcurso);
        destino.setTipoIngresso(tipoIngresso);
        destino.setClassificacao(origem.getClassificacao());
        destino.setAluno(origem.getAluno());
        destino.setAprovado(origem.getAprovado());
        destino.setUsuarioAprovacao(usuarioAprovacao);
    }
    
    @Transactional
    public void remover(Long idCandidato) {
    	candidatoRepository.deleteById(idCandidato);
    }
}
