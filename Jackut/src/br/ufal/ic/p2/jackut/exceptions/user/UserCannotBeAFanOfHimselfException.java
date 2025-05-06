package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada que indica que o usuário não pode ser fã de si mesmo.
 * Esta classe estende {@link Exception} e é lançada quando ocorre uma tentativa
 * de definir o próprio usuário como fã.
 */

public class UserCannotBeAFanOfHimselfException extends Exception {
    public UserCannotBeAFanOfHimselfException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
