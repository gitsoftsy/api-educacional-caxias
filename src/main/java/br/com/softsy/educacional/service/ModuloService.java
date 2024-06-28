package br.com.softsy.educacional.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.CadastroModuloDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.dto.ModuloDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.Modulo;
import br.com.softsy.educacional.repository.ModuloRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class ModuloService {

    @Autowired
    ModuloRepository moduloRepository;

    @Transactional(readOnly = true)
    public List<ModuloDTO> listarTudo() {
        List<Modulo> modulos = moduloRepository.findAll();
        return modulos.stream().map(ModuloDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ModuloDTO buscarPorId(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar módulo por ID"));
        return new ModuloDTO(modulo);
    }
    
	public String getLogoById(Long idModulo, String caminho) throws IOException {
		Optional<Modulo> contaOptional = moduloRepository.findById(idModulo);

		String imagemCarregada;
		imagemCarregada = ImageManager.buscaImagem(caminho);

		if (contaOptional.isPresent()) {
			return imagemCarregada;
		} else {
			return null;
		}
	}

    @Transactional
    public CadastroModuloDTO salvar(CadastroModuloDTO dto) {
        validarModulo(dto.getModulo());
        
        String base64 = "";
        Modulo modulo = criarModuloAPartirDTO(dto);
        
        base64 = modulo.getIcone();
        
        modulo.setIcone(null);
        modulo = moduloRepository.save(modulo);
        
     // Manipulando a imagem e obtendo o caminho
     	String caminhoIMG = ImageManager.salvaImagemModulo(base64, modulo.getIdModulo(),"iconeModulo" + dto.getModulo());

     	modulo.setIcone(caminhoIMG);
		dto.setIcone(caminhoIMG);
		dto.setIdModulo(modulo.getIdModulo());
		
		atualizaDados(modulo, dto);

		CadastroModuloDTO moduloCriado = new CadastroModuloDTO(modulo);
		return moduloCriado;
    }

	@Transactional
	public ModuloDTO atualizar(CadastroModuloDTO dto) {
		Modulo modulo = moduloRepository.getReferenceById(dto.getIdModulo());
	      
		modulo = moduloRepository.save(modulo);

	    // Manipulando a imagem e obtendo o caminho
	    String caminhoIMG = ImageManager.atualizaImagemModulo(dto.getIdModulo(), dto.getIcone());

	    // Setando a imagem diretamente no objeto escola
	    modulo.setIcone(caminhoIMG); 
	    dto.setIcone(caminhoIMG);
	    dto.setIdModulo(modulo.getIdModulo());
		
		
		atualizaDados(modulo, dto);
		return new ModuloDTO(modulo);
	}

    private Modulo criarModuloAPartirDTO(CadastroModuloDTO dto) {
        Modulo modulo = new Modulo();
        modulo.setModulo(dto.getModulo());
        modulo.setIcone(dto.getIcone());
        return modulo;
    }


    private void validarModulo(String modulo) {
        Optional<Modulo> moduloExistente = moduloRepository.findByModulo(modulo).stream().findFirst();
        if (moduloExistente.isPresent()) {
            throw new IllegalArgumentException("Esse módulo já existe.");
        }
    }

    private void atualizaDados(Modulo destino, CadastroModuloDTO origem) {
        destino.setModulo(origem.getModulo());
        destino.setIcone(origem.getIcone());
    }
    
	@Transactional
	public void remover(Long id) {
		moduloRepository.deleteById(id);
	}
}