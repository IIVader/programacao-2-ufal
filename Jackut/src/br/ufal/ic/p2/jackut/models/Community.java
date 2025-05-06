package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.utils.UtilsString;

import java.io.Serializable;
import java.util.*;

/**
 * A classe {@code Community} representa uma comunidade, que possui um nome, uma descrição,
 * um proprietário e uma lista de membros. A comunidade pode ser gerenciada e expandida ao
 * adicionar novos membros à lista de participantes.
 */

public class Community implements Serializable {
    /**
     * O nome da comunidade.
     */
    private String name;

    /**
     * A descrição da comunidade.
     */
    private String description;

    /**
     * A conta do usuário que é o proprietário da comunidade.
     */
    private UserAccount owner;

    /**
     * A lista de contas de usuários que são membros da comunidade.
     */
    private ArrayList<UserAccount> membersList;

    /**
     * Constrói uma nova instância de {@code Community} com o nome, descrição e proprietário fornecidos.
     * Inicializa a lista de membros com o proprietário da comunidade.
     *
     * @param name        o nome da comunidade
     * @param description a descrição da comunidade
     * @param owner       o proprietário da comunidade
     */
    public Community(String name, String description, UserAccount owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.membersList = new ArrayList<>();
        this.membersList.add(owner);
    }


    /**
     * Retorna o nome da comunidade.
     *
     * @return o nome da comunidade
     */
    public String getName() {
        return name;
    }


    /**
     * Retorna a descrição da comunidade.
     *
     * @return a descrição da comunidade
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna o proprietário da comunidade.
     *
     * @return o proprietário da comunidade
     */
    public UserAccount getOwner() {
        return owner;
    }


    /**
     * Retorna uma representação em forma de string dos membros da comunidade formatados.
     * Utiliza o método {@link UtilsString#formatArrayList(ArrayList)} para formatar a lista de membros.
     *
     * @return uma string formatada com os membros da comunidade
     */
    public String getMembersString() {
        return UtilsString.formatArrayList(this.membersList);
    }

    /**
     * Retorna a lista de membros da comunidade.
     *
     * @return a lista de membros da comunidade
     */
    public ArrayList<UserAccount> getMembersList() {
        return membersList;
    }


    /**
     * Adiciona um novo membro à lista de membros da comunidade.
     *
     * @param member a conta de usuário a ser adicionada como membro da comunidade
     */
    public void setMembersList(UserAccount member) {
        this.membersList.add(member);
    }
}
