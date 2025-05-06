package br.ufal.ic.p2.jackut.exceptions.community;

/**
 * Exceção personalizada que indica que não há mensagens disponíveis.
 * Esta classe estende {@link Exception} e é lançada quando uma operação
 * relacionada à obtenção de mensagens não encontra nenhuma mensagem.
 */

public class ThereAreNoMessagesException extends Exception {

    public ThereAreNoMessagesException() {
        super("Não há mensagens.");
    }
}
