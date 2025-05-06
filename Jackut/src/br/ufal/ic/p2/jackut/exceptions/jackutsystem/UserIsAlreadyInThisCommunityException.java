package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que indica que o usu�rio j� faz parte da comunidade.
 * Esta classe estende {@link Exception} e � lan�ada quando h� uma tentativa
 * de adicionar um usu�rio a uma comunidade � qual ele j� pertence.
 */

public class UserIsAlreadyInThisCommunityException extends Exception {

    /**
     * Construtor padr�o que cria uma nova inst�ncia da exce��o com a
     * mensagem padr�o "Usu�rio j� faz parte dessa comunidade."
     */
    public UserIsAlreadyInThisCommunityException() {
        super("Usuario j� faz parte dessa comunidade.");
    }
}
