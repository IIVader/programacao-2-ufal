package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.utils.UtilsString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Community implements Serializable {
    private String name;
    private String description;
    private UserAccount owner;
    private ArrayList<UserAccount> membersList;

    public Community(String name, String description, UserAccount owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.membersList = new ArrayList<>();
        this.membersList.add(owner);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public String getMembersString() {
        return UtilsString.formatArrayList(this.membersList);
    }

    public void setMembersList(UserAccount member) {
        this.membersList.add(member);
    }
}
