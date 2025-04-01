package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que � lan�ada quando ocorre uma falha no login ou na senha de um usu�rio.
 * Esta classe estende {@link Exception} e permite fornecer uma mensagem personalizada
 * ao ser lan�ada.
 *
 * <p>Esta exce��o pode ser utilizada para indicar falhas de autentica��o, como quando
 * o usu�rio insere um login ou senha incorretos.</p>
 */

public class InvalidLoginOrPasswordException extends Exception {

    public InvalidLoginOrPasswordException() {
        super("Login ou senha inv�lidos.");
    }
}
