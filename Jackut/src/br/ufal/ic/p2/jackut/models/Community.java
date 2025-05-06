package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.utils.UtilsString;

import java.io.Serializable;
import java.util.*;

/**
 * A classe {@code Community} representa uma comunidade, que possui um nome, uma descri��o,
 * um propriet�rio e uma lista de membros. A comunidade pode ser gerenciada e expandida ao
 * adicionar novos membros � lista de participantes.
 */

public class Community implements Serializable {
    /**
     * O nome da comunidade.
     */
    private String name;

    /**
     * A descri��o da comunidade.
     */
    private String description;

    /**
     * A conta do usu�rio que � o propriet�rio da comunidade.
     */
    private UserAccount owner;

    /**
     * A lista de contas de usu�rios que s�o membros da comunidade.
     */
    private ArrayList<UserAccount> membersList;

    /**
     * Constr�i uma nova inst�ncia de {@code Community} com o nome, descri��o e propriet�rio fornecidos.
     * Inicializa a lista de membros com o propriet�rio da comunidade.
     *
     * @param name        o nome da comunidade
     * @param description a descri��o da comunidade
     * @param owner       o propriet�rio da comunidade
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
     * Retorna a descri��o da comunidade.
     *
     * @return a descri��o da comunidade
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna o propriet�rio da comunidade.
     *
     * @return o propriet�rio da comunidade
     */
    public UserAccount getOwner() {
        return owner;
    }


    /**
     * Retorna uma representa��o em forma de string dos membros da comunidade formatados.
     * Utiliza o m�todo {@link UtilsString#formatArrayList(ArrayList)} para formatar a lista de membros.
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
     * Adiciona um novo membro � lista de membros da comunidade.
     *
     * @param member a conta de usu�rio a ser adicionada como membro da comunidade
     */
    public void setMembersList(UserAccount member) {
        this.membersList.add(member);
    }
}
