package br.ufal.ic.p2.jackut.exceptions.community;

/**
 * Exce��o personalizada que indica que n�o h� mensagens dispon�veis.
 * Esta classe estende {@link Exception} e � lan�ada quando uma opera��o
 * relacionada � obten��o de mensagens n�o encontra nenhuma mensagem.
 */

public class ThereAreNoMessagesException extends Exception {

    public ThereAreNoMessagesException() {
        super("N�o h� mensagens.");
    }
}
