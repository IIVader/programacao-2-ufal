package br.ufal.ic.p2.jackut.exceptions.profile;

/**
 * Exceção personalizada que é lançada quando um atributo inválido ou não preenchido é fornecido.
 * Esta exceção estende a classe {@link Exception}.
 */

public class InvalidAttributeProvidedException extends Exception {

    public InvalidAttributeProvidedException() {
        super("Atributo não preenchido.");
    }
}
