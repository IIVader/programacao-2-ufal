package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.jackutsystem.InvalidLoginOrPasswordException;
import br.ufal.ic.p2.jackut.exceptions.jackutsystem.LoginInvalidException;
import br.ufal.ic.p2.jackut.exceptions.jackutsystem.PasswordInvalidException;
import br.ufal.ic.p2.jackut.exceptions.jackutsystem.UserAlreadyExistsException;
import br.ufal.ic.p2.jackut.exceptions.note.ThereAreNoNotesException;
import br.ufal.ic.p2.jackut.exceptions.note.UserCannotSendNoteToHimselfException;
import br.ufal.ic.p2.jackut.exceptions.profile.InvalidAttributeProvidedException;
import br.ufal.ic.p2.jackut.exceptions.user.RequestAlreadySendedException;
import br.ufal.ic.p2.jackut.exceptions.user.UnregisteredUserException;
import br.ufal.ic.p2.jackut.exceptions.user.UserAlreadyIsFriendException;
import br.ufal.ic.p2.jackut.exceptions.user.UserCannotAddHimselfException;
import br.ufal.ic.p2.jackut.models.Note;
import br.ufal.ic.p2.jackut.models.Profile;
import br.ufal.ic.p2.jackut.models.UserAccount;
import br.ufal.ic.p2.jackut.utils.Serealization;

import java.util.*;

/**
 * Classe principal do sistema Jackut, respons�vel por gerenciar usu�rios, sess�es, amizades e recados.
 */

public class JackutSystem {
    private HashMap<String, UserAccount> usersMap;
    private Map<String, UserAccount> activeSessions = new HashMap<>();
    private final Serealization serealization = new Serealization();

    /**
     * Construtor da classe JackutSystem. Inicializa o mapa de usu�rios e carrega os dados do sistema.
     */

    public JackutSystem() {
        this.usersMap = new HashMap<>();
        readData();
    }

    /**
     * Obt�m o mapa de usu�rios cadastrados no sistema.
     *
     * @return Um mapa contendo os usu�rios cadastrados.
     */

    public Map<String, UserAccount> getUsersMap() {
        return usersMap;
    }

    /**
     * Adiciona um usu�rio ao mapa de usu�rios.
     *
     * @param login       O login do usu�rio.
     * @param userAccount O objeto UserAccount do usu�rio.
     */

    public void setUsersMap(String login, UserAccount userAccount) {
        this.usersMap.put(login, userAccount);
    }

    /**
     * Cria um novo usu�rio no sistema.
     *
     * @param login    O login do usu�rio.
     * @param password A senha do usu�rio.
     * @param userName O nome do usu�rio.
     * @return true se o usu�rio for criado com sucesso.
     * @throws UserAlreadyExistsException Se o login j� estiver cadastrado.
     * @throws LoginInvalidException      Se o login for inv�lido.
     * @throws PasswordInvalidException   Se a senha for inv�lida.
     */

    public boolean createUser(String login, String password, String userName) throws UserAlreadyExistsException, LoginInvalidException, PasswordInvalidException {
        if (login == null) throw new LoginInvalidException();
        if (password == null) throw new PasswordInvalidException();

        if (getUsersMap().containsKey(login)) {
            throw new UserAlreadyExistsException();
        } else {
            UserAccount newUser = new UserAccount(login, password, userName);

            setUsersMap(login, newUser);

            return true;
        }
    }

    /**
     * Obt�m um atributo espec�fico de um usu�rio.
     *
     * @param login     O login do usu�rio.
     * @param attribute O atributo a ser consultado.
     * @return O valor do atributo solicitado.
     * @throws InvalidAttributeProvidedException Se o atributo n�o for encontrado.
     * @throws UnregisteredUserException         Se o usu�rio n�o estiver cadastrado.
     */

    public String getUserAttribute(String login, String attribute) throws InvalidAttributeProvidedException, UnregisteredUserException {

        if (!getUsersMap().containsKey(login)) {
            throw new UnregisteredUserException();
        }

        UserAccount user = getUsersMap().get(login);

        switch (attribute) {
            case "login":
                return user.getLogin();
            case "senha":
                return user.getPassword();
            case "nome":
                return user.getUserName();
            default:
                String profileAttribute = user.getProfile().getAttributesMap().get(attribute);
                if (profileAttribute == null) {
                    throw new InvalidAttributeProvidedException();
                }
                return profileAttribute;
        }
    }

    /**
     * Abre uma sess�o para um usu�rio autenticado.
     *
     * @param login    O login do usu�rio.
     * @param password A senha do usu�rio.
     * @return O identificador �nico da sess�o aberta.
     * @throws InvalidLoginOrPasswordException Se o login ou senha estiverem incorretos.
     */

