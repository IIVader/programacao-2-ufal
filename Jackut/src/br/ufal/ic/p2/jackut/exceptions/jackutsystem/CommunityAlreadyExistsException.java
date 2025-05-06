package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que indica que uma comunidade com o mesmo nome
 * j� existe. Esta classe estende {@link Exception} e � lan�ada quando
 * ocorre uma tentativa de cria��o ou cadastro de uma comunidade com um
 * nome que j� foi utilizado.
 */

public class CommunityAlreadyExistsException extends Exception {


    public CommunityAlreadyExistsException() {
        super("Comunidade com esse nome j� existe.");
    }
}
