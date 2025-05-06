package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada que indica que o usuário já foi adicionado como ídolo.
 * Esta classe estende {@link Exception} e é lançada quando há uma tentativa
 * de adicionar um usuário que já está registrado como ídolo.
 */

public class UserAlreadyIsAnIdolException extends Exception {

    public UserAlreadyIsAnIdolException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
