package br.com.softsy.educacional.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoImgDTO;
import br.com.softsy.educacional.dto.OfertaConcursoImgDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.OfertaConcursoImg;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.OfertaConcursoImgRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class OfertaConcursoImgService {

	@Autowired
	private OfertaConcursoImgRepository ofertaConcursoImgRepository;

	@Autowired
	private OfertaConcursoRepository ofertaConcursoRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public CadastroOfertaConcursoImgDTO salvar(CadastroOfertaConcursoImgDTO dto) throws IOException {
		String base64 = "";

		OfertaConcursoImg ofertaConcursoImg = criarOfertaConcursoImgAPartirDTO(dto);
		base64 = ofertaConcursoImg.getPathImg();

		ofertaConcursoImg.setPathImg("a");

		ofertaConcursoImg = ofertaConcursoImgRepository.save(ofertaConcursoImg);
		String caminhoIMG = ImageManager.salvaImagemOfertaCncurso(base64, ofertaConcursoImg.getIdOfertaConcursoImg(),
				"anexoOfertaConcursoImg");

		ofertaConcursoImg.setPathImg(caminhoIMG);
		dto.setPathImg(caminhoIMG);
		dto.setIdOfertaConcursoImg(ofertaConcursoImg.getIdOfertaConcursoImg());

		atualizaDados(ofertaConcursoImg, dto);
		CadastroOfertaConcursoImgDTO ofertaConcursoImgCriada = new CadastroOfertaConcursoImgDTO(ofertaConcursoImg);
		return ofertaConcursoImgCriada;
	}

	private void atualizaDados(OfertaConcursoImg destino, CadastroOfertaConcursoImgDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idOfertaConcursoImg", "dataCadastro");

		OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(origem.getOfertaConcursoId())
				.orElseThrow(() -> new IllegalArgumentException(
						"A oferta de concurso com ID " + origem.getOfertaConcursoId() + " não foi encontrada."));

		destino.setOfertaConcurso(ofertaConcurso);
		destino.setPathImg(origem.getPathImg());
	}

	private OfertaConcursoImg criarOfertaConcursoImgAPartirDTO(CadastroOfertaConcursoImgDTO dto) {

		if (!"D".equals(dto.getTipoDispositivo()) && !"M".equals(dto.getTipoDispositivo())) {
			throw new IllegalArgumentException("O tipo de dispositivo deve ser 'D' para Desktop ou 'M' para Mobile.");
		}

		List<OfertaConcursoImg> imagensExistentes = ofertaConcursoImgRepository
				.findByOfertaConcurso_IdOfertaConcursoAndOrdem(dto.getOfertaConcursoId(), dto.getOrdem());
		if (imagensExistentes != null && !imagensExistentes.isEmpty()) {
			throw new IllegalArgumentException("Já existe uma imagem cadastrada com a ordem " + dto.getOrdem()
					+ " para a oferta de concurso com ID " + dto.getOfertaConcursoId());
		}

		OfertaConcursoImg ofertaConcursoImg = new OfertaConcursoImg();
		BeanUtils.copyProperties(dto, ofertaConcursoImg, "idOfertaConcursoImg", "pathImg");

		OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(dto.getOfertaConcursoId())
				.orElseThrow(() -> new IllegalArgumentException("OfertaConcurso não encontrado"));
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

		ofertaConcursoImg.setOfertaConcurso(ofertaConcurso);
		ofertaConcursoImg.setDataCadastro(LocalDateTime.now());
		ofertaConcursoImg.setConta(conta);
		ofertaConcursoImg.setUsuario(usuario);
		ofertaConcursoImg.setTipoDispositivo(dto.getTipoDispositivo());
		ofertaConcursoImg.setPathImg(dto.getPathImg());
		ofertaConcursoImg.setOrdem(dto.getOrdem());
		ofertaConcursoImg.setUrl(dto.getUrl());

		return ofertaConcursoImg;
	}

	@Transactional
    public List<OfertaConcursoImgDTO> listarImagensOfertaConcurso(Long idOfertaConcurso, Long idConta) {
        OfertaConcurso ofertaConcurso = ofertaConcursoRepository.findById(idOfertaConcurso)
                .orElseThrow(() -> new IllegalArgumentException(
                        "A oferta de concurso com ID " + idOfertaConcurso + " não existe."));

        if (!ofertaConcurso.getCurso().getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A oferta de concurso informada não pertence à conta fornecida.");
        }
 
        List<OfertaConcursoImg> imagens = ofertaConcursoImgRepository.findByOfertaConcurso_IdOfertaConcurso(idOfertaConcurso);
        return imagens.stream().map(OfertaConcursoImgDTO::new).collect(Collectors.toList());
    }

	public OfertaConcursoImgDTO obterImagemOfertaConcurso(Long idOfertaConcursoImg, Long idConta) {
	    Optional<OfertaConcursoImg> ofertaConcursoImgOptional = ofertaConcursoImgRepository.findById(idOfertaConcursoImg);
	    if (!ofertaConcursoImgOptional.isPresent()) {
	        throw new IllegalArgumentException("A imagem com ID " + idOfertaConcursoImg + " não foi encontrada.");
	    }

	    OfertaConcursoImg ofertaConcursoImg = ofertaConcursoImgOptional.get();

	    // Verificar se a imagem está vinculada ao idConta
	    if (!ofertaConcursoImg.getOfertaConcurso().getCurso().getConta().getIdConta().equals(idConta)) {
	        throw new IllegalArgumentException(
	                "A imagem de oferta de concurso com ID " + idOfertaConcursoImg +
	                " não está vinculada à conta com ID " + idConta + ".");
	    }

	    // Retornar o DTO da imagem
	    return new OfertaConcursoImgDTO(ofertaConcursoImg);
	}
	
	@Transactional
	public void removerImagemOfertaConcurso(Long idOfertaConcursoImg, Long idConta) {
	    // Buscar a imagem a ser removida
	    OfertaConcursoImg imagemRemover = ofertaConcursoImgRepository.findById(idOfertaConcursoImg)
	            .orElseThrow(() -> new IllegalArgumentException("A imagem com ID " + idOfertaConcursoImg + " não existe."));

	    // Validar se a imagem está vinculada à conta fornecida
	    if (!imagemRemover.getOfertaConcurso().getCurso().getConta().getIdConta().equals(idConta)) {
	        throw new IllegalArgumentException("A imagem com ID " + idOfertaConcursoImg + " não está vinculada à conta com ID " + idConta + ".");
	    }

	    // Remover a imagem do banco de dados
	    ofertaConcursoImgRepository.delete(imagemRemover);

	    // Reordenar as imagens restantes
	    List<OfertaConcursoImg> imagensRestantes = ofertaConcursoImgRepository.findByOfertaConcurso_IdOfertaConcursoOrderByOrdem(
	            imagemRemover.getOfertaConcurso().getIdOfertaConcurso());

	    for (OfertaConcursoImg imagem : imagensRestantes) {
	        if (imagem.getOrdem() > imagemRemover.getOrdem()) {
	            imagem.setOrdem(imagem.getOrdem() - 1); // Atualizar a ordem
	            ofertaConcursoImgRepository.save(imagem); // Salvar a reordenação
	        }
	    }

	    // Remover o arquivo da imagem no servidor
	    Path path = Paths.get(imagemRemover.getPathImg());
	    try {
	        Files.deleteIfExists(path);
	    } catch (IOException e) {
	        throw new RuntimeException("Erro ao excluir o arquivo da imagem no servidor.", e);
	    }
	}




}
