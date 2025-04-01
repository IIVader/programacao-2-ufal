package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que é lançada quando ocorre um erro relacionado a um login inválido.
 * Esta classe estende {@link Exception} e fornece uma mensagem padrão "Login inválido."
 * ao ser lançada.
 *
 * <p>Esta exceção pode ser utilizada para indicar que o login fornecido não é válido
 * ou não corresponde a um registro existente.</p>
 */

public class LoginInvalidException extends Exception {

    public LoginInvalidException() {
        super("Login inválido.");
    }
}
