package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exce��o personalizada para indicar que um pedido de amizade j� foi enviado.
 * <p>
 * Esta exce��o � lan�ada quando um usu�rio tenta enviar um convite de amizade
 * para outro usu�rio que j� foi adicionado como amigo, estando aguardando
 * a aceita��o do convite.
 * </p>
 */

public class RequestAlreadySendedException extends Exception {

    public RequestAlreadySendedException() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}
