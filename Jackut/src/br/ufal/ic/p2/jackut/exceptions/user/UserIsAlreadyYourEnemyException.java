package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada que indica que o usuário já foi adicionado como inimigo.
 * Esta classe estende {@link Exception} e é lançada quando ocorre uma tentativa
 * de adicionar um usuário como inimigo, mas ele já foi adicionado anteriormente.
 */

public class UserIsAlreadyYourEnemyException extends Exception{
    public UserIsAlreadyYourEnemyException(){
        super("Usuário já está adicionado como inimigo.");
    }
}
