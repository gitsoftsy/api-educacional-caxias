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
import br.com.softsy.educacional.dto.AvisoInternoDestinatarioRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoDestinatarioRespostaDTO;
import br.com.softsy.educacional.model.AvisoInternoDestinatario;
import br.com.softsy.educacional.model.AvisoInternoDestinatarioResposta;
import br.com.softsy.educacional.repository.AvisoInternoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoInternoDestinatarioRespostaRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AvisoInternoDestinatarioRespostaService {
	
	@Autowired
	private AvisoInternoDestinatarioRespostaRepository repository;
	
	@Autowired
	private AvisoInternoDestinatarioRepository avisoInternoDestRepository;
	
	  @Transactional(readOnly = true)
	    public List<AvisoInternoDestinatarioRespostaDTO> listarTudo() {
	        List<AvisoInternoDestinatarioResposta> avisoInternoDestinatarioResp = repository.findAll();
	        return avisoInternoDestinatarioResp.stream()
	                .map(AvisoInternoDestinatarioRespostaDTO::new)
	                .collect(Collectors.toList());
	    }
		
		@Transactional(readOnly = true)
		public AvisoInternoDestinatarioRespostaDTO buscarPorId(Long id) {
			return new AvisoInternoDestinatarioRespostaDTO(repository.getReferenceById(id));
		}
		
	    @Transactional(readOnly = true)
	    public List<AvisoInternoDestinatarioRespostaDTO> buscarPorIdAvisoInternoDestinatario(Long idAvisoInternoDestinatario) {
	        List<AvisoInternoDestinatarioResposta> avisInternoDestinatarioResp = repository.findByAvisoInternoDestinatario_IdAvisoInternoDestinatario(idAvisoInternoDestinatario)
	                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar resposta do destinatário do aviso Interno  por ID do do destinatário"));
	        return avisInternoDestinatarioResp.stream()
	                .map(AvisoInternoDestinatarioRespostaDTO::new)
	                .collect(Collectors.toList());
	    }
	    
	    @Transactional
	    public CadastroAvisoInternoDestinatarioRespostaDTO salvar(CadastroAvisoInternoDestinatarioRespostaDTO dto) throws IOException {

	        AvisoInternoDestinatarioResposta avisoInternoDestinatarioResp = criarAvisoInternoDestRespostaAPartirDTO(dto);

	        String base64 = avisoInternoDestinatarioResp.getPathAnexo();
	        avisoInternoDestinatarioResp.setPathAnexo(null);
	        avisoInternoDestinatarioResp = repository.save(avisoInternoDestinatarioResp);

	        if (base64 != null && !base64.isEmpty()) {
	            String caminhoIMG = ImageManager.salvaImagemAvisoInternoDestinatarioResposta(
	                base64,
	                avisoInternoDestinatarioResp.getIdAvisoInternoDestinatarioResposta(),
	                "anexoAviso" + dto.getIdAvisoInternoDestinatarioResposta()
	            );

	           
	            avisoInternoDestinatarioResp.setPathAnexo(caminhoIMG);
	            dto.setPathAnexo(caminhoIMG);  

	            avisoInternoDestinatarioResp = repository.save(avisoInternoDestinatarioResp);
	        }

	        dto.setIdAvisoInternoDestinatarioResposta(avisoInternoDestinatarioResp.getIdAvisoInternoDestinatarioResposta());
	        atualizaDados(avisoInternoDestinatarioResp, dto);
	        CadastroAvisoInternoDestinatarioRespostaDTO avisoCriado = new CadastroAvisoInternoDestinatarioRespostaDTO(avisoInternoDestinatarioResp);

	        return avisoCriado;
	    }

		
	    private AvisoInternoDestinatarioResposta criarAvisoInternoDestRespostaAPartirDTO(CadastroAvisoInternoDestinatarioRespostaDTO dto) {
			AvisoInternoDestinatarioResposta avisoInternoDestinatarioResp = new AvisoInternoDestinatarioResposta();
			BeanUtils.copyProperties(dto, avisoInternoDestinatarioResp, "idAvisoInternoDestinatarioResposta", "dataCadastro");
			
			
			AvisoInternoDestinatario avisoInternoDest = avisoInternoDestRepository.findById(dto.getAvisoInternoDestinatario())
	                .orElseThrow(() -> new IllegalArgumentException("Destinatário do aviso não encontrado"));
			
			
			avisoInternoDestinatarioResp.setDataCadastro(LocalDateTime.now());
			avisoInternoDestinatarioResp.setAvisoInternoDestinatario(avisoInternoDest);
			return avisoInternoDestinatarioResp;
		}

	    
	    @Transactional
	    public AvisoInternoDestinatarioRespostaDTO atualizarDataLeitura(Long idAvisoInternoDestinatarioResposta, LocalDateTime dataLeitura) {
	    	AvisoInternoDestinatarioResposta avisoInternoDestinatarioResp = repository.getReferenceById(idAvisoInternoDestinatarioResposta);
	    	avisoInternoDestinatarioResp.setDataLeitura(dataLeitura);
	 
	        repository.save(avisoInternoDestinatarioResp);
	        return new AvisoInternoDestinatarioRespostaDTO(avisoInternoDestinatarioResp);
	    }
	    
	    
		@Transactional
		public AvisoInternoDestinatarioRespostaDTO atualizar(CadastroAvisoInternoDestinatarioRespostaDTO dto) {
			AvisoInternoDestinatarioResposta avisoInternoDestinatarioResp = repository.getReferenceById(dto.getIdAvisoInternoDestinatarioResposta());
			atualizaDados(avisoInternoDestinatarioResp, dto);
			return new AvisoInternoDestinatarioRespostaDTO(avisoInternoDestinatarioResp);
		}
		
		@Transactional
		public AvisoInternoDestinatarioRespostaDTO alterarInternoImagemAvisoDestResp(Long idAvisoInternoDestinatarioResposta, String novaImagemBase64) throws IOException {
			AvisoInternoDestinatarioResposta avisoInternoDestinatarioResp = repository.findById(idAvisoInternoDestinatarioResposta).orElseThrow(() -> new IllegalArgumentException("Resposta do destinatário do aviso Interno não encontrada"));

		    if (avisoInternoDestinatarioResp.getPathAnexo() != null) {
		        File imagemExistente = new File(avisoInternoDestinatarioResp.getPathAnexo());
		        if (imagemExistente.exists()) {
		            imagemExistente.delete();
		        }
		    }

		    String novoCaminhoIMG = ImageManager.salvaImagemAvisoDestinatarioResposta(novaImagemBase64, idAvisoInternoDestinatarioResposta, "avisoDestinatarioResposta" + avisoInternoDestinatarioResp.getIdAvisoInternoDestinatarioResposta());

		    avisoInternoDestinatarioResp.setPathAnexo(novoCaminhoIMG);
		    repository.save(avisoInternoDestinatarioResp);

		    AvisoInternoDestinatarioRespostaDTO avisoInternoAtualizado = new AvisoInternoDestinatarioRespostaDTO(avisoInternoDestinatarioResp);
		    return avisoInternoAtualizado;
		}
	    
		private void atualizaDados(AvisoInternoDestinatarioResposta destino, CadastroAvisoInternoDestinatarioRespostaDTO origem) {
			BeanUtils.copyProperties(origem, destino, "idAvisoInterno", "dataCadastro");
			
			
			AvisoInternoDestinatario avisoInternoDest = avisoInternoDestRepository.findById(origem.getAvisoInternoDestinatario())
	                .orElseThrow(() -> new IllegalArgumentException("Destinatário do aviso não encontrado"));

			
			destino.setAvisoInternoDestinatario(avisoInternoDest);
			destino.setPathAnexo(origem.getPathAnexo());
		}
	    
	    @Transactional
	    public void excluir(Long id) {
	        repository.deleteById(id);
	    }
}
