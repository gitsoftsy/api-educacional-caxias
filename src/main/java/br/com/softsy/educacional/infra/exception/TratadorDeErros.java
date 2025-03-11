package br.com.softsy.educacional.infra.exception;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingRequestHeaderException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<StandardError> tratarExcecaoRegraNegocio(NegocioException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Erro ao salvar");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> tratarErro404(EntityNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Recurso não encontrado");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(UniqueException.class)
	public ResponseEntity<StandardError> tratarErroUnique(UniqueException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Recurso já existente");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> tratarErroUnicidade(DataIntegrityViolationException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Duplicidade de registro");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErroValidacao>> tratarErro400(MethodArgumentNotValidException e) {
		List<FieldError> erros = e.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(ErroValidacao::new).collect(Collectors.toList()));
	}

	@ExceptionHandler(DataException.class)
	public ResponseEntity<StandardError> tratarErroBanco(DataException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Erro ao salvar no banco");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> tratarErroGeral(Exception e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Erro na requisição");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> tratarErroValidacao(IllegalArgumentException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Parâmetro inválido");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<StandardError> tratarHeaderObrigatorio(MissingRequestHeaderException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Parâmetro obrigatório"); 
		error.setMessage("O parâmetro de cabeçalho '" + e.getHeaderName() + "' é obrigatório."); 
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}
	

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<StandardError> tratarPathVariableObrigatorio(MissingPathVariableException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Parâmetro obrigatório");
        error.setMessage("O parâmetro de URL '" + e.getVariableName() + "' é obrigatório.");
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

}
