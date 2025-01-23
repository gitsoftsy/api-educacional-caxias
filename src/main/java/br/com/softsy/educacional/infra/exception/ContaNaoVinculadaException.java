package br.com.softsy.educacional.infra.exception;

public class ContaNaoVinculadaException extends RuntimeException {
	   public ContaNaoVinculadaException(Long idAluno, Long idConta) {
	       super(validarVinculo(idAluno, idConta));
	   }
	   public ContaNaoVinculadaException(String mensagem) {
	       super(mensagem);
	   }
	   private static String validarVinculo(Long idAluno, Long idConta) {
	// Simula a validação de vínculo (esta lógica deve ser substituída por algo real)
	       boolean vinculado = verificarAlunoConta(idAluno, idConta);
	       if (!vinculado) {
	           return "O aluno com ID " + idAluno + " não está vinculado à conta com ID " + idConta;
	       }
	       return "Erro inesperado ao validar vínculo do aluno com a conta."; // Caso nunca entre no throw
	   }
	// Simula um método que verifica se o aluno pertence à conta
	   private static boolean verificarAlunoConta(Long idAluno, Long idConta) {
	// Aqui você deve implementar a chamada real ao banco de dados ou serviço para validar o vínculo
	// Exemplo fictício: substitua com a lógica real
	       if (idAluno != null && idConta != null) {
	// Exemplo fictício: considera que alunos com ID par estão vinculados
	           return idAluno % 2 == 0 && idConta % 2 == 0;
	       }
	       return false;
	   }
	}