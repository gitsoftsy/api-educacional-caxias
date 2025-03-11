package br.com.softsy.educacional.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


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
			String diretorio = path;

			byte[] loadedImage = loadImage(diretorio);
			return byteToBase64(loadedImage);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

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

	/// ************************ TRATAMENTO DE IMAGENS DE ESCOLA /// ******************************///
	
	public static String salvaImagemEscola(String base64, Long escolaId, String nomeArquivo) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        
        String directoryPath = ImageProperties.getImagePath() +"/uploads/escola/"+escolaId.toString();
 

        Path diretorio = Paths.get(directoryPath);

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + ".png";
 
        // Cria o diretório, se não existir
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
 
        // Salva a imagem no disco
        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
            fos.write(imageBytes);
        }
 
        return caminhoArquivo;
    }
	

	/// ************************ TRATAMENTO DE IMAGENS DE CONTA /// ******************************///

	
	public static String salvaImagemConta(String base64, Long contaId, String nomeArquivo) throws IOException {
        // Decodifica a string base64 em um array de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        
        String directoryPath = ImageProperties.getImagePath() +"/uploads/conta/"+contaId.toString();
 
        // Define o caminho para salvar a imagem
        Path diretorio = Paths.get(directoryPath);
        // Adiciona um timestamp para garantir um nome de arquivo único
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + ".png"; // ou outra extensão
 
        // Cria o diretório, se não existir
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
 
        // Salva a imagem no disco
        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
            fos.write(imageBytes);
        }
 
        return caminhoArquivo;
    }
	

	/// ************************ TRATAMENTO DE DOCUMENTOS /// ******************************///

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

	/// ************************ TRATAMENTO DE IMAGENS DE AGENDA /// ******************************///

	public static String salvaImagemAgenda(String base64, Long agendaId, String nomeArquivo) throws IOException {
        // Decodifica a string base64 em um array de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        
        String directoryPath = ImageProperties.getImagePath() +"/uploads/agenda/"+agendaId.toString();
 
        // Define o caminho para salvar a imagem
        Path diretorio = Paths.get(directoryPath);
        // Adiciona um timestamp para garantir um nome de arquivo único
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + ".png"; // ou outra extensão
 
        // Cria o diretório, se não existir
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
 
        // Salva a imagem no disco
        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
            fos.write(imageBytes);
        }
 
        return caminhoArquivo;
    }

	/// ************************ TRATAMENTO DE IMAGENS DE AVISO/// ******************************///

	public static String salvaImagemAviso(String base64, Long avisoId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/aviso/" + avisoId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;
	 }
	 

	/// ************************ TRATAMENTO DE IMAGENS DE AVISO RESPOSTA /// *********************************///
	
	public static String salvaImagemAvisoResposta(String base64, Long avisoRespostaId, String nomeArquivo) throws IOException {
	   
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/avisoResposta/" + avisoRespostaId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;
	}

	/// ************************ TRATAMENTO DE IMAGENS DE AVISO INTERNO /// *********************************///
	
	public static String salvaImagemAvisoInterno(String base64, Long avisoRespostaId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/avisoInterno/" + avisoRespostaId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;
	}

	

	/// ************************ TRATAMENTO DE IMAGENS DE AVISO INTERNO RESPOSTA /// *********************************///
	
	public static String salvaImagemAvisoInternoResposta(String base64, Long avisoRespostaId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/avisoInternoResposta/" + avisoRespostaId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;
	}
	
	/// ************************ TRATAMENTO DE IMAGENS DE AVISO DESTINATARIO RESPOSTA /// *********************************///
	
	public static String salvaImagemAvisoDestinatarioResposta(String base64, Long avisoDestinatarioRespostaId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/avisoDestinatario/" + avisoDestinatarioRespostaId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;
    }
	
	/// ************************ TRATAMENTO DE IMAGENS DE AVISO DESTINATARIO RESPOSTA /// *********************************///
	
	public static String salvaImagemAvisoInternoDestinatarioResposta(String base64, Long avisoDestinatarioRespostaId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/avisoInternoDestinatario/" + avisoDestinatarioRespostaId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;

}
	
	
	/// ************************ TRATAMENTO DE IMAGENS DE CURSO IMG /// *********************************///
	public static String salvaImagemCurso(String base64, Long cursoImgId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/cursoImg/" + cursoImgId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;

}
	
	 /// ************************ TRATAMENTO DE ARQUIVOS DE CONCURSO /// *********************************///
    
	public static String salvaArquivoConcurso(String base64, Long concursoId, String nomeArquivo) throws IOException {
	    if (base64 == null || base64.trim().isEmpty()) {
	        throw new IllegalArgumentException("O conteúdo Base64 não pode ser nulo ou vazio.");
	    }

	    byte[] fileBytes = Base64.getDecoder().decode(base64);

	    String fileType;
	    if (base64.startsWith("JVBERi0xLj")) {
	        fileType = "pdf";
	    } else {
	        throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente arquivos PDF são aceitos.");
	    }

	    String directoryPath = ImageProperties.getImagePath() + "/uploads/concurso/" + concursoId.toString();
	    Path diretorio = Paths.get(directoryPath);

	    String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	    String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	    File directory = new File(directoryPath);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	        fos.write(fileBytes);
	    }

	    return caminhoArquivo;
	}

 	

	/// ************************ TRATAMENTO DE IMAGENS DE CURSO IMG /// *********************************///
	public static String salvaImagemOfertaCncurso(String base64, Long ofertaConcursoImgId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/ofertaConcursoImg/" + ofertaConcursoImgId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;

}
	
	/// ************************ TRATAMENTO DE IMAGENS DE OFERTA CONCURSO ARQ /// *********************************///
	public static String salvaArquivoOfertaConcurso(String base64, Long ofertaConcursoArqId, String nomeArquivo) throws IOException {
		byte[] fileBytes = Base64.getDecoder().decode(base64);

	     String fileType;
	     if (base64.startsWith("iVBORw0KGgo")) {
	         fileType = "png";
	     } else if (base64.startsWith("JVBERi0xLj")) {
	         fileType = "pdf";
	     } else {
	         throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente PNG e PDF são aceitos.");
	     }

	     String directoryPath = ImageProperties.getImagePath() + "/uploads/ofertaConcursoArq/" + ofertaConcursoArqId.toString();
	     Path diretorio = Paths.get(directoryPath);

	     String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	     String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + "." + fileType;

	     File directory = new File(directoryPath);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }

	     try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	         fos.write(fileBytes);
	     }

	     return caminhoArquivo;

}

	
	/// ************************ TRATAMENTO DE IMAGENS DE CONTA LOGO /// ******************************///

	 public static void excluirImagem(String caminhoImagem) {
	        File imagem = new File(caminhoImagem);
	        if (imagem.exists()) {
	            imagem.delete();
	        }
	    }
	 
		/// ************************ TRATAMENTO DE IMAGENS DE CONTA /// ******************************///

		
		public static String salvaImagemContaLogin(String base64, Long contaId, String nomeArquivo) throws IOException {
	        // Decodifica a string base64 em um array de bytes
	        byte[] imageBytes = Base64.getDecoder().decode(base64);
	        
	        String directoryPath = ImageProperties.getImagePath() +"/uploads/conta/"+contaId.toString();
	 
	        // Define o caminho para salvar a imagem
	        Path diretorio = Paths.get(directoryPath);
	        // Adiciona um timestamp para garantir um nome de arquivo único
	        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	        String caminhoArquivo = directoryPath + "/" + nomeArquivo + "_" + timestamp + ".png"; // ou outra extensão
	 
	        // Cria o diretório, se não existir
	        File directory = new File(directoryPath);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }
	 
	        // Salva a imagem no disco
	        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
	            fos.write(imageBytes);
	        }
	 
	        return caminhoArquivo;
	    }
		
	
 	
 	
}