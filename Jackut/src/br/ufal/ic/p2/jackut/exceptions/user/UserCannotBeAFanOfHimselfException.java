package br.ufal.ic.p2.jackut.exceptions.user;

public class UserCannotBeAFanOfHimselfException extends Exception {
    public UserCannotBeAFanOfHimselfException() {
        super("Usu�rio n�o pode ser f� de si mesmo.");
    }
}
