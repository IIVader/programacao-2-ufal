package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.utils.UtilsString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Representa uma conta de usuário no sistema Jackut.
 * Cada conta possui um login, senha, nome de usuário e um perfil associado.
 * Além disso, permite gerenciamento de amizades e troca de notas.
 * <p>
 * implementa {@link Serializable} para permitir a serialização do objeto.
 */

public class UserAccount implements Serializable {
    private String login;
    private String password;
    private String userName;

    private Profile profile = new Profile();

    private ArrayList<UserAccount> friendList = new ArrayList<>();
    private ArrayList<UserAccount> friendsRequestsSent = new ArrayList<>();
    private ArrayList<UserAccount> friendsRequestsReceived = new ArrayList<>();

    private Queue<Note> notesQueue = new LinkedList<>();
    private Queue<Message> messagesQueue = new LinkedList<>();

    private ArrayList<String> peopleISentNotesTo = new ArrayList<>();

    private ArrayList<String> communityList = new ArrayList<>();

    private ArrayList<String> fansList = new ArrayList<>();
    private ArrayList<String> crushsList = new ArrayList<>();
    private ArrayList<String> enemysList = new ArrayList<>();

    /**
     * Constrói uma nova conta de usuário.
     *
     * @param login    O login do usuário.
     * @param password A senha do usuário.
     * @param userName O nome de usuário.
     */

    public UserAccount(String login, String password, String userName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
    }

    /**
     * @return O login do usuário.
     */

    public String getLogin() {
        return login;
    }

    /**
     * @return A senha do usuário.
     */

    public String getPassword() {
        return password;
    }

    /**
     * @return O nome de usuário.
     */

    public String getUserName() {
        return userName;
    }

    /**
     * @return O perfil do usuário.
     */

    public Profile getProfile() {
        return profile;
    }

    /**
     * @return Lista de amigos do usuário.
     */

    public List<UserAccount> getFriendList() {
        return friendList;
    }

    /**
     * @return Lista formatada de amigos do usuário.
     */

    public String getFriendsString() {
        return UtilsString.formatArrayList(this.friendList);
    }

    /**
     * Adiciona um amigo à lista de amigos do usuário.
     *
     * @param friend O usuário a ser adicionado como amigo.
     */

    public void setFriendList(UserAccount friend) {
        this.friendList.add(friend);
    }

    /**
     * Envia uma solicitação de amizade para outro usuário.
     *
     * @param userAccount O usuário para quem a solicitação será enviada.
     */

    public void sendRequest(UserAccount userAccount) {
        this.friendsRequestsSent.add(userAccount);
        userAccount.friendsRequestsReceived.add(this);
    }

    /**
     * Aceita uma solicitação de amizade recebida.
     *
     * @param userAccount O usuário cuja solicitação será aceita.
     */

    public void acceptRequest(UserAccount userAccount) {
        this.friendList.add(userAccount);
        this.friendsRequestsReceived.remove(userAccount);
        userAccount.friendList.add(this);
        userAccount.friendsRequestsSent.remove(this);
    }

    /**
     * @return Lista de solicitações de amizade enviadas.
     */

    public ArrayList<UserAccount> getFriendsRequestsSent() {
        return friendsRequestsSent;
    }

    /**
     * @return Lista de solicitações de amizade recebidas.
     */

    public ArrayList<UserAccount> getFriendsRequestsReceived() {
        return friendsRequestsReceived;
    }

    /**
     * @return Fila de notas do usuário.
     */

    public Queue<Note> getNotesQueue() {
        return notesQueue;
    }

    /**
     * Adiciona uma nota à fila de notas do usuário.
     *
     * @param note A nota a ser adicionada.
     */

    public void setNotesQueue(Note note) {
        this.notesQueue.add(note);
    }

    public ArrayList<String> getCommunityList() {
        return communityList;
    }

    public void setCommunityList(String communityName) {
        this.communityList.add(communityName);
    }

    public Queue<Message> getMessagesQueue() {
        return messagesQueue;
    }

    public void setMessagesQueue(Message message) {
        this.messagesQueue.add(message);
    }

    public ArrayList<String> getFansList() {
        return fansList;
    }

    public void setFansList(String fanName) {
        this.fansList.add(fanName);
    }

    public String getFansString() {
        return UtilsString.formatArrayList(this.fansList);
    }

    public ArrayList<String> getCrushsList() {
        return crushsList;
    }

    public void setCrushsList(String crushName) {
        this.crushsList.add(crushName);
    }

    public String getCrushsString() {
        return UtilsString.formatArrayList(this.crushsList);
    }

    public ArrayList<String> getEnemysList() {
        return enemysList;
    }

    public void setEnemysList(String enemyName) {
        this.enemysList.add(enemyName);
    }

    public String getEnemysString() {
        return UtilsString.formatArrayList(this.enemysList);
    }

    public ArrayList<String> getPeopleISentNotesTo() {
        return peopleISentNotesTo;
    }

    public void setPeopleISentNotesTo(String userName) {
        this.peopleISentNotesTo.add(userName);
    }

    /**
     * Representação em String do usuário, baseada no login.
     *
     * @return O login do usuário.
     */

    @Override
    public String toString() {
        return this.getLogin();
    }
}
