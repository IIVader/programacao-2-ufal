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
 * Classe principal do sistema Jackut, respons�vel por gerenciar usu�rios, sess�es, amizades e recados.
 */

public class JackutSystem {
    private HashMap<String, UserAccount> usersMap;
    private HashMap<String, Community> communityMap;
    private Map<String, UserAccount> activeSessions = new HashMap<>();
    private final Serealization serealization = new Serealization();

    /**
     * Construtor da classe JackutSystem. Inicializa o mapa de usu�rios e carrega os dados do sistema.
     */

    public JackutSystem() {
        this.usersMap = new HashMap<>();
        this.communityMap = new HashMap<>();
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

        if (friendUserAccount.getEnemysList().contains(userAccount.getLogin())) {
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
     * Obt�m a lista de amigos de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return Uma string contendo a lista de amigos.
     */

    public String getFriends(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFriendsString();
    }

    /**
     * Envia uma nota de um usu�rio para outro, verificando restri��es de envio.
     * <p>
     * Este m�todo verifica se o usu�rio remetente est� registrado na sess�o ativa e se o destinat�rio
     * tamb�m est� registrado. Al�m disso, o m�todo garante que o usu�rio n�o tente enviar uma nota para si mesmo
     * e que n�o envie uma nota para um inimigo. Caso todas as verifica��es passem, a nota � enviada para o destinat�rio.
     *
     * @param id       o identificador do usu�rio remetente.
     * @param receiver o identificador do usu�rio destinat�rio.
     * @param note     o conte�do da nota a ser enviada.
     * @throws UnregisteredUserException            se o remetente ou o destinat�rio n�o estiverem registrados.
     * @throws UserCannotSendNoteToHimselfException se o usu�rio tentar enviar uma nota para si mesmo.
     * @throws InvalidFunctionDueEnemyException     se o destinat�rio for um inimigo do remetente.
     */

    public void sendNote(String id, String receiver, String note) throws UnregisteredUserException, UserCannotSendNoteToHimselfException, InvalidFunctionDueEnemyException {
        if (!(activeSessions.containsKey(id) && usersMap.containsKey(receiver))) {
            throw new UnregisteredUserException();
        }

        if (activeSessions.get(id).getLogin().equals(receiver)) {
            throw new UserCannotSendNoteToHimselfException();
        }

        if (usersMap.get(receiver).getEnemysList().contains(activeSessions.get(id).getLogin())) {
            throw new InvalidFunctionDueEnemyException(usersMap.get(receiver).getUserName());
        }

        Note newNote = new Note(id, receiver, note);

        activeSessions.get(id).getPeopleISentNotesTo().add(receiver);

        usersMap.get(receiver).setNotesQueue(newNote);
    }

    /**
     * L� a pr�xima nota de um usu�rio registrado.
     * <p>
     * Este m�todo verifica se o usu�rio est� registrado na sess�o ativa e se o usu�rio tem notas em sua fila.
     * Caso haja uma nota, ela � removida da fila e o conte�do da nota � retornado. Se o usu�rio n�o estiver registrado
     * ou se n�o houver notas, exce��es apropriadas ser�o lan�adas.
     *
     * @param id o identificador do usu�rio que deseja ler a nota.
     * @return o conte�do da pr�xima nota na fila de notas do usu�rio.
     * @throws UnregisteredUserException se o usu�rio n�o estiver registrado na sess�o ativa.
     * @throws ThereAreNoNotesException  se n�o houver notas na fila do usu�rio.
     */

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
     * Cria uma nova comunidade e a associa a um usu�rio.
     * <p>
     * Este m�todo verifica se o usu�rio est� registrado na sess�o ativa e se a comunidade com o nome fornecido
     * j� existe. Caso n�o exista uma comunidade com o nome especificado, a nova comunidade � criada e o usu�rio
     * � associado como propriet�rio. A comunidade � ent�o adicionada ao mapa de comunidades.
     *
     * @param id          o identificador do usu�rio que est� criando a comunidade.
     * @param name        o nome da comunidade a ser criada.
     * @param description a descri��o da comunidade a ser criada.
     * @throws UnregisteredUserException       se o usu�rio n�o estiver registrado na sess�o ativa.
     * @throws CommunityAlreadyExistsException se j� existir uma comunidade com o nome especificado.
     */

    public void createCommunity(String id, String name, String description) throws UnregisteredUserException, CommunityAlreadyExistsException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if (communityMap.containsKey(name)) {
            throw new CommunityAlreadyExistsException();
        }

        UserAccount owner = activeSessions.get(id);
        owner.setCommunityList(name);

        Community community = new Community(name, description, owner);
        communityMap.put(name, community);
    }

    /**
     * Retorna a descri��o de uma comunidade espec�fica.
     * <p>
     * Este m�todo verifica se a comunidade especificada existe no sistema. Caso a comunidade seja encontrada,
     * a descri��o da comunidade � retornada. Se a comunidade n�o existir, uma exce��o ser� lan�ada.
     *
     * @param name o nome da comunidade para a qual a descri��o ser� retornada.
     * @return a descri��o da comunidade.
     * @throws CommunityDoesNotExistsException se a comunidade especificada n�o existir.
     */

    public String getDescriptionCommunity(String name) throws CommunityDoesNotExistsException {
        if (communityMap.containsKey(name)) {
            return communityMap.get(name).getDescription();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    /**
     * Retorna o login do propriet�rio de uma comunidade.
     * <p>
     * Este m�todo verifica se a comunidade especificada existe no sistema. Caso a comunidade seja encontrada,
     * o login do propriet�rio da comunidade � retornado. Se a comunidade n�o existir, uma exce��o ser� lan�ada.
     *
     * @param name o nome da comunidade para a qual o login do propriet�rio ser� retornado.
     * @return o login do propriet�rio da comunidade.
     * @throws CommunityDoesNotExistsException se a comunidade especificada n�o existir.
     */

    public String getOwnerCommunity(String name) throws CommunityDoesNotExistsException {
        if (communityMap.containsKey(name)) {
            UserAccount owner = communityMap.get(name).getOwner();

            return owner.getLogin();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    /**
     * Retorna uma lista de membros de uma comunidade espec�fica.
     * <p>
     * Este m�todo verifica se a comunidade especificada existe no sistema. Caso a comunidade seja encontrada,
     * a lista de membros da comunidade � retornada como uma string formatada. Se a comunidade n�o existir,
     * uma exce��o ser� lan�ada.
     *
     * @param name o nome da comunidade para a qual a lista de membros ser� retornada.
     * @return uma string contendo os membros da comunidade, formatada de acordo com o m�todo {@link Community#getMembersString}.
     * @throws CommunityDoesNotExistsException se a comunidade especificada n�o existir.
     */

    public String getMembersCommunity(String name) throws CommunityDoesNotExistsException {
        if (communityMap.containsKey(name)) {
            Community community = communityMap.get(name);

            return community.getMembersString();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    /**
     * Retorna a lista de comunidades de um usu�rio registrado.
     * <p>
     * Este m�todo verifica se o usu�rio est� registrado no sistema. Caso o usu�rio seja encontrado,
     * a lista de comunidades � qual o usu�rio pertence � retornada como uma string formatada. Se o usu�rio
     * n�o estiver registrado, uma exce��o ser� lan�ada.
     *
     * @param login o login do usu�rio cuja lista de comunidades ser� retornada.
     * @return uma string contendo as comunidades do usu�rio formatadas de acordo com o m�todo {@link UtilsString#formatArrayList}.
     * @throws UnregisteredUserException se o usu�rio n�o estiver registrado no sistema.
     */

    public String getCommunity(String login) throws UnregisteredUserException {
        if (!(getUsersMap().containsKey(login))) {
            throw new UnregisteredUserException();
        }

        return UtilsString.formatArrayList(usersMap.get(login).getCommunityList());
    }

    /**
     * Adiciona um usu�rio a uma comunidade existente.
     * <p>
     * Este m�todo verifica se o usu�rio est� registrado na sess�o ativa e se a comunidade especificada
     * existe. Caso o usu�rio ainda n�o fa�a parte da comunidade, ele � adicionado � lista de membros da
     * comunidade e a comunidade � associada ao usu�rio. Se o usu�rio j� fizer parte da comunidade ou se
     * a comunidade n�o existir, exce��es s�o lan�adas.
     *
     * @param id           o identificador do usu�rio que deseja entrar na comunidade.
     * @param comunityName o nome da comunidade � qual o usu�rio deseja ser adicionado.
     * @throws UnregisteredUserException             se o usu�rio n�o estiver registrado na sess�o ativa.
     * @throws CommunityDoesNotExistsException       se a comunidade especificada n�o existir.
     * @throws UserIsAlreadyInThisCommunityException se o usu�rio j� estiver presente na comunidade.
     */

    public void addComunity(String id, String comunityName) throws UnregisteredUserException, CommunityDoesNotExistsException, UserIsAlreadyInThisCommunityException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if (!(communityMap.containsKey(comunityName))) {
            throw new CommunityDoesNotExistsException();
        }

        if (activeSessions.get(id).getCommunityList().contains(comunityName)) {
            throw new UserIsAlreadyInThisCommunityException();
        } else {
            communityMap.get(comunityName).setMembersList(activeSessions.get(id));
            activeSessions.get(id).setCommunityList(comunityName);
        }
    }

    /**
     * Envia uma mensagem para todos os membros de uma comunidade.
     * <p>
     * Este m�todo verifica se o usu�rio est� registrado na sess�o ativa e se a comunidade de destino
     * existe. Caso ambas as condi��es sejam atendidas, a mensagem � criada e enviada para todos os
     * membros da comunidade, colocando-a na fila de mensagens de cada um.
     *
     * @param id                o identificador do usu�rio que est� enviando a mensagem.
     * @param receiverCommunity o nome da comunidade para a qual a mensagem ser� enviada.
     * @param message           o conte�do da mensagem a ser enviada para os membros da comunidade.
     * @throws UnregisteredUserException       se o usu�rio n�o estiver registrado na sess�o ativa.
     * @throws CommunityDoesNotExistsException se a comunidade especificada n�o existir.
     */

    public void sendMessage(String id, String receiverCommunity, String message) throws UnregisteredUserException, CommunityDoesNotExistsException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if (!(communityMap.containsKey(receiverCommunity))) {
            throw new CommunityDoesNotExistsException();
        }

        Message newMessage = new Message(message);

        Community community = communityMap.get(receiverCommunity);

        for (UserAccount userAccount : community.getMembersList()) {
            userAccount.setMessagesQueue(newMessage);
        }
    }

    /**
     * L� a pr�xima mensagem da fila de mensagens de um usu�rio registrado.
     * <p>
     * Este m�todo verifica se o usu�rio est� registrado na sess�o ativa e, em seguida, recupera a pr�xima
     * mensagem da fila de mensagens desse usu�rio. Caso o usu�rio n�o esteja registrado ou se n�o houver
     * mensagens na fila, exce��es apropriadas s�o lan�adas.
     *
     * @param id o identificador do usu�rio que deseja ler a mensagem.
     * @return o conte�do da pr�xima mensagem na fila do usu�rio.
     * @throws UnregisteredUserException   se o usu�rio n�o estiver registrado na sess�o ativa.
     * @throws ThereAreNoMessagesException se n�o houver mensagens na fila do usu�rio.
     */

    public String readMessage(String id) throws UnregisteredUserException, ThereAreNoMessagesException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        UserAccount userAccount = activeSessions.get(id);

        if (userAccount.getMessagesQueue().isEmpty()) {
            throw new ThereAreNoMessagesException();
        }

        Message message = userAccount.getMessagesQueue().poll();

        assert message != null;
        return message.getMessage();
    }

    /**
     * Verifica se um determinado usu�rio � f� de outro usu�rio (�dolo).
     *
     * @param login o login do poss�vel f�.
     * @param idol  o login do usu�rio que pode ser o �dolo.
     * @return {@code true} se o usu�rio identificado por {@code login} estiver na lista de f�s do {@code idol},
     * {@code false} caso contr�rio.
     */

    public Boolean isFan(String login, String idol) {
        return usersMap.get(idol).getFansList().contains(login);
    }

    /**
     * Adiciona um usu�rio como �dolo do usu�rio autenticado, ou seja, o usu�rio autenticado se torna f� do �dolo especificado.
     *
     * <p>Este m�todo realiza diversas verifica��es antes de registrar a rela��o de f�:
     * <ul>
     *   <li>Verifica se o usu�rio autenticado est� com sess�o ativa.</li>
     *   <li>Verifica se o �dolo especificado est� registrado no sistema.</li>
     *   <li>Impede que o usu�rio se torne f� de algu�m que j� considera como �dolo.</li>
     *   <li>Impede que o usu�rio se torne f� de si mesmo.</li>
     *   <li>Impede que o usu�rio se torne f� de algu�m que o tenha como inimigo.</li>
     * </ul>
     *
     * @param id       o identificador da sess�o do usu�rio que deseja adicionar um �dolo
     * @param idolName o login do usu�rio que ser� marcado como �dolo
     * @throws UnregisteredUserException          se o usu�rio autenticado ou o �dolo n�o estiver registrado
     * @throws UserAlreadyIsAnIdolException       se o �dolo j� estiver na lista de f�s
     * @throws UserCannotBeAFanOfHimselfException se o usu�rio tentar se tornar f� de si mesmo
     * @throws InvalidFunctionDueEnemyException   se o �dolo tiver o usu�rio autenticado como inimigo
     */

    public void addIdol(String id, String idolName) throws UnregisteredUserException, UserAlreadyIsAnIdolException, UserCannotBeAFanOfHimselfException, InvalidFunctionDueEnemyException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if (!(usersMap.containsKey(idolName))) {
            throw new UnregisteredUserException();
        }

        String userLogin = activeSessions.get(id).getLogin();
        if (usersMap.get(idolName).getFansList().contains(userLogin)) {
            throw new UserAlreadyIsAnIdolException();
        }

        if (userLogin.equals(idolName)) {
            throw new UserCannotBeAFanOfHimselfException();
        }

        if (usersMap.get(idolName).getEnemysList().contains(userLogin)) {
            throw new InvalidFunctionDueEnemyException(usersMap.get(idolName).getUserName());
        }

        usersMap.get(idolName).setFansList(activeSessions.get(id).getLogin());
    }

    /**
     * Retorna uma representa��o em formato de string dos f�s (usu�rios que marcaram como crush)
     * do usu�rio identificado pelo login fornecido.
     *
     * <p>Este m�todo acessa a conta do usu�rio pelo login informado e retorna a lista
     * de f�s no formato de {@code String}, conforme definido por {@code getFansString()}.
     *
     * @param login o login do usu�rio cuja lista de f�s ser� recuperada
     * @return uma {@code String} contendo os logins ou nomes dos usu�rios que t�m o usu�rio como crush
     */

    public String getFans(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFansString();
    }

    /**
     * Verifica se o usu�rio especificado est� na lista de crushes do usu�rio autenticado.
     *
     * <p>Este m�todo consulta a lista de crushes associada � sess�o ativa identificada por {@code id}
     * e retorna {@code true} se o login informado em {@code crush} estiver presente nessa lista.
     *
     * @param id    o identificador da sess�o do usu�rio autenticado
     * @param crush o login do usu�rio a ser verificado como crush
     * @return {@code true} se o usu�rio especificado estiver na lista de crushes, {@code false} caso contr�rio
     * @throws UnregisteredUserException se o usu�rio n�o estiver com a sess�o ativa
     */

    public Boolean isCrush(String id, String crush) throws UnregisteredUserException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        return activeSessions.get(id).getCrushsList().contains(crush);
    }

    /**
     * Adiciona um usu�rio � lista de crushes do usu�rio autenticado.
     *
     * <p>Este m�todo realiza diversas valida��es antes de adicionar o crush:
     * <ul>
     *   <li>Verifica se o usu�rio est� autenticado (sess�o ativa).</li>
     *   <li>Verifica se o usu�rio a ser marcado como crush est� registrado no sistema.</li>
     *   <li>Impede que um usu�rio adicione algu�m que j� esteja em sua lista de crushes.</li>
     *   <li>Impede que o usu�rio adicione a si mesmo como crush.</li>
     *   <li>Impede que o usu�rio adicione como crush algu�m que o tenha como inimigo.</li>
     * </ul>
     *
     * @param id    o identificador da sess�o do usu�rio que est� adicionando o crush
     * @param crush o login do usu�rio a ser adicionado como crush
     * @throws UnregisteredUserException            se o usu�rio autenticado ou o usu�rio a ser adicionado n�o estiver registrado
     * @throws UserIsAlreadyYourCrushException      se o usu�rio j� estiver na lista de crushes
     * @throws UserCannotBeACrushOfHimselfException se o usu�rio tentar se adicionar como crush
     * @throws InvalidFunctionDueEnemyException     se o usu�rio a ser adicionado tiver o usu�rio autenticado como inimigo
     */

    public void addCrush(String id, String crush) throws UnregisteredUserException, UserIsAlreadyYourCrushException, UserCannotBeACrushOfHimselfException, InvalidFunctionDueEnemyException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        String userLogin = activeSessions.get(id).getLogin();

        if (!(usersMap.containsKey(crush))) {
            throw new UnregisteredUserException();
        }

        if (activeSessions.get(id).getCrushsList().contains(crush)) {
            throw new UserIsAlreadyYourCrushException();
        }

        if (userLogin.equals(crush)) {
            throw new UserCannotBeACrushOfHimselfException();
        }

        if (usersMap.get(crush).getEnemysList().contains(userLogin)) {
            throw new InvalidFunctionDueEnemyException(usersMap.get(crush).getUserName());
        }

        activeSessions.get(id).setCrushsList(crush);
    }

    /**
     * Envia automaticamente uma mensagem para usu�rios que possuem uma paquera rec�proca com o usu�rio autenticado.
     *
     * <p>Este m�todo percorre a lista de crushes do usu�rio identificado por {@code id} e,
     * para cada crush, verifica se h� reciprocidade (ou seja, se o crush tamb�m marcou o usu�rio como crush).
     * Caso haja reciprocidade, � enviada uma nota autom�tica com a mensagem "{nome} � seu paquera - Recado do Jackut.".
     *
     * @param id o identificador da sess�o do usu�rio autenticado
     * @throws UserCannotSendNoteToHimselfException se o usu�rio tentar enviar uma nota para si mesmo
     * @throws UnregisteredUserException            se o usu�rio n�o estiver com uma sess�o ativa
     * @throws InvalidFunctionDueEnemyException     se o envio da nota n�o for permitido por rela��o de inimizade
     */

    public void automaticMessageForCrush(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        ArrayList<String> crushList = activeSessions.get(id).getCrushsList();

        for (String crush : crushList) {
            if (usersMap.containsKey(crush)) {
                if (usersMap.get(crush).getCrushsList().contains(activeSessions.get(id).getLogin())) {
                    sendNote(id, crush, activeSessions.get(id).getUserName() + " � seu paquera - Recado do Jackut.");
                }
            }
        }
    }

    /**
     * Retorna uma representa��o em formato de string dos usu�rios marcados como "crush" pelo usu�rio autenticado.
     *
     * <p>Antes de obter a lista de crushes, o m�todo executa uma verifica��o autom�tica
     * atrav�s de {@code automaticMessageForCrush(id)}, que pode lan�ar exce��es com base em restri��es de uso:
     * <ul>
     *   <li>Usu�rios n�o podem marcar a si mesmos como crush.</li>
     *   <li>Usu�rios n�o registrados ou n�o autenticados n�o podem acessar essa funcionalidade.</li>
     *   <li>Usu�rios n�o podem enviar notas ou marcar como crush algu�m que seja considerado inimigo.</li>
     * </ul>
     *
     * @param id o identificador da sess�o do usu�rio solicitando a lista de crushes
     * @return uma {@code String} contendo os nomes dos usu�rios marcados como crush
     * @throws UserCannotSendNoteToHimselfException se o usu�rio tentar enviar uma nota para si mesmo durante o processo
     * @throws UnregisteredUserException            se o usu�rio n�o estiver com uma sess�o ativa
     * @throws InvalidFunctionDueEnemyException     se a funcionalidade for inv�lida devido � rela��o de inimizade
     */

    public String getCrushs(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        automaticMessageForCrush(id);

        return activeSessions.get(id).getCrushsString();
    }

    /**
     * Adiciona um usu�rio � lista de inimigos do usu�rio atualmente autenticado.
     *
     * <p>Este m�todo realiza as seguintes valida��es antes de adicionar o inimigo:
     * <ul>
     *   <li>Verifica se o usu�rio atual est� com a sess�o ativa.</li>
     *   <li>Verifica se o inimigo especificado est� registrado no sistema.</li>
     *   <li>Verifica se o inimigo j� est� presente na lista de inimigos.</li>
     *   <li>Verifica se o usu�rio n�o est� tentando se adicionar como inimigo.</li>
     * </ul>
     *
     * @param id        o identificador da sess�o do usu�rio que deseja adicionar um inimigo
     * @param enemyName o nome do usu�rio a ser adicionado como inimigo
     * @throws UnregisteredUserException            se o usu�rio atual ou o inimigo n�o estiver registrado ou autenticado
     * @throws UserIsAlreadyYourEnemyException      se o usu�rio especificado j� for um inimigo
     * @throws UserCannotBeAEnemyOfHimselfException se o usu�rio tentar se adicionar como inimigo
     */

    public void addEnemy(String id, String enemyName) throws UnregisteredUserException, UserIsAlreadyYourEnemyException, UserCannotBeAEnemyOfHimselfException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        if (!(usersMap.containsKey(enemyName))) {
            throw new UnregisteredUserException();
        }

        if (activeSessions.get(id).getEnemysList().contains(enemyName)) {
            throw new UserIsAlreadyYourEnemyException();
        }

        String userLogin = activeSessions.get(id).getLogin();
        if (userLogin.equals(enemyName)) {
            throw new UserCannotBeAEnemyOfHimselfException();
        }

        activeSessions.get(id).setEnemysList(enemyName);
    }

