package br.ufal.ic.p2.jackut.exceptions.user;

public class UserCannotBeACrushOfHimselfException extends Exception {
    public UserCannotBeACrushOfHimselfException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
