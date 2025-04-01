package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que é lançada quando ocorre um erro relacionado a uma senha inválida.
 * Esta classe estende {@link Exception} e fornece uma mensagem padrão "Senha inválida."
 * ao ser lançada.
 *
 * <p>Esta exceção pode ser utilizada para indicar que a senha fornecida é inválida ou não corresponde
 * aos critérios de segurança esperados.</p>
 */

public class PasswordInvalidException extends Exception {

    public PasswordInvalidException() {
        super("Senha inválida.");
    }
}
