package br.ufal.ic.p2.jackut.exceptions.community;

public class ThereAreNoMessagesException extends Exception{
    public ThereAreNoMessagesException(){
        super("Não há mensagens.");
    }
}
