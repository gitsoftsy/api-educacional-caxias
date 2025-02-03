package br.com.softsy.educacional.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoArquivoDTO;
import br.com.softsy.educacional.dto.CadastroOfertaConcursoImgDTO;
import br.com.softsy.educacional.dto.OfertaConcursoArquivoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoImgDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.OfertaConcursoArquivo;
import br.com.softsy.educacional.model.OfertaConcursoImg;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.OfertaConcursoArquivoRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class OfertaConcursoArquivoService {
	
	@Autowired
	private OfertaConcursoArquivoRepository ofertaConcursoArqRepository;

	@Autowired
	private OfertaConcursoRepository ofertaConcursoRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public CadastroOfertaConcursoArquivoDTO salvar(CadastroOfertaConcursoArquivoDTO dto) throws IOException {
	    String base64 = "";

	    OfertaConcursoArquivo ofertaConcursoArquivo = criarOfertaConcursoArqAPartirDTO(dto);
	    base64 = ofertaConcursoArquivo.getPathArq();

	    ofertaConcursoArquivo.setPathArq("a");

	    ofertaConcursoArquivo = ofertaConcursoArqRepository.save(ofertaConcursoArquivo);
	    String caminhoArq = ImageManager.salvaArquivoOfertaConcurso(base64, ofertaConcursoArquivo.getIdOfertaConcursoArq(),
	            "anexoOfertaConcursoArq");

	    ofertaConcursoArquivo.setPathArq(caminhoArq);
	    dto.setPathArq(caminhoArq);
	    dto.setIdOfertaConcursoArq(ofertaConcursoArquivo.getIdOfertaConcursoArq());

	    atualizaDados(ofertaConcursoArquivo, dto);
	    return new CadastroOfertaConcursoArquivoDTO(ofertaConcursoArquivo);
	}

	private void atualizaDados(OfertaConcursoArquivo destino, CadastroOfertaConcursoArquivoDTO origem) {
	    BeanUtils.copyProperties(origem, destino, "idOfertaConcursoArq", "dataCadastro");

	    OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(origem.getOfertaConcursoId())
	            .orElseThrow(() -> new IllegalArgumentException(
	                    "A oferta de concurso com ID " + origem.getOfertaConcursoId() + " não foi encontrada."));

	    destino.setOfertaConcurso(ofertaConcurso);
	    destino.setPathArq(origem.getPathArq());
	    destino.setNomeArq(origem.getNomeArq());
	}

	private OfertaConcursoArquivo criarOfertaConcursoArqAPartirDTO(CadastroOfertaConcursoArquivoDTO dto) {
	    List<OfertaConcursoArquivo> arquivosExistentes = ofertaConcursoArqRepository
	            .findByOfertaConcurso_IdOfertaConcursoAndOrdem(dto.getOfertaConcursoId(), dto.getOrdem());
	    if (arquivosExistentes != null && !arquivosExistentes.isEmpty()) {
	        throw new IllegalArgumentException("Já existe um arquivo cadastrado com a ordem " + dto.getOrdem()
	                + " para a oferta de concurso com ID " + dto.getOfertaConcursoId());
	    }

	    OfertaConcursoArquivo ofertaConcursoArquivo = new OfertaConcursoArquivo();
	    BeanUtils.copyProperties(dto, ofertaConcursoArquivo, "idOfertaConcursoArq", "pathArq");

	    OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(dto.getOfertaConcursoId())
	            .orElseThrow(() -> new IllegalArgumentException("OfertaConcurso não encontrado"));
	    Conta conta = contaRepository.findById(dto.getContaId())
	            .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
	    Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
	            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

	    ofertaConcursoArquivo.setOfertaConcurso(ofertaConcurso);
	    ofertaConcursoArquivo.setDataCadastro(LocalDateTime.now());
	    ofertaConcursoArquivo.setConta(conta);
	    ofertaConcursoArquivo.setUsuario(usuario);
	    ofertaConcursoArquivo.setPathArq(dto.getPathArq());
	    ofertaConcursoArquivo.setNomeArq(dto.getNomeArq());
	    ofertaConcursoArquivo.setOrdem(dto.getOrdem());

	    return ofertaConcursoArquivo;
	}
	
	@Transactional
	public List<OfertaConcursoArquivoDTO> listarImagensOfertaConcursoArq(Long idOfertaConcurso, Long idConta) {
	    OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(idOfertaConcurso)
	            .orElseThrow(() -> new IllegalArgumentException("A oferta de concurso com ID " + idOfertaConcurso + " não existe."));

	    if (!ofertaConcurso.getCurso().getConta().getIdConta().equals(idConta)) {
	        throw new IllegalArgumentException("A oferta de concurso informada não pertence à conta fornecida.");
	    }
	    List<OfertaConcursoArquivo> arquivos = ofertaConcursoArqRepository.findByOfertaConcurso_IdOfertaConcurso(idOfertaConcurso);

	    return arquivos.stream().map(OfertaConcursoArquivoDTO::new).collect(Collectors.toList());
	}
	
	public OfertaConcursoArquivoDTO obterImagemOfertaConcursoArq(Long idOfertaConcursoArq, Long idConta) {
		OfertaConcursoArquivo arquivo = ofertaConcursoArqRepository.findById(idOfertaConcursoArq)
	            .orElseThrow(() -> new IllegalArgumentException("O arquivo com ID " + idOfertaConcursoArq + " não existe."));

	    if (!arquivo.getConta().getIdConta().equals(idConta)) {
	        throw new IllegalArgumentException("O arquivo informado não pertence à conta fornecida.");
	    }

	    return new OfertaConcursoArquivoDTO(arquivo);
	}
	
	@Transactional
	public void removerImagemOfertaConcursoArq(Long idOfertaConcursoArq, Long idConta) {
	    OfertaConcursoArquivo arquivoRemover = ofertaConcursoArqRepository.findById(idOfertaConcursoArq)
	            .orElseThrow(() -> new IllegalArgumentException("A imagem com ID " + idOfertaConcursoArq + " não existe."));

	    if (!arquivoRemover.getOfertaConcurso().getCurso().getConta().getIdConta().equals(idConta)) {
	        throw new IllegalArgumentException("A imagem com ID " + idOfertaConcursoArq + " não está vinculada à conta com ID " + idConta + ".");
	    }
	    ofertaConcursoArqRepository.delete(arquivoRemover);
	    List<OfertaConcursoArquivo> arquivoRestantes = ofertaConcursoArqRepository.findByOfertaConcurso_IdOfertaConcursoOrderByOrdem(
	    		arquivoRemover.getOfertaConcurso().getIdOfertaConcurso());

	    for (OfertaConcursoArquivo arquivo : arquivoRestantes) {
	        if (arquivo.getOrdem() > arquivoRemover.getOrdem()) {
	        	arquivo.setOrdem(arquivo.getOrdem() - 1);
	            ofertaConcursoArqRepository.save(arquivo); 
	        }
	    }

	    Path path = Paths.get(arquivoRemover.getPathArq());
	    try {
	        Files.deleteIfExists(path);
	    } catch (IOException e) {
	        throw new RuntimeException("Erro ao excluir o arquivo da imagem no servidor.", e);
	    }
	}
	


}
