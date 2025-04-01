package br.ufal.ic.p2.jackut.exceptions.note;

/**
 * Exce��o personalizada para indicar que n�o h� recados dispon�veis.
 * <p>
 * Esta exce��o � lan�ada quando uma tentativa de acessar recados � feita,
 * mas n�o h� recados dispon�veis ou cadastrados.
 * </p>
 */

public class ThereAreNoNotesException extends Exception {

    public ThereAreNoNotesException() {
        super("N�o h� recados.");
    }
}
