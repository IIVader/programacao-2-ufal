package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que indica que o usuário já faz parte da comunidade.
 * Esta classe estende {@link Exception} e é lançada quando há uma tentativa
 * de adicionar um usuário a uma comunidade à qual ele já pertence.
 */

public class UserIsAlreadyInThisCommunityException extends Exception {

    /**
     * Construtor padrão que cria uma nova instância da exceção com a
     * mensagem padrão "Usuário já faz parte dessa comunidade."
     */
    public UserIsAlreadyInThisCommunityException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
