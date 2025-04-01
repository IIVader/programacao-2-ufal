package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada para indicar que o usu�rio n�o est� registrado.
 * <p>
 * Esta exce��o � lan�ada quando uma opera��o � tentada com um usu�rio que
 * n�o est� cadastrado no sistema.
 * </p>
 */

public class UnregisteredUserException extends Exception {
    public UnregisteredUserException() {
        super("Usu�rio n�o cadastrado.");
    }
}
