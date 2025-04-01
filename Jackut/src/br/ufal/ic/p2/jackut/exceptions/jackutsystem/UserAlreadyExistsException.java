package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada para indicar que j� existe uma conta com o login de usu�rio fornecido.
 * <p>
 * Esta exce��o � lan�ada quando uma tentativa de registrar um novo usu�rio � feita,
 * mas j� existe uma conta com o mesmo login de usu�rio no sistema.
 * </p>
 */

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("Conta com esse nome j� existe.");
    }
}
