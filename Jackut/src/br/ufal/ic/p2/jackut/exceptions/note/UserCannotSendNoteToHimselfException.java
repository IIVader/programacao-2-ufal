package br.ufal.ic.p2.jackut.exceptions.note;

/**
 * Exceção personalizada para indicar que o usuário não pode enviar um recado para si mesmo.
 * <p>
 * Esta exceção é lançada quando um usuário tenta enviar um recado para si mesmo,
 * o que não é permitido pelo sistema.
 * </p>
 */

public class UserCannotSendNoteToHimselfException extends Exception {
    public UserCannotSendNoteToHimselfException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
