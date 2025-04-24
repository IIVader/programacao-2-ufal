package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

public class UserIsAlreadyInThisCommunityException extends Exception {
    public UserIsAlreadyInThisCommunityException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
