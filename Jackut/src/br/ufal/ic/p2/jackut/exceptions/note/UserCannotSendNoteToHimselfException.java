package br.ufal.ic.p2.jackut.exceptions.note;

/**
 * Exce��o personalizada para indicar que o usu�rio n�o pode enviar um recado para si mesmo.
 * <p>
 * Esta exce��o � lan�ada quando um usu�rio tenta enviar um recado para si mesmo,
 * o que n�o � permitido pelo sistema.
 * </p>
 */

public class UserCannotSendNoteToHimselfException extends Exception {
    public UserCannotSendNoteToHimselfException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}
