package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Representa um recado (Note) enviada entre usu�rios no sistema Jackut.
 * Cada mensagem cont�m um remetente, um destinat�rio e o conte�do da mensagem.
 *
 * <p>Esta classe implementa {@code Serializable} para permitir a serializa��o dos objetos.</p>
 */

public class Note implements Serializable {

    /**
     * Nome do remetente da mensagem.
     */
    private String sender;

    /**
     * Nome do destinat�rio da mensagem.
     */
    private String receiver;

    /**
     * Conte�do da mensagem.
     */
    private String message;

    /**
     * Construtor para criar uma nova nota.
     *
     * @param sender   Nome do remetente da mensagem.
     * @param receiver Nome do destinat�rio da mensagem.
     * @param message  Conte�do da mensagem.
     */

    public Note(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Obt�m o nome do remetente da mensagem.
     *
     * @return Nome do remetente.
     */

    public String getSender() {
        return sender;
    }

    /**
     * Obt�m o nome do destinat�rio da mensagem.
     *
     * @return Nome do destinat�rio.
     */

    public String getReceiver() {
        return receiver;
    }

    /**
     * Obt�m o conte�do da mensagem.
     *
     * @return Conte�do da mensagem.
     */

    public String getMessage() {
        return message;
    }
}
