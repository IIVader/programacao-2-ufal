package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada que indica que o usuário já foi adicionado como paquera.
 * Esta classe estende {@link Exception} e é lançada quando ocorre uma tentativa
 * de adicionar um usuário como paquera, mas ele já foi adicionado anteriormente.
 */
public class UserIsAlreadyYourCrushException extends Exception {
    public UserIsAlreadyYourCrushException() {
        super("Usuário já está adicionado como paquera.");
    }
}
