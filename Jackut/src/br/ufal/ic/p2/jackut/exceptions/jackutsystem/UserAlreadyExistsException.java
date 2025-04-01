package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada para indicar que já existe uma conta com o login de usuário fornecido.
 * <p>
 * Esta exceção é lançada quando uma tentativa de registrar um novo usuário é feita,
 * mas já existe uma conta com o mesmo login de usuário no sistema.
 * </p>
 */

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("Conta com esse nome já existe.");
    }
}
