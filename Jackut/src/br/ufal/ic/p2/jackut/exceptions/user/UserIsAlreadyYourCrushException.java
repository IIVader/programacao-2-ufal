package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada que indica que o usu�rio j� foi adicionado como paquera.
 * Esta classe estende {@link Exception} e � lan�ada quando ocorre uma tentativa
 * de adicionar um usu�rio como paquera, mas ele j� foi adicionado anteriormente.
 */
public class UserIsAlreadyYourCrushException extends Exception {
    public UserIsAlreadyYourCrushException() {
        super("Usu�rio j� est� adicionado como paquera.");
    }
}
