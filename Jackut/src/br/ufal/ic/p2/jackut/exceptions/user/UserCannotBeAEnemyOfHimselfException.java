package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada que indica que o usu�rio n�o pode ser inimigo de si mesmo.
 * Esta classe estende {@link Exception} e � lan�ada quando ocorre uma tentativa
 * de definir o pr�prio usu�rio como inimigo.
 */

public class UserCannotBeAEnemyOfHimselfException extends Exception {
    public UserCannotBeAEnemyOfHimselfException() {
        super("Usu�rio n�o pode ser inimigo de si mesmo.");
    }
}
