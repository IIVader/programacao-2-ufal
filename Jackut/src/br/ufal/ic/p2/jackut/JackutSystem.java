package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.jackutsystem.*;
import br.ufal.ic.p2.jackut.exceptions.note.ThereAreNoNotesException;
import br.ufal.ic.p2.jackut.exceptions.note.UserCannotSendNoteToHimselfException;
import br.ufal.ic.p2.jackut.exceptions.profile.InvalidAttributeProvidedException;
import br.ufal.ic.p2.jackut.exceptions.user.*;
import br.ufal.ic.p2.jackut.exceptions.community.ThereAreNoMessagesException;
import br.ufal.ic.p2.jackut.models.*;
import br.ufal.ic.p2.jackut.utils.Serealization;
import br.ufal.ic.p2.jackut.utils.UtilsString;

import java.util.*;

/**
 * Classe principal do sistema Jackut, responsável por gerenciar usuários, sessões, amizades e recados.
 */

public class JackutSystem {
    private HashMap<String, UserAccount> usersMap;
    private HashMap<String, Community> communityMap;
    private Map<String, UserAccount> activeSessions = new HashMap<>();
    private final Serealization serealization = new Serealization();

    /**
     * Construtor da classe JackutSystem. Inicializa o mapa de usuários e carrega os dados do sistema.
     */

    public JackutSystem() {
        this.usersMap = new HashMap<>();
        this.communityMap = new HashMap<>();
        readData();
    }

    /**
     * Obtém o mapa de usuários cadastrados no sistema.
     *
     * @return Um mapa contendo os usuários cadastrados.
     */

    public Map<String, UserAccount> getUsersMap() {
        return usersMap;
    }

    /**
     * Adiciona um usuário ao mapa de usuários.
     *
     * @param login       O login do usuário.
     * @param userAccount O objeto UserAccount do usuário.
     */

    public void setUsersMap(String login, UserAccount userAccount) {
        this.usersMap.put(login, userAccount);
    }

    /**
     * Cria um novo usuário no sistema.
     *
     * @param login    O login do usuário.
     * @param password A senha do usuário.
     * @param userName O nome do usuário.
     * @return true se o usuário for criado com sucesso.
     * @throws UserAlreadyExistsException Se o login já estiver cadastrado.
     * @throws LoginInvalidException      Se o login for inválido.
     * @throws PasswordInvalidException   Se a senha for inválida.
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
     * Obtém um atributo específico de um usuário.
     *
     * @param login     O login do usuário.
     * @param attribute O atributo a ser consultado.
     * @return O valor do atributo solicitado.
     * @throws InvalidAttributeProvidedException Se o atributo não for encontrado.
     * @throws UnregisteredUserException         Se o usuário não estiver cadastrado.
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
     * Abre uma sessão para um usuário autenticado.
     *
     * @param login    O login do usuário.
     * @param password A senha do usuário.
     * @return O identificador único da sessão aberta.
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
     * Obtém um usuário a partir de uma sessão ativa.
     *
     * @param sessionId O identificador da sessão.
     * @return O objeto UserAccount correspondente à sessão.
     * @throws UnregisteredUserException Se a sessão não estiver registrada.
     */

    public UserAccount getUserFromSession(String sessionId) throws UnregisteredUserException {
        if (!activeSessions.containsKey(sessionId)) {
            throw new UnregisteredUserException();
        }
        return activeSessions.get(sessionId);
    }

    /**
     * Edita o perfil de um usuário autenticado.
     *
     * @param id        O identificador da sessão do usuário.
     * @param attribute O atributo do perfil a ser editado.
     * @param value     O novo valor do atributo.
     * @throws UnregisteredUserException Se o usuário não estiver cadastrado.
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
     * Verifica se dois usuários são amigos.
     *
     * @param login      O login do usuário.
     * @param loginAmigo O login do possível amigo.
     * @return true se os usuários forem amigos, false caso contrário.
     * @throws UnregisteredUserException Se algum dos usuários não estiver cadastrado.
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
     * Adiciona um amigo à lista de amigos de um usuário autenticado.
     *
     * @param id    O identificador da sessão do usuário.
     * @param amigo O login do usuário a ser adicionado como amigo.
     * @throws UnregisteredUserException     Se o usuário ou o amigo não estiver cadastrado.
     * @throws RequestAlreadySendedException Se o pedido de amizade já foi enviado.
     * @throws UserAlreadyIsFriendException  Se os usuários já forem amigos.
     * @throws UserCannotAddHimselfException Se o usuário tentar adicionar a si mesmo.
     */