    public String openSession(String login, String password) throws InvalidLoginOrPasswordException {
        if (!usersMap.containsKey(login) || !Objects.equals(usersMap.get(login).getPassword(), password)) {
            throw new InvalidLoginOrPasswordException();
        }

        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, usersMap.get(login));
        return sessionId;
    }

    /**
     * Obt�m um usu�rio a partir de uma sess�o ativa.
     *
     * @param sessionId O identificador da sess�o.
     * @return O objeto UserAccount correspondente � sess�o.
     * @throws UnregisteredUserException Se a sess�o n�o estiver registrada.
     */

    public UserAccount getUserFromSession(String sessionId) throws UnregisteredUserException {
        if (!activeSessions.containsKey(sessionId)) {
            throw new UnregisteredUserException();
        }
        return activeSessions.get(sessionId);
    }

    /**
     * Edita o perfil de um usu�rio autenticado.
     *
     * @param id        O identificador da sess�o do usu�rio.
     * @param attribute O atributo do perfil a ser editado.
     * @param value     O novo valor do atributo.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver cadastrado.
     */

    public void editProfile(String id, String attribute, String value) throws UnregisteredUserException {
        UserAccount userAccount = getUserFromSession(id);

        if (!getUsersMap().containsKey(userAccount.getLogin())) {
            throw new UnregisteredUserException();
        }

        UserAccount user = getUsersMap().get(userAccount.getLogin());
        Profile profile = user.getProfile();

        profile.setAttributesMap(attribute, value);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login      O login do usu�rio.
     * @param loginAmigo O login do poss�vel amigo.
     * @return true se os usu�rios forem amigos, false caso contr�rio.
     * @throws UnregisteredUserException Se algum dos usu�rios n�o estiver cadastrado.
     */

    public boolean isFriend(String login, String loginAmigo) throws UnregisteredUserException {
        if (!(getUsersMap().containsKey(login) && getUsersMap().containsKey(loginAmigo))) {
            throw new UnregisteredUserException();
        }

        UserAccount userAccount = usersMap.get(login);
        UserAccount friendUserAcount = usersMap.get(loginAmigo);

        return userAccount.getFriendList().contains(friendUserAcount) && friendUserAcount.getFriendList().contains(userAccount);
    }

    /**
     * Adiciona um amigo � lista de amigos de um usu�rio autenticado.
     *
     * @param id    O identificador da sess�o do usu�rio.
     * @param amigo O login do usu�rio a ser adicionado como amigo.
     * @throws UnregisteredUserException     Se o usu�rio ou o amigo n�o estiver cadastrado.
     * @throws RequestAlreadySendedException Se o pedido de amizade j� foi enviado.
     * @throws UserAlreadyIsFriendException  Se os usu�rios j� forem amigos.
     * @throws UserCannotAddHimselfException Se o usu�rio tentar adicionar a si mesmo.
     */

    public void addFriend(String id, String amigo) throws UnregisteredUserException, RequestAlreadySendedException, UserAlreadyIsFriendException, UserCannotAddHimselfException {
        if (!(activeSessions.containsKey(id) && usersMap.containsKey(amigo))) {
            throw new UnregisteredUserException();
        }

        UserAccount userAccount = activeSessions.get(id);
        UserAccount friendUserAccount = usersMap.get(amigo);

        if (userAccount.getLogin().equals(friendUserAccount.getLogin())) {
            throw new UserCannotAddHimselfException();
        }

        if (userAccount.getFriendList().contains(friendUserAccount) && friendUserAccount.getFriendList().contains(userAccount)) {
            throw new UserAlreadyIsFriendException();
        }

        if (userAccount.getFriendsRequestsSent().contains(friendUserAccount)) {
            throw new RequestAlreadySendedException();
        } else if (userAccount.getFriendsRequestsReceived().contains(friendUserAccount)) {
            userAccount.acceptRequest(friendUserAccount);
        } else {
            userAccount.sendRequest(friendUserAccount);
        }
    }

    /**
     * Obt�m a lista de amigos de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return Uma string contendo a lista de amigos.
     */

    public String getFriends(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFriendsString();
    }

    public void SendNote(String id, String receiver, String note) throws UnregisteredUserException, UserCannotSendNoteToHimselfException {
        if (!(activeSessions.containsKey(id) && usersMap.containsKey(receiver))) {
            throw new UnregisteredUserException();
        }

        if (activeSessions.get(id).getLogin().equals(receiver)) {
            throw new UserCannotSendNoteToHimselfException();
        }

        Note newNote = new Note(id, receiver, note);

        usersMap.get(receiver).setNotesQueue(newNote);
    }

    public String readNote(String id) throws UnregisteredUserException, ThereAreNoNotesException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if (activeSessions.get(id).getNotesQueue().isEmpty()) {
            throw new ThereAreNoNotesException();
        }

        Note note = activeSessions.get(id).getNotesQueue().poll();

        assert note != null;
        return note.getMessage();
    }

    /**
     * Salva os dados dos usu�rios no sistema.
     */

    public void saveData() {
        Serealization.serealizeObject(usersMap, "usersAccount");
    }

    /**
     * Remove todos os dados do sistema, incluindo usu�rios, amigos e recados.
     */

    public void clearData() {
        usersMap.clear();
    }

    /**
     * L� os dados dos usu�rios do armazenamento persistente.
     */

    public void readData() {
        this.usersMap = Serealization.deserializeObject("usersAccount");
    }

    /**
     * Fecha o sistema, salvando, limpando e recarregando os dados.
     */

    public void closeSystem() {
        saveData();
        clearData();
        readData();
    }
}
