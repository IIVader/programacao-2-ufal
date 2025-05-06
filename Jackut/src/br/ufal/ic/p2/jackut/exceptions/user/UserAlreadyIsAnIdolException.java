package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada que indica que o usu�rio j� foi adicionado como �dolo.
 * Esta classe estende {@link Exception} e � lan�ada quando h� uma tentativa
 * de adicionar um usu�rio que j� est� registrado como �dolo.
 */

public class UserAlreadyIsAnIdolException extends Exception {

    public UserAlreadyIsAnIdolException() {
        super("Usu�rio j� est� adicionado como �dolo.");
    }
}
