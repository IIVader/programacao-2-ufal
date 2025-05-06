package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que indica que a comunidade solicitada n�o existe.
 * Esta classe estende {@link Exception} e � lan�ada quando h� uma tentativa
 * de acessar ou operar em uma comunidade que n�o foi encontrada.
 */

public class CommunityDoesNotExistsException extends Exception {

    public CommunityDoesNotExistsException() {
        super("Comunidade n�o existe.");
    }
}
