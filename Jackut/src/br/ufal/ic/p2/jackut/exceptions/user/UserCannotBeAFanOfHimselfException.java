package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada que indica que o usu�rio n�o pode ser f� de si mesmo.
 * Esta classe estende {@link Exception} e � lan�ada quando ocorre uma tentativa
 * de definir o pr�prio usu�rio como f�.
 */

public class UserCannotBeAFanOfHimselfException extends Exception {
    public UserCannotBeAFanOfHimselfException() {
        super("Usu�rio n�o pode ser f� de si mesmo.");
    }
}
