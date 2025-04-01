package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada para indicar que o usu�rio j� foi adicionado como amigo.
 * <p>
 * Esta exce��o � lan�ada quando uma tentativa de adicionar um usu�rio como amigo
 * � feita, mas o usu�rio j� foi adicionado como amigo anteriormente.
 * </p>
 */

public class UserAlreadyIsFriendException extends Exception {
    public UserAlreadyIsFriendException() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}
