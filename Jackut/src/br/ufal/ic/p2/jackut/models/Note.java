package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Representa um recado (Note) enviada entre usuários no sistema Jackut.
 * Cada mensagem contém um remetente, um destinatário e o conteúdo da mensagem.
 *
 * <p>Esta classe implementa {@code Serializable} para permitir a serialização dos objetos.</p>
 */

public class Note implements Serializable {

    /**
     * Nome do remetente da mensagem.
     */
    private String sender;

    /**
     * Nome do destinatário da mensagem.
     */
    private String receiver;

    /**
     * Conteúdo da mensagem.
     */
    private String message;

    /**
     * Construtor para criar uma nova nota.
     *
     * @param sender   Nome do remetente da mensagem.
     * @param receiver Nome do destinatário da mensagem.
     * @param message  Conteúdo da mensagem.
     */

    public Note(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Obtém o nome do remetente da mensagem.
     *
     * @return Nome do remetente.
     */

    public String getSender() {
        return sender;
    }

    /**
     * Obtém o nome do destinatário da mensagem.
     *
     * @return Nome do destinatário.
     */

    public String getReceiver() {
        return receiver;
    }

    /**
     * Obtém o conteúdo da mensagem.
     *
     * @return Conteúdo da mensagem.
     */

    public String getMessage() {
        return message;
    }
}
