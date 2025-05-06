package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que indica que uma comunidade com o mesmo nome
 * já existe. Esta classe estende {@link Exception} e é lançada quando
 * ocorre uma tentativa de criação ou cadastro de uma comunidade com um
 * nome que já foi utilizado.
 */

public class CommunityAlreadyExistsException extends Exception {


    public CommunityAlreadyExistsException() {
        super("Comunidade com esse nome já existe.");
    }
}
