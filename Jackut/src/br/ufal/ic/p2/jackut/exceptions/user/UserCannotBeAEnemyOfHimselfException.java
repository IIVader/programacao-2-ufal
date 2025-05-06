package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada que indica que o usuário não pode ser inimigo de si mesmo.
 * Esta classe estende {@link Exception} e é lançada quando ocorre uma tentativa
 * de definir o próprio usuário como inimigo.
 */

public class UserCannotBeAEnemyOfHimselfException extends Exception {
    public UserCannotBeAEnemyOfHimselfException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
