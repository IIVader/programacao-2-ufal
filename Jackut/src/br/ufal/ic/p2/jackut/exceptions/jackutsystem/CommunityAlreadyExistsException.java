package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

public class CommunityAlreadyExistsException extends Exception {

    public CommunityAlreadyExistsException() {
        super("Comunidade com esse nome já existe.");
    }
}
