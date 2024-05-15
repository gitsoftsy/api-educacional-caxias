package br.com.softsy.educacional.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.service.EscolaService;



public class ImageManager {
	


	public static void saveImage(byte[] imageBytes, String imageName) throws IOException {
		Path imagePath = Paths.get(ImageProperties.getImagePath(), imageName);
		Files.write(imagePath, imageBytes);
	}

	public static byte[] loadImage(String imageName) throws IOException {
		Path path = Paths.get(imageName);
		return Files.readAllBytes(path);
	}
	
	
	public static String buscaImagem(String path) {
		try {
			String diretorio = ImageProperties.getImagePath() + path;
			
			byte[] loadedImage = loadImage(diretorio);
			return byteToBase64(loadedImage);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
		
	}
	
	public static String salvaImagemEscola(String base64, Long escola, String nomeArquivo) {
		EscolaService escolaService;
		
		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioEscola(escola);
			
			//salva a imagem
		
			saveImage(base64ToByte(base64), diretorio+"\\"+nomeArquivo+".jpg");
//			CadastroEscolaDTO novaEscola = new CadastroEscolaDTO();
//			Escola escolaAntiga = new Escola();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return diretorio+"\\"+nomeArquivo+".jpg";
	}

	
	private static byte[] base64ToByte(String base64) {
		// Converter a string Base64 para um array de bytes
		byte[] retorno = Base64.getDecoder().decode(base64);
		return retorno;
	}

	private static String byteToBase64(byte[] byteArray) {
		// Converter a string Base64 para um array de bytes
		String retorno = org.apache.commons.codec.binary.Base64.encodeBase64String(byteArray);
		return retorno;
	}
	
	private static String criaDiretorioEscola(Long idEscola) {
		// Caminho do diretório a ser criado
		String directoryPath = ImageProperties.getImagePath() +"\\escola\\"+idEscola.toString();

		// Criação do objeto Path com o caminho do diretório
		Path directory = Paths.get(directoryPath);

		// Verifica se o diretório não existe e cria se necessário
		if (!Files.exists(directory)) {
			try {
				Files.createDirectories(directory);
				System.out.println("Diretório criado com sucesso!");
			} catch (IOException e) {
				System.out.println("Falha ao criar o diretório.");
				e.printStackTrace();
			}
		} else {
			System.out.println("O diretório já existe.");
		}
		return "\\escola\\"+idEscola.toString();
	}

	
}
