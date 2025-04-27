package br.ufal.ic.p2.jackut.exceptions.user;

public class UserCannotBeAEnemyOfHimselfException extends Exception {
    public UserCannotBeAEnemyOfHimselfException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
