package br.com.softsy.educacional.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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

		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioEscola(escola);

			// salva a imagem

			saveImage(base64ToByte(base64), diretorio + "\\" + nomeArquivo + ".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return diretorio + "\\" + nomeArquivo + ".jpg";
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
		String directoryPath = ImageProperties.getImagePath() + "\\escola\\" + idEscola.toString();

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
		return "\\escola\\" + idEscola.toString();
	}

	public static String atualizaImagemEscola(Long idEscola, String imagemBase64) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\escola\\" + idEscola.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\escolaLogo.png";

		try {
			byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
			Files.write(Paths.get(imagePath), imagemBytes);
			System.out.println("Imagem atualizada com sucesso!");
		} catch (IOException e) {
			System.out.println("Falha ao atualizar a imagem.");
			e.printStackTrace();
		}

		return "\\escola\\" + idEscola.toString() + "\\escolaLogo.png";
	}

	/// ************************ TRATAMENTO DE IMAGENS DE CONTA
	/// ******************************///

	private static String criaDiretorioConta(Long idConta) {
		// Caminho do diretório a ser criado
		String directoryPath = ImageProperties.getImagePath() + "\\conta\\" + idConta.toString();

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
		return "\\conta\\" + idConta.toString();
	}

	public static String salvaImagemConta(String base64, Long conta, String nomeArquivo) {

		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioConta(conta);

			// salva a imagem

			saveImage(base64ToByte(base64), diretorio + "\\" + nomeArquivo + ".jpg");
			;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return diretorio + "\\" + nomeArquivo + ".jpg";
	}

	public static String atualizaImagemConta(Long idConta, String imagemBase64) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\conta\\" + idConta.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\contaLogo.png";

		try {
			byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
			Files.write(Paths.get(imagePath), imagemBytes);
			System.out.println("Imagem atualizada com sucesso!");
		} catch (IOException e) {
			System.out.println("Falha ao atualizar a imagem.");
			e.printStackTrace();
		}

		return "\\conta\\" + idConta.toString() + "\\contaLogo.png";
	}

	/// ************************ TRATAMENTO DE IMAGENS DE MODULO
	/// ******************************///

	private static String criaDiretorioModulo(Long idModulo) {
		// Caminho do diretório a ser criado
		String directoryPath = ImageProperties.getImagePath() + "\\modulo\\" + idModulo.toString();

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
		return "\\modulo\\" + idModulo.toString();
	}

	public static String salvaImagemModulo(String base64, Long modulo, String nomeArquivo) {

		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioModulo(modulo);

			// salva a imagem

			saveImage(base64ToByte(base64), diretorio + "\\" + nomeArquivo + ".jpg");
			;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return diretorio + "\\" + nomeArquivo + ".jpg";
	}

	public static String atualizaImagemModulo(Long idModulo, String imagemBase64) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\modulo\\" + idModulo.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\moduloIcone.png";

		try {
			byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
			Files.write(Paths.get(imagePath), imagemBytes);
			System.out.println("Imagem atualizada com sucesso!");
		} catch (IOException e) {
			System.out.println("Falha ao atualizar a imagem.");
			e.printStackTrace();
		}

		return "\\modulo\\" + idModulo.toString() + "\\moduloIcone.png";
	}

	/// ************************ TRATAMENTO DE DOCUMENTOS
	/// ******************************///

	private static String criaDiretorioDocumento(Long idCandidatoDocumentoIngresso) {
		// Caminho do diretório a ser criado
		String directoryPath = ImageProperties.getImagePath() + "\\documentosCandidato\\"
				+ idCandidatoDocumentoIngresso.toString();

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
		return "\\documentosCandidato\\" + idCandidatoDocumentoIngresso.toString();
	}

	public static String salvaImagemDocumento(String base64, Long documentoCandidato, String nomeArquivo) {

		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioDocumento(documentoCandidato);

			// salva a imagem

			saveImage(base64ToByte(base64), diretorio + "\\" + nomeArquivo + ".pdf");
			;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return diretorio + "\\" + nomeArquivo + ".pdf";
	}

	public static String atualizaImagemDocumento(Long idCandidatoDocumentoIngresso, String imagemBase64) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\documentosCandidato\\"
				+ idCandidatoDocumentoIngresso.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\candidatoDoc.pdf";

		try {
			// Verifica se o diretório existe, caso não exista, cria
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs(); // Cria diretórios pais se não existirem
			}

			byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
			Files.write(Paths.get(imagePath), imagemBytes);
			System.out.println("Imagem atualizada com sucesso!");
		} catch (IOException e) {
			System.out.println("Falha ao atualizar a imagem.");
			e.printStackTrace();
		}

		return "\\documentosCandidato\\" + idCandidatoDocumentoIngresso.toString() + "\\candidatoDoc.pdf";
	}

	public static void excluirImagemDocumento(Long idCandidatoDocumentoIngresso) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\documentosCandidato\\"
				+ idCandidatoDocumentoIngresso.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\candidatoDoc.pdf";

		try {
			// Verifica se o arquivo existe antes de excluí-lo
			File arquivo = new File(imagePath);
			if (arquivo.exists()) {
				arquivo.delete(); // Exclui o arquivo do diretório
				System.out.println("Imagem excluída com sucesso!");
			}
		} catch (Exception e) {
			System.out.println("Falha ao excluir a imagem.");
			e.printStackTrace();
		}
	}

	/// ************************ TRATAMENTO DE IMAGENS DE AGENDA
	/// ******************************///

	private static String criaDiretorioAgenda(Long idAgendaAnexo) {
		// Caminho do diretório a ser criado
		String directoryPath = ImageProperties.getImagePath() + "\\agenda\\" + idAgendaAnexo.toString();

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
		return "\\agenda\\" + idAgendaAnexo.toString();
	}

	public static String salvaImagemAgenda(String base64, Long agenda, String nomeArquivo) {

		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioAgenda(agenda);

			// salva a imagem

			saveImage(base64ToByte(base64), diretorio + "\\" + nomeArquivo + ".jpg");
			;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return diretorio + "\\" + nomeArquivo + ".jpg";
	}

	public static String atualizaImagemAgenda(Long idAgendaAnexo, String imagemBase64) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\agenda\\" + idAgendaAnexo.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\agendaAnexo.png";

		try {
			byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
			Files.write(Paths.get(imagePath), imagemBytes);
			System.out.println("Imagem atualizada com sucesso!");
		} catch (IOException e) {
			System.out.println("Falha ao atualizar a imagem.");
			e.printStackTrace();
		}

		return "\\idAgendaAnexo\\" + idAgendaAnexo.toString() + "\\agendaAnexo.png";
	}

	/// ************************ TRATAMENTO DE IMAGENS DE AVISO/// ******************************///

	private static String criaDiretorioAviso(Long idAviso) {
		// Caminho do diretório a ser criado
		String directoryPath = ImageProperties.getImagePath() + "\\aviso\\" + idAviso.toString();

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
		return "\\aviso\\" + idAviso.toString();
	}

	public static String salvaImagemAviso(String base64, Long aviso, String nomeArquivo) {

		String diretorio = "";
		try {
			// cria o diretorio para salvar o logo
			diretorio = criaDiretorioAviso(aviso);

			// salva a imagem

			saveImage(base64ToByte(base64), diretorio + "\\" + nomeArquivo + ".jpg");
			;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return diretorio + "\\" + nomeArquivo + ".jpg";
	}

	public static String atualizaImagemAviso(Long idAviso, String imagemBase64) {
		// Caminho do diretório onde a imagem está localizada
		String directoryPath = ImageProperties.getImagePath() + "\\aviso\\" + idAviso.toString();

		// Caminho completo da imagem
		String imagePath = directoryPath + "\\aviso.png";

		try {
			byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
			Files.write(Paths.get(imagePath), imagemBytes);
			System.out.println("Imagem atualizada com sucesso!");
		} catch (IOException e) {
			System.out.println("Falha ao atualizar a imagem.");
			e.printStackTrace();
		}

		return "\\idAviso\\" + idAviso.toString() + "\\aviso.png";
	}

}
