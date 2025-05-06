package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * A classe {@code Message} representa uma mensagem simples contendo um texto.
 * Esta classe implementa a interface {@link Serializable}, permitindo que instâncias
 * da classe possam ser serializadas para serem armazenadas ou transmitidas.
 */

public class Message implements Serializable {
    /**
     * A mensagem armazenada nesta instância.
     */
    private String message;

    /**
     * Constrói uma nova instância de {@code Message} com o texto fornecido.
     *
     * @param message o texto da mensagem
     */

    public Message(String message) {
        this.message = message;
    }

    /**
     * Retorna o texto da mensagem armazenada nesta instância.
     *
     * @return o texto da mensagem
     */

    public String getMessage() {
        return message;
    }
}
