package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que é lançada quando ocorre uma falha no login ou na senha de um usuário.
 * Esta classe estende {@link Exception} e permite fornecer uma mensagem personalizada
 * ao ser lançada.
 *
 * <p>Esta exceção pode ser utilizada para indicar falhas de autenticação, como quando
 * o usuário insere um login ou senha incorretos.</p>
 */

public class InvalidLoginOrPasswordException extends Exception {

    public InvalidLoginOrPasswordException() {
        super("Login ou senha inválidos.");
    }
}
