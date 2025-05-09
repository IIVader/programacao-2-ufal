package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.utils.UtilsString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Representa uma conta de usu�rio no sistema Jackut.
 * Cada conta possui um login, senha, nome de usu�rio e um perfil associado.
 * Al�m disso, permite gerenciamento de amizades e troca de notas.
 * <p>
 * implementa {@link Serializable} para permitir a serializa��o do objeto.
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
     * Constr�i uma nova conta de usu�rio.
     *
     * @param login    O login do usu�rio.
     * @param password A senha do usu�rio.
     * @param userName O nome de usu�rio.
     */

    public UserAccount(String login, String password, String userName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
    }

    /**
     * @return O login do usu�rio.
     */

    public String getLogin() {
        return login;
    }

    /**
     * @return A senha do usu�rio.
     */

    public String getPassword() {
        return password;
    }

    /**
     * @return O nome de usu�rio.
     */

    public String getUserName() {
        return userName;
    }

    /**
     * @return O perfil do usu�rio.
     */

    public Profile getProfile() {
        return profile;
    }

    /**
     * @return Lista de amigos do usu�rio.
     */

    public List<UserAccount> getFriendList() {
        return friendList;
    }

    /**
     * @return Lista formatada de amigos do usu�rio.
     */

    public String getFriendsString() {
        return UtilsString.formatArrayList(this.friendList);
    }

    /**
     * Adiciona um amigo � lista de amigos do usu�rio.
     *
     * @param friend O usu�rio a ser adicionado como amigo.
     */

    public void setFriendList(UserAccount friend) {
        this.friendList.add(friend);
    }

    /**
     * Envia uma solicita��o de amizade para outro usu�rio.
     *
     * @param userAccount O usu�rio para quem a solicita��o ser� enviada.
     */

    public void sendRequest(UserAccount userAccount) {
        this.friendsRequestsSent.add(userAccount);
        userAccount.friendsRequestsReceived.add(this);
    }

    /**
     * Aceita uma solicita��o de amizade recebida.
     *
     * @param userAccount O usu�rio cuja solicita��o ser� aceita.
     */

    public void acceptRequest(UserAccount userAccount) {
        this.friendList.add(userAccount);
        this.friendsRequestsReceived.remove(userAccount);
        userAccount.friendList.add(this);
        userAccount.friendsRequestsSent.remove(this);
    }

    /**
     * @return Lista de solicita��es de amizade enviadas.
     */

    public ArrayList<UserAccount> getFriendsRequestsSent() {
        return friendsRequestsSent;
    }

    /**
     * @return Lista de solicita��es de amizade recebidas.
     */

    public ArrayList<UserAccount> getFriendsRequestsReceived() {
        return friendsRequestsReceived;
    }

    /**
     * @return Fila de notas do usu�rio.
     */

    public Queue<Note> getNotesQueue() {
        return notesQueue;
    }

    /**
     * Adiciona uma nota � fila de notas do usu�rio.
     *
     * @param note A nota a ser adicionada.
     */

    public void setNotesQueue(Note note) {
        this.notesQueue.add(note);
    }

    /**
     * Retorna a lista de comunidades associadas.
     *
     * @return a lista de nomes das comunidades
     */

    public ArrayList<String> getCommunityList() {
        return communityList;
    }

    /**
     * Adiciona o nome de uma comunidade � lista de comunidades.
     *
     * @param communityName o nome da comunidade a ser adicionada
     */

    public void setCommunityList(String communityName) {
        this.communityList.add(communityName);
    }


    /**
     * Retorna a fila de mensagens.
     *
     * @return a fila de mensagens
     */

    public Queue<Message> getMessagesQueue() {
        return messagesQueue;
    }

    /**
     * Adiciona uma mensagem � fila de mensagens.
     *
     * @param message a mensagem a ser adicionada � fila
     */

    public void setMessagesQueue(Message message) {
        this.messagesQueue.add(message);
    }

    /**
     * Retorna a lista de f�s.
     *
     * @return a lista de nomes dos f�s
     */

    public ArrayList<String> getFansList() {
        return fansList;
    }

    /**
     * Adiciona o nome de um f� � lista de f�s.
     *
     * @param fanName o nome do f� a ser adicionado
     */

    public void setFansList(String fanName) {
        this.fansList.add(fanName);
    }

    /**
     * Retorna uma representa��o em forma de string dos f�s, formatados.
     * Utiliza o m�todo {@link UtilsString#formatArrayList(ArrayList)} para formatar a lista de f�s.
     *
     * @return uma string formatada com os f�s
     */

    public String getFansString() {
        return UtilsString.formatArrayList(this.fansList);
    }

    /**
     * Retorna a lista de crushes.
     *
     * @return a lista de nomes dos crushes
     */

    public ArrayList<String> getCrushsList() {
        return crushsList;
    }

    /**
     * Adiciona o nome de um crush � lista de crushes.
     *
     * @param crushName o nome do crush a ser adicionado
     */

    public void setCrushsList(String crushName) {
        this.crushsList.add(crushName);
    }

    /**
     * Retorna uma representa��o em forma de string dos crushes, formatados.
     * Utiliza o m�todo {@link UtilsString#formatArrayList(ArrayList)} para formatar a lista de crushes.
     *
     * @return uma string formatada com os crushes
     */

    public String getCrushsString() {
        return UtilsString.formatArrayList(this.crushsList);
    }

    /**
     * Retorna a lista de inimigos.
     *
     * @return a lista de nomes dos inimigos
     */

    public ArrayList<String> getEnemysList() {
        return enemysList;
    }

    /**
     * Adiciona o nome de um inimigo � lista de inimigos.
     *
     * @param enemyName o nome do inimigo a ser adicionado
     */

    public void setEnemysList(String enemyName) {
        this.enemysList.add(enemyName);
    }

    /**
     * Retorna uma representa��o em forma de string dos inimigos, formatados.
     * Utiliza o m�todo {@link UtilsString#formatArrayList(ArrayList)} para formatar a lista de inimigos.
     *
     * @return uma string formatada com os inimigos
     */

    public String getEnemysString() {
        return UtilsString.formatArrayList(this.enemysList);
    }


    /**
     * Retorna a lista de pessoas para as quais foram enviadas notas.
     *
     * @return a lista de nomes das pessoas que receberam notas
     */

    public ArrayList<String> getPeopleISentNotesTo() {
        return peopleISentNotesTo;
    }

    /**
     * Adiciona o nome de uma pessoa � lista de pessoas para as quais foram enviadas notas.
     *
     * @param userName o nome da pessoa que recebeu a nota
     */

    public void setPeopleISentNotesTo(String userName) {
        this.peopleISentNotesTo.add(userName);
    }

    /**
     * Representa��o em String do usu�rio, baseada no login.
     *
     * @return O login do usu�rio.
     */

    @Override
    public String toString() {
        return this.getLogin();
    }
}
