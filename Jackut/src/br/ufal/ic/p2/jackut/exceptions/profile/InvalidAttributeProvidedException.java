package br.ufal.ic.p2.jackut.exceptions.profile;

/**
 * Exce��o personalizada que � lan�ada quando um atributo inv�lido ou n�o preenchido � fornecido.
 * Esta exce��o estende a classe {@link Exception}.
 */

public class InvalidAttributeProvidedException extends Exception {

    public InvalidAttributeProvidedException() {
        super("Atributo n�o preenchido.");
    }
}
