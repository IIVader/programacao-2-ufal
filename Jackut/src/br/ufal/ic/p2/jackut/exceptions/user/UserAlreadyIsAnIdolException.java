package br.ufal.ic.p2.jackut.exceptions.user;

public class UserAlreadyIsAnIdolException extends Exception{
    public UserAlreadyIsAnIdolException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
