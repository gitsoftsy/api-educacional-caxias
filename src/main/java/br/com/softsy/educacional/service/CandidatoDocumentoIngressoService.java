package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CandidatoDocumentoIngressoDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.CandidatoDocumentoIngresso;
import br.com.softsy.educacional.model.MotivoReprovacaoDocumento;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.CandidatoDocumentoIngressoRepository;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.MotivoReprovacaoDocumentoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class CandidatoDocumentoIngressoService {

    @Autowired
    private CandidatoDocumentoIngressoRepository repository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private MotivoReprovacaoDocumentoRepository motivoReprovacaoDocumentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<CandidatoDocumentoIngressoDTO> listarTudo() {
        List<CandidatoDocumentoIngresso> documentos = repository.findAll();
        return documentos.stream()
                .map(CandidatoDocumentoIngressoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CandidatoDocumentoIngressoDTO buscarPorId(Long id) {
    	CandidatoDocumentoIngresso documentos = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar módulo por ID"));
        return new CandidatoDocumentoIngressoDTO(documentos);
    }
    
    @Transactional(readOnly = true)
    public List<CandidatoDocumentoIngressoDTO> buscarPorIdCandidato(Long idCandidato) {
        List<CandidatoDocumentoIngresso> curso = repository.findByCandidato_IdCandidato(idCandidato)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar concurso por ID do candidato"));
        return curso.stream()
                .map(CandidatoDocumentoIngressoDTO::new)
                .collect(Collectors.toList());
    }
    
	public String getLogoById(Long idCandidatoDocumentoIngresso) throws IOException {
		Optional<CandidatoDocumentoIngresso> documentoOptional = repository.findById(idCandidatoDocumentoIngresso);

		String imagemCarregada;
		imagemCarregada = ImageManager.buscaImagem(documentoOptional.get().getDocFileServer());

		if (documentoOptional.isPresent()) {
			return imagemCarregada;
		} else {
			return null;
		}
	}

    
    @Transactional
    public CandidatoDocumentoIngressoDTO salvar(CandidatoDocumentoIngressoDTO dto) {
        
        String base64 = "";
        CandidatoDocumentoIngresso documento = criarCandidatoDocumentoIngressoAPartirDTO(dto);
        
        base64 = documento.getDocFileServer();
        
        documento.setDocFileServer(null);
        documento = repository.save(documento);
        
     // Manipulando a imagem e obtendo o caminho
     	String caminhoIMG = ImageManager.salvaImagemDocumento(base64, documento.getIdCandidatoDocumentoIngresso(),"documentoCandidato" + LocalDate.now());

     	documento.setDocFileServer(caminhoIMG);
		dto.setDocFileServer(caminhoIMG);
		dto.setIdCandidatoDocumentoIngresso(documento.getIdCandidatoDocumentoIngresso());
		
		atualizarDados(documento, dto);

		CandidatoDocumentoIngressoDTO documentoCriado = new CandidatoDocumentoIngressoDTO(documento);
		return documentoCriado;
    }
                
    @Transactional
    public CandidatoDocumentoIngressoDTO excluirImagem(Long idCandidatoDocumentoIngresso) {
        CandidatoDocumentoIngresso documento = repository.getReferenceById(idCandidatoDocumentoIngresso);
        String caminhoAntigo = documento.getDocFileServer();

        // Exclui a imagem do diretório
        ImageManager.excluirImagemDocumento(idCandidatoDocumentoIngresso);

        // Define o campo como NULL apenas se havia um caminho antes
        if (caminhoAntigo != null && !caminhoAntigo.isEmpty()) {
            documento.setDocFileServer(null);
            documento.setDocAprovado(null);
            repository.save(documento);
        }

        return new CandidatoDocumentoIngressoDTO(documento);
    }
    

    private CandidatoDocumentoIngresso criarCandidatoDocumentoIngressoAPartirDTO(CandidatoDocumentoIngressoDTO dto) {
        CandidatoDocumentoIngresso documento = new CandidatoDocumentoIngresso();
        
        Candidato candidato = candidatoRepository.findById(dto.getCandidatoId())
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
        
     
        MotivoReprovacaoDocumento motivoReprovacao = null;
        if (dto.getMotivoReprovacaoDocumentoId() != null) {
        	motivoReprovacao = motivoReprovacaoDocumentoRepository.findById(dto.getMotivoReprovacaoDocumentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Motivo de reprovação não encontrado"));
        }
        
        BeanUtils.copyProperties(dto, documento, "docAprovado");
        documento.setCandidato(candidato);
        documento.setMotivoReprovacaoDocumento(motivoReprovacao);
        documento.setDocFileServer(dto.getDocFileServer());
        documento.setDataCadastro(LocalDateTime.now());
        
        Usuario usuarioAprovacao = null;
        if (dto.getUsuarioAprovacaoId() != null) {
        	usuarioAprovacao = usuarioRepository.findById(dto.getUsuarioAprovacaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário de aprovação não encontrado"));
        }
        
        documento.setUsuarioAprovacao(usuarioAprovacao);
        
        return documento;
    }
    
    @Transactional
    public CandidatoDocumentoIngressoDTO atualizar(CandidatoDocumentoIngressoDTO dto) {
    	CandidatoDocumentoIngresso documento = repository.findById(dto.getIdCandidatoDocumentoIngresso())
                .orElseThrow(() -> new IllegalArgumentException("Concurso não encontrado"));
    	atualizarDadosSemImagem(documento, dto);
        documento = repository.save(documento);
        return new CandidatoDocumentoIngressoDTO(documento);
    }

    private void atualizarDados(CandidatoDocumentoIngresso destino, CandidatoDocumentoIngressoDTO origem) {
    	
        MotivoReprovacaoDocumento motivoReprovacao = null;
        if (origem.getMotivoReprovacaoDocumentoId() != null) {
        	motivoReprovacao = motivoReprovacaoDocumentoRepository.findById(origem.getMotivoReprovacaoDocumentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Motivo de reprovação não encontrado"));
        }
    	
        BeanUtils.copyProperties(origem, destino, "candidatoId", "dataCadastro");
        destino.setDocFileServer(origem.getDocFileServer());
        destino.setDocAprovado(origem.getDocAprovado());
        destino.setDataAprovacao(origem.getDataAprovacao());
        destino.setMotivoReprovacaoDocumento(motivoReprovacao);
        destino.setObsAprovacao(origem.getObsAprovacao());
        
        Usuario usuarioAprovacao = null;
        if (origem.getUsuarioAprovacaoId() != null) {
        	usuarioAprovacao = usuarioRepository.findById(origem.getUsuarioAprovacaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário de aprovação não encontrado"));
        }
        
        destino.setUsuarioAprovacao(usuarioAprovacao);
    }
    
    private void atualizarDadosSemImagem(CandidatoDocumentoIngresso destino, CandidatoDocumentoIngressoDTO origem) {
        MotivoReprovacaoDocumento motivoReprovacao = null;
        if (origem.getMotivoReprovacaoDocumentoId() != null) {
            motivoReprovacao = motivoReprovacaoDocumentoRepository.findById(origem.getMotivoReprovacaoDocumentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Motivo de reprovação não encontrado"));
        }

        // Copiando propriedades, exceto 'candidatoId', 'dataCadastro' e 'docFileServer'
        BeanUtils.copyProperties(origem, destino, "candidatoId", "dataCadastro", "docFileServer");

        // Setando as propriedades restantes manualmente, exceto 'docFileServer' e 'dataCadastro'
        destino.setDocAprovado(origem.getDocAprovado());
        destino.setDataAprovacao(origem.getDataAprovacao());
        destino.setMotivoReprovacaoDocumento(motivoReprovacao);
        destino.setObsAprovacao(origem.getObsAprovacao());

        Usuario usuarioAprovacao = null;
        if (origem.getUsuarioAprovacaoId() != null) {
            usuarioAprovacao = usuarioRepository.findById(origem.getUsuarioAprovacaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário de aprovação não encontrado"));
        }

        destino.setUsuarioAprovacao(usuarioAprovacao);
    }
    
}