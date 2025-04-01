package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada para indicar que um pedido de amizade já foi enviado.
 * <p>
 * Esta exceção é lançada quando um usuário tenta enviar um convite de amizade
 * para outro usuário que já foi adicionado como amigo, estando aguardando
 * a aceitação do convite.
 * </p>
 */

public class RequestAlreadySendedException extends Exception {

    public RequestAlreadySendedException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
