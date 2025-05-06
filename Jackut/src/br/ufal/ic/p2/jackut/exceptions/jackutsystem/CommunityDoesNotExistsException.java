package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que indica que a comunidade solicitada não existe.
 * Esta classe estende {@link Exception} e é lançada quando há uma tentativa
 * de acessar ou operar em uma comunidade que não foi encontrada.
 */

public class CommunityDoesNotExistsException extends Exception {

    public CommunityDoesNotExistsException() {
        super("Comunidade não existe.");
    }
}
