package br.ufal.ic.p2.jackut.exceptions.user;

public class UserCannotBeAEnemyOfHimselfException extends Exception {
    public UserCannotBeAEnemyOfHimselfException() {
        super("Usu�rio n�o pode ser inimigo de si mesmo.");
    }
}
