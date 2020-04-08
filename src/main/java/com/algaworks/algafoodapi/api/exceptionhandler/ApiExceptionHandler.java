package com.algaworks.algafoodapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		// campos do nosso ErrorOutput. Caso venhamos a incluir mais campos, acrescentar
		// aqui e tambem no createErrorOutput.
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorType errorType = ErrorType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ErrorType errorType = ErrorType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorType errorType = ErrorType.ERRO_NEGOCIO;
		String detail = ex.getMessage();

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ErrorType errorType = ErrorType.RECURSO_NAO_ENCONTRADO;

		String detail = String.format("O recurso '%s' que você tentou acessar é inexistente.", ex.getRequestURL());

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, new HttpHeaders(), status, request);
	}

	@Override
	public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		String detail = "Foi passado algum parâmetro desconhecido ou de formato inválido na URL.";
		if (ex instanceof MethodArgumentTypeMismatchException) {
			String nomeparametro = ((MethodArgumentTypeMismatchException) ex).getName();
			String valor = ((MethodArgumentTypeMismatchException) ex).getValue().toString();
			String tipo = ((MethodArgumentTypeMismatchException) ex).getRequiredType().getSimpleName();

			detail = String.format(
					"O parâmetro de URL '%s' recebeu o valor '%s' que é de um tipo inválido. Informe um valor do tipo %s.",
					nomeparametro, valor, tipo);
		}

		ErrorType errorType = ErrorType.PARAMETRO_INVALIDO;

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();

		// capturo todos os fields inválidos e construo uma lista de ErrorOutput.Field
		// para colocar no meu ErrorOutput.
		List<ErrorOutput.Field> fields = bindingResult.getFieldErrors().stream().map(fieldError -> {

			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

			return ErrorOutput.Field.builder().fieldname(fieldError.getField()).message(message).build();
		}).collect(Collectors.toList());

		ErrorType errorType = ErrorType.DADOS_INVALIDOS;

		String detail = "Um ou mais campos estão inválidos. Tente novamente passando os dados válidos.";

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).fields(fields).build();

		return handleExceptionInternal(ex, errorOutput, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ErrorType errorType = ErrorType.ERRO_SINTAXE;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ErrorType errorType = ErrorType.ERRO_SINTAXE;
		String detail = String.format("O campo %s é inexistente ou não permitido para edição.", path);

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal((Exception) ex, errorOutput, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ErrorType errorType = ErrorType.ERRO_SINTAXE;
		String detail = String.format(
				"O campo '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, headers, status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorType errorType = ErrorType.ERRO_DE_SISTEMA;
		String detail = "Ocorreu um erro interno inesperado no sistema. "
				+ "Tente novamente e se o problema persistir, entre em contato " + "com o administrador do sistema.";
		ex.printStackTrace();

		ErrorOutput errorOutput = createErrorOutput(status, errorType, detail).build();

		return handleExceptionInternal(ex, errorOutput, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = ErrorOutput.builder().title(status.getReasonPhrase()).status(status.value()).build();
		} else if (body instanceof String) {
			body = ErrorOutput.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	// Caso viemos a incluir novos campos no ErrorOutput, devemos acrescentar aqui
	private ErrorOutput.ErrorOutputBuilder createErrorOutput(HttpStatus status, ErrorType errorType, String detail) {

		return ErrorOutput.builder().datetime(LocalDateTime.now()).status(status.value()).type(errorType.getUri())
				.title(errorType.getTitle()).detail(detail);
	}

}
