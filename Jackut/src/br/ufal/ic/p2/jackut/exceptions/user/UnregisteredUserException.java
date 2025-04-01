package br.ufal.ic.p2.jackut.exceptions.user;

/**
 * Exceção personalizada para indicar que o usuário não está registrado.
 * <p>
 * Esta exceção é lançada quando uma operação é tentada com um usuário que
 * não está cadastrado no sistema.
 * </p>
 */

public class UnregisteredUserException extends Exception {
    public UnregisteredUserException() {
        super("Usuário não cadastrado.");
    }
}
