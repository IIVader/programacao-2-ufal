package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa um perfil de usuário contendo atributos personalizados.
 * Cada atributo é armazenado em um mapa de chave-valor.
 *
 * <p>Esta classe implementa {@link Serializable}, permitindo que os perfis
 * sejam salvos e recuperados de forma persistente.</p>
 */

public class Profile implements Serializable {

    /**
     * Mapa que armazena os atributos do perfil como pares chave-valor.
     */
    private Map<String, String> attributesMap;

    /**
     * Construtor padrão que inicializa o mapa de atributos.
     */

    public Profile() {
        this.attributesMap = new HashMap<>();
    }

    /**
     * Obtém o mapa de atributos do perfil.
     *
     * @return Um {@link Map} contendo os atributos do perfil.
     */

    public Map<String, String> getAttributesMap() {
        return attributesMap;
    }

    /**
     * Adiciona ou atualiza um atributo no perfil.
     *
     * @param attribute A chave do atributo a ser adicionado/atualizado.
     * @param value     O valor correspondente ao atributo.
     */

    public void setAttributesMap(String attribute, String value) {
        this.attributesMap.put(attribute, value);
    }
}
