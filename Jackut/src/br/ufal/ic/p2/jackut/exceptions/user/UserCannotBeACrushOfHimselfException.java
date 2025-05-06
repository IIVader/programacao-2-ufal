package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada que indica que o usuário não pode ser paquera de si mesmo.
 * Esta classe estende {@link Exception} e é lançada quando ocorre uma tentativa
 * de definir o próprio usuário como paquera.
 */

public class UserCannotBeACrushOfHimselfException extends Exception {
    public UserCannotBeACrushOfHimselfException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
