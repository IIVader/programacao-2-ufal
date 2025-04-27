package br.ufal.ic.p2.jackut.exceptions.user;

public class UserCannotBeAFanOfHimselfException extends Exception {
    public UserCannotBeAFanOfHimselfException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
