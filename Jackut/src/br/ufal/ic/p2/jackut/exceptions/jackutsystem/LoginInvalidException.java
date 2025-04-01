package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que � lan�ada quando ocorre um erro relacionado a um login inv�lido.
 * Esta classe estende {@link Exception} e fornece uma mensagem padr�o "Login inv�lido."
 * ao ser lan�ada.
 *
 * <p>Esta exce��o pode ser utilizada para indicar que o login fornecido n�o � v�lido
 * ou n�o corresponde a um registro existente.</p>
 */

public class LoginInvalidException extends Exception {

    public LoginInvalidException() {
        super("Login inv�lido.");
    }
}
