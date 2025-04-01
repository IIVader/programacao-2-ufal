package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que � lan�ada quando ocorre um erro relacionado a uma senha inv�lida.
 * Esta classe estende {@link Exception} e fornece uma mensagem padr�o "Senha inv�lida."
 * ao ser lan�ada.
 *
 * <p>Esta exce��o pode ser utilizada para indicar que a senha fornecida � inv�lida ou n�o corresponde
 * aos crit�rios de seguran�a esperados.</p>
 */

public class PasswordInvalidException extends Exception {

    public PasswordInvalidException() {
        super("Senha inv�lida.");
    }
}