    public void addFriend(String id, String amigo) throws UnregisteredUserException, RequestAlreadySendedException, UserAlreadyIsFriendException, UserCannotAddHimselfException, InvalidFunctionDueEnemyException {
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

        if(friendUserAccount.getEnemysList().contains(userAccount.getLogin())) {
            throw new InvalidFunctionDueEnemyException(friendUserAccount.getUserName());
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
     * Obtém a lista de amigos de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma string contendo a lista de amigos.
     */

    public String getFriends(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFriendsString();
    }

    public void sendNote(String id, String receiver, String note) throws UnregisteredUserException, UserCannotSendNoteToHimselfException, InvalidFunctionDueEnemyException {
        if (!(activeSessions.containsKey(id) && usersMap.containsKey(receiver))) {
            throw new UnregisteredUserException();
        }

        if (activeSessions.get(id).getLogin().equals(receiver)) {
            throw new UserCannotSendNoteToHimselfException();
        }

        if(usersMap.get(receiver).getEnemysList().contains(activeSessions.get(id).getLogin())) {
            throw new InvalidFunctionDueEnemyException(usersMap.get(receiver).getUserName());
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

    public void createCommunity(String id, String name, String description) throws UnregisteredUserException, CommunityAlreadyExistsException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if(communityMap.containsKey(name)) {
            throw new CommunityAlreadyExistsException();
        }

        UserAccount owner = activeSessions.get(id);
        owner.setCommunityList(name);

        Community community = new Community(name, description, owner);
        communityMap.put(name, community);
    }

    public String getDescriptionCommunity(String name) throws CommunityDoesNotExistsException {
        if(communityMap.containsKey(name)) {
            return communityMap.get(name).getDescription();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    public String getOwnerCommunity(String name) throws CommunityDoesNotExistsException {
        if(communityMap.containsKey(name)) {
            UserAccount owner = communityMap.get(name).getOwner();

            return owner.getLogin();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    public String getMembersCommunity(String name) throws CommunityDoesNotExistsException {
        if(communityMap.containsKey(name)) {
            Community community = communityMap.get(name);

            return community.getMembersString();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    public String getCommunity(String login) throws UnregisteredUserException {
        if (!(getUsersMap().containsKey(login))) {
            throw new UnregisteredUserException();
        }

        return UtilsString.formatArrayList(usersMap.get(login).getCommunityList());
    }

    public void addComunity(String id, String comunityName) throws UnregisteredUserException, CommunityDoesNotExistsException, UserIsAlreadyInThisCommunityException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if(!(communityMap.containsKey(comunityName))) {
            throw new CommunityDoesNotExistsException();
        }

        if(activeSessions.get(id).getCommunityList().contains(comunityName)) {
            throw new UserIsAlreadyInThisCommunityException();
        } else {
            communityMap.get(comunityName).setMembersList(activeSessions.get(id));
            activeSessions.get(id).setCommunityList(comunityName);
        }
    }

    public void sendMessage(String id, String receiverCommunity, String message) throws UnregisteredUserException, CommunityDoesNotExistsException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if(!(communityMap.containsKey(receiverCommunity))) {
            throw new CommunityDoesNotExistsException();
        }

        Message newMessage = new Message(message);

        Community community = communityMap.get(receiverCommunity);

        for(UserAccount userAccount : community.getMembersList()) {
            userAccount.setMessagesQueue(newMessage);
        }
    }

    public String readMessage(String id) throws UnregisteredUserException, ThereAreNoMessagesException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        UserAccount userAccount = activeSessions.get(id);

        if(userAccount.getMessagesQueue().isEmpty()) {
            throw new ThereAreNoMessagesException();
        }

        Message message = userAccount.getMessagesQueue().poll();

        assert message != null;
        return message.getMessage();
    }

    public Boolean isFan(String login, String idol) {
        return usersMap.get(idol).getFansList().contains(login);
    }

    public void addIdol(String id, String idolName) throws UnregisteredUserException, UserAlreadyIsAnIdolException, UserCannotBeAFanOfHimselfException, InvalidFunctionDueEnemyException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if(!(usersMap.containsKey(idolName))) {
            throw new UnregisteredUserException();
        }

        String userLogin = activeSessions.get(id).getLogin();
        if(usersMap.get(idolName).getFansList().contains(userLogin)) {
            throw new UserAlreadyIsAnIdolException();
        }

        if(userLogin.equals(idolName)) {
            throw new UserCannotBeAFanOfHimselfException();
        }

        if(usersMap.get(idolName).getEnemysList().contains(userLogin)) {
            throw new InvalidFunctionDueEnemyException(usersMap.get(idolName).getUserName());
        }

        usersMap.get(idolName).setFansList(activeSessions.get(id).getLogin());
    }

    public String getFans(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFansString();
    }

    public Boolean isCrush(String id, String crush) throws UnregisteredUserException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        return activeSessions.get(id).getCrushsList().contains(crush);
    }

    public void addCrush(String id, String crush) throws UnregisteredUserException, UserIsAlreadyYourCrushException, UserCannotBeACrushOfHimselfException, InvalidFunctionDueEnemyException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        String userLogin = activeSessions.get(id).getLogin();

        if(!(usersMap.containsKey(crush))) {
            throw new UnregisteredUserException();
        }

        if(activeSessions.get(id).getCrushsList().contains(crush)) {
            throw new UserIsAlreadyYourCrushException();
        }

        if(userLogin.equals(crush)) {
            throw new UserCannotBeACrushOfHimselfException();
        }

        if(usersMap.get(crush).getEnemysList().contains(userLogin)) {
            throw new InvalidFunctionDueEnemyException(usersMap.get(crush).getUserName());
        }

        activeSessions.get(id).setCrushsList(crush);
    }

    public void automaticMessageForCrush(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        ArrayList<String> crushList = activeSessions.get(id).getCrushsList();

        for(String crush : crushList) {
            if(usersMap.containsKey(crush)) {
                if(usersMap.get(crush).getCrushsList().contains(activeSessions.get(id).getLogin())) {
                    sendNote(id, crush, activeSessions.get(id).getUserName() + " é seu paquera - Recado do Jackut.");
                }
            }
        }
    }

    public String getCrushs(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        automaticMessageForCrush(id);

        return activeSessions.get(id).getCrushsString();
    }

    public void addEnemy(String id, String enemyName) throws UnregisteredUserException, UserIsAlreadyYourEnemyException, UserCannotBeAEnemyOfHimselfException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if(!(usersMap.containsKey(enemyName))) {
            throw new UnregisteredUserException();
        }

        if(activeSessions.get(id).getEnemysList().contains(enemyName)) {
            throw new UserIsAlreadyYourEnemyException();
        }

        String userLogin = activeSessions.get(id).getLogin();
        if(userLogin.equals(enemyName)) {
            throw new UserCannotBeAEnemyOfHimselfException();
        }

        activeSessions.get(id).setEnemysList(enemyName);
    }

    /**
     * Salva os dados dos usuários no sistema.
     */

    public void saveData() {

        Serealization.serealizeObject(usersMap, "usersAccount");
        Serealization.serealizeObject(communityMap, "communities");
    }

    /**
     * Remove todos os dados do sistema, incluindo usuários, amigos e recados.
     */

    public void clearData() {
        usersMap.clear();
        communityMap.clear();
    }

    /**
     * Lê os dados dos usuários do armazenamento persistente.
     */

    public void readData() {
        this.usersMap = Serealization.deserializeObject("usersAccount");
        this.communityMap = Serealization.deserializeObject("communities");
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
