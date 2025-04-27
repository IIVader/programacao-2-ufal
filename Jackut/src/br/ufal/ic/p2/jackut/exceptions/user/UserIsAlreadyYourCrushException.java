package br.ufal.ic.p2.jackut.exceptions.user;

public class UserIsAlreadyYourCrushException extends Exception {
    public UserIsAlreadyYourCrushException() {
        super("Usuário já está adicionado como paquera.");
    }
}
