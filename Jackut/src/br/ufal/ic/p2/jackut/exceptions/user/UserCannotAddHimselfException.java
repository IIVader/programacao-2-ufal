package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada para indicar que o usu�rio n�o pode adicionar a si mesmo como amigo.
 * <p>
 * Esta exce��o � lan�ada quando um usu�rio tenta enviar um convite de amizade para si mesmo,
 * o que n�o � permitido pelo sistema.
 * </p>
 */

public class UserCannotAddHimselfException extends Exception {
    public UserCannotAddHimselfException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
