package br.ufal.ic.p2.jackut.exceptions.user;

public class UserIsAlreadyYourEnemyException extends Exception{
    public UserIsAlreadyYourEnemyException(){
        super("Usuário já está adicionado como inimigo.");
    }
}
