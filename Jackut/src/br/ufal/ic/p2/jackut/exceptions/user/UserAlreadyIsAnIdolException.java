package br.ufal.ic.p2.jackut.exceptions.user;

public class UserAlreadyIsAnIdolException extends Exception{
    public UserAlreadyIsAnIdolException() {
        super("Usu�rio j� est� adicionado como �dolo.");
    }
}
