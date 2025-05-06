package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada que indica que o usu�rio j� foi adicionado como inimigo.
 * Esta classe estende {@link Exception} e � lan�ada quando ocorre uma tentativa
 * de adicionar um usu�rio como inimigo, mas ele j� foi adicionado anteriormente.
 */

public class UserIsAlreadyYourEnemyException extends Exception{
    public UserIsAlreadyYourEnemyException(){
        super("Usu�rio j� est� adicionado como inimigo.");
    }
}
