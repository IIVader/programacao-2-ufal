package br.ufal.ic.p2.jackut.exceptions.note;

/**
 * Exceção personalizada para indicar que não há recados disponíveis.
 * <p>
 * Esta exceção é lançada quando uma tentativa de acessar recados é feita,
 * mas não há recados disponíveis ou cadastrados.
 * </p>
 */

public class ThereAreNoNotesException extends Exception {

    public ThereAreNoNotesException() {
        super("Não há recados.");
    }
}
