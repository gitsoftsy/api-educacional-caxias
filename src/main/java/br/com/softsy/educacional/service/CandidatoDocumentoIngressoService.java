package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CandidatoDocumentoIngressoDTO;
import br.com.softsy.educacional.dto.ModuloDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.CandidatoDocumentoIngresso;
import br.com.softsy.educacional.model.Modulo;
import br.com.softsy.educacional.model.MotivoReprovacaoDocumento;
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
    
	public String getLogoById(Long idCandidatoDocumentoIngresso, String caminho) throws IOException {
		Optional<CandidatoDocumentoIngresso> documentoOptional = repository.findById(idCandidatoDocumentoIngresso);

		String imagemCarregada;
		imagemCarregada = ImageManager.buscaImagem(caminho);

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
    
    
    @Transactional
    public void aprovarReprovar(char status, Long idCandidatoDocumentoIngresso) {
        CandidatoDocumentoIngresso documento = repository.findById(idCandidatoDocumentoIngresso)
                .orElseThrow(() -> new IllegalArgumentException("Documento não encontrado"));
        documento.setDocAprovado(status);
        repository.save(documento);
    }

    private CandidatoDocumentoIngresso criarCandidatoDocumentoIngressoAPartirDTO(CandidatoDocumentoIngressoDTO dto) {
        CandidatoDocumentoIngresso documento = new CandidatoDocumentoIngresso();
        
        Candidato candidato = candidatoRepository.findById(dto.getCandidatoId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        
        MotivoReprovacaoDocumento motivoReprovacao = motivoReprovacaoDocumentoRepository.findById(dto.getMotivoReprovacaoDocumentoId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        documento.setCandidato(candidato);
        documento.setMotivoReprovacaoDocumento(motivoReprovacao);
        documento.setDocFileServer(dto.getDocFileServer());
        documento.setDocAprovado(dto.getDocAprovado());
        documento.setDataCadastro(LocalDateTime.now());
        documento.setDataAprovacao(dto.getDataAprovacao());
        documento.setObsAprovacao(dto.getObsAprovacao());
        documento.setUsuarioAprovacao(usuarioRepository.findById(dto.getUsuarioAprovacaoId())
                .orElse(null));
        return documento;
    }

    private void atualizarDados(CandidatoDocumentoIngresso destino, CandidatoDocumentoIngressoDTO origem) {
    	
    	Candidato candidato = candidatoRepository.findById(origem.getCandidatoId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        
        MotivoReprovacaoDocumento motivoReprovacao = motivoReprovacaoDocumentoRepository.findById(origem.getMotivoReprovacaoDocumentoId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
    	
        destino.setCandidato(candidato);
        destino.setDocFileServer(origem.getDocFileServer());
        destino.setDocAprovado(origem.getDocAprovado());
        destino.setDataAprovacao(origem.getDataAprovacao());
        destino.setMotivoReprovacaoDocumento(motivoReprovacao);
        destino.setObsAprovacao(origem.getObsAprovacao());
        destino.setUsuarioAprovacao(usuarioRepository.findById(origem.getUsuarioAprovacaoId())
                .orElse(null));
    }
}