    /**
     * Remove um usu�rio do sistema com base no seu identificador de sess�o.
     *
     * <p>Este m�todo realiza as seguintes a��es:
     * <ul>
     *   <li>Verifica se o usu�rio est� registrado em uma sess�o ativa. Caso contr�rio, lan�a uma exce��o.</li>
     *   <li>Remove a primeira nota na fila de anota��es dos usu�rios para quem o usu�rio atual enviou notas.</li>
     *   <li>Remove comunidades em que o usu�rio � o dono, e tamb�m remove o nome da comunidade das listas dos membros.</li>
     *   <li>Remove o usu�rio dos mapas de usu�rios registrados e de sess�es ativas.</li>
     * </ul>
     *
     * @param id o identificador da sess�o do usu�rio a ser removido
     * @throws UnregisteredUserException se o usu�rio n�o estiver registrado em uma sess�o ativa
     */

    public void removeUser(String id) throws UnregisteredUserException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        UserAccount userToBeDeleted = activeSessions.get(id);

        for (String userName : userToBeDeleted.getPeopleISentNotesTo()) {
            usersMap.get(userName).getNotesQueue().poll();
        }

        for (Community community : communityMap.values()) {
            if (community.getOwner().getLogin().equals(userToBeDeleted.getLogin())) {
                communityMap.remove(community.getName());
                for (int i = 0; i < community.getMembersList().size(); i++) {
                    community.getMembersList().get(i).getCommunityList().remove(community.getName());
                }
            }
        }

        usersMap.remove(userToBeDeleted.getLogin());
        activeSessions.remove(id);
    }

    /**
     * Salva os dados dos usu�rios no sistema.
     */

    public void saveData() {

        Serealization.serealizeObject(usersMap, "usersAccount");
        Serealization.serealizeObject(communityMap, "communities");
    }

    /**
     * Remove todos os dados do sistema, incluindo usu�rios, amigos e recados.
     */

    public void clearData() {
        usersMap.clear();
        communityMap.clear();
    }

    /**
     * L� os dados dos usu�rios do armazenamento persistente.
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
