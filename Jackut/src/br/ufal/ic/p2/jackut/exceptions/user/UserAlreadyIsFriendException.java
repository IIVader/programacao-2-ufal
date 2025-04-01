package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada para indicar que o usuário já foi adicionado como amigo.
 * <p>
 * Esta exceção é lançada quando uma tentativa de adicionar um usuário como amigo
 * é feita, mas o usuário já foi adicionado como amigo anteriormente.
 * </p>
 */

public class UserAlreadyIsFriendException extends Exception {
    public UserAlreadyIsFriendException() {
        super("Usuário já está adicionado como amigo.");
    }
}
