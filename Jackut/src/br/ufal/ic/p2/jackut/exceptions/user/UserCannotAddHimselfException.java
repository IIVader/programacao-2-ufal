package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada para indicar que o usuário não pode adicionar a si mesmo como amigo.
 * <p>
 * Esta exceção é lançada quando um usuário tenta enviar um convite de amizade para si mesmo,
 * o que não é permitido pelo sistema.
 * </p>
 */

public class UserCannotAddHimselfException extends Exception {
    public UserCannotAddHimselfException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
