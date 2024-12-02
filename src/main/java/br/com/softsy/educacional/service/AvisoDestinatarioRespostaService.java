package br.com.softsy.educacional.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AvisoDestinatarioRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioRespostaDTO;
import br.com.softsy.educacional.model.AvisoDestinatario;
import br.com.softsy.educacional.model.AvisoDestinatarioResposta;
import br.com.softsy.educacional.repository.AvisoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoDestinatarioRespostaRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AvisoDestinatarioRespostaService {
	
	@Autowired
	private AvisoDestinatarioRespostaRepository repository;
	
	@Autowired
	private AvisoDestinatarioRepository avisoDestRepository;
	
    @Transactional(readOnly = true)
    public List<AvisoDestinatarioRespostaDTO> listarTudo() {
        List<AvisoDestinatarioResposta> avisoDestinatarioResp = repository.findAll();
        return avisoDestinatarioResp.stream()
                .map(AvisoDestinatarioRespostaDTO::new)
                .collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
	public AvisoDestinatarioRespostaDTO buscarPorId(Long id) {
		return new AvisoDestinatarioRespostaDTO(repository.getReferenceById(id));
	}
	
    @Transactional(readOnly = true)
    public List<AvisoDestinatarioRespostaDTO> buscarPorIdAvisoDestinatario(Long idAvisoDestinatario) {
        List<AvisoDestinatarioResposta> avisoDestinatarioResp = repository.findByAvisoDestinatario_IdAvisoDestinatario(idAvisoDestinatario)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar resposta do destinatário do aviso por ID do do destinatário"));
        return avisoDestinatarioResp.stream()
                .map(AvisoDestinatarioRespostaDTO::new)
                .collect(Collectors.toList());
    }
	
	@Transactional
	public CadastroAvisoDestinatarioRespostaDTO salvar(CadastroAvisoDestinatarioRespostaDTO dto) throws IOException {

		String base64 = "";
		AvisoDestinatarioResposta avisoDestinatarioResp = criarAvisoDestRespostaAPartirDTO(dto);

		base64 = avisoDestinatarioResp.getPathAnexo();

		avisoDestinatarioResp.setPathAnexo(null);
		avisoDestinatarioResp = repository.save(avisoDestinatarioResp);

	
		String caminhoIMG = ImageManager.salvaImagemAviso(base64, avisoDestinatarioResp.getIdAvisoDestinatarioResposta(),"anexoAviso" + dto.getIdAvisoDestinatarioResposta());

		
		avisoDestinatarioResp.setPathAnexo(caminhoIMG);
		dto.setPathAnexo(caminhoIMG);
		dto.setIdAvisoDestinatarioResposta(avisoDestinatarioResp.getIdAvisoDestinatarioResposta());

		atualizaDados(avisoDestinatarioResp, dto);

		CadastroAvisoDestinatarioRespostaDTO avisoCriado = new CadastroAvisoDestinatarioRespostaDTO(avisoDestinatarioResp);

		return avisoCriado;
	}
	
	private AvisoDestinatarioResposta criarAvisoDestRespostaAPartirDTO(CadastroAvisoDestinatarioRespostaDTO dto) {
		AvisoDestinatarioResposta avisoDestinatarioResp = new AvisoDestinatarioResposta();
		BeanUtils.copyProperties(dto, avisoDestinatarioResp, "idAvisoDestinatarioResposta", "dataCadastro");
		
		
		AvisoDestinatario avisoDest = avisoDestRepository.findById(dto.getAvisoDestinatarioId())
                .orElseThrow(() -> new IllegalArgumentException("Destinatário do aviso não encontrado"));
		
		
		avisoDestinatarioResp.setDataCadastro(LocalDateTime.now());
		avisoDestinatarioResp.setAvisoDestinatario(avisoDest);
		return avisoDestinatarioResp;
	}
	
    
    @Transactional
    public AvisoDestinatarioRespostaDTO atualizarDataLeitura(Long idAvisoDestinatarioResposta, LocalDateTime dataLeitura) {
    	AvisoDestinatarioResposta avisoDestinatarioResp = repository.getReferenceById(idAvisoDestinatarioResposta);
    	avisoDestinatarioResp.setDataLeitura(dataLeitura);
 
        repository.save(avisoDestinatarioResp);
        return new AvisoDestinatarioRespostaDTO(avisoDestinatarioResp);
    }
    
    
	@Transactional
	public AvisoDestinatarioRespostaDTO atualizar(CadastroAvisoDestinatarioRespostaDTO dto) {
		AvisoDestinatarioResposta avisoDestinatarioResp = repository.getReferenceById(dto.getIdAvisoDestinatarioResposta());
		atualizaDados(avisoDestinatarioResp, dto);
		return new AvisoDestinatarioRespostaDTO(avisoDestinatarioResp);
	}
	
	@Transactional
	public AvisoDestinatarioRespostaDTO alterarImagemAvisoDestResp(Long idAvisoDestinatarioResposta, String novaImagemBase64) throws IOException {
		AvisoDestinatarioResposta avisoDestinatarioResp = repository.findById(idAvisoDestinatarioResposta).orElseThrow(() -> new IllegalArgumentException("Resposta do destinatário do aviso não encontrada"));

	    if (avisoDestinatarioResp.getPathAnexo() != null) {
	        File imagemExistente = new File(avisoDestinatarioResp.getPathAnexo());
	        if (imagemExistente.exists()) {
	            imagemExistente.delete();
	        }
	    }

	    String novoCaminhoIMG = ImageManager.salvaImagemAvisoDestinatarioResposta(novaImagemBase64, idAvisoDestinatarioResposta, "avisoDestinatarioResposta" + avisoDestinatarioResp.getIdAvisoDestinatarioResposta());

	    avisoDestinatarioResp.setPathAnexo(novoCaminhoIMG);
	    repository.save(avisoDestinatarioResp);

	    AvisoDestinatarioRespostaDTO avisoAtualizado = new AvisoDestinatarioRespostaDTO(avisoDestinatarioResp);
	    return avisoAtualizado;
	}
    
	private void atualizaDados(AvisoDestinatarioResposta destino, CadastroAvisoDestinatarioRespostaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idAviso", "dataCadastro");
		
		
		AvisoDestinatario avisoDest = avisoDestRepository.findById(origem.getAvisoDestinatarioId())
                .orElseThrow(() -> new IllegalArgumentException("Destinatário do aviso não encontrado"));

		
		destino.setAvisoDestinatario(avisoDest);
		destino.setPathAnexo(origem.getPathAnexo());
	}
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
