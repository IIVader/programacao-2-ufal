package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada que indica que o usu�rio n�o pode ser paquera de si mesmo.
 * Esta classe estende {@link Exception} e � lan�ada quando ocorre uma tentativa
 * de definir o pr�prio usu�rio como paquera.
 */

public class UserCannotBeACrushOfHimselfException extends Exception {
    public UserCannotBeACrushOfHimselfException() {
        super("Usu�rio n�o pode ser paquera de si mesmo.");
    }
}
