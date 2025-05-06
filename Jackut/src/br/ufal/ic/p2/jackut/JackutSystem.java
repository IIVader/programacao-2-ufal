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
     * Obtém a lista de amigos de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma string contendo a lista de amigos.
     */

    public String getFriends(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFriendsString();
    }

    /**
     * Envia uma nota de um usuário para outro, verificando restrições de envio.
     * <p>
     * Este método verifica se o usuário remetente está registrado na sessão ativa e se o destinatário
     * também está registrado. Além disso, o método garante que o usuário não tente enviar uma nota para si mesmo
     * e que não envie uma nota para um inimigo. Caso todas as verificações passem, a nota é enviada para o destinatário.
     *
     * @param id       o identificador do usuário remetente.
     * @param receiver o identificador do usuário destinatário.
     * @param note     o conteúdo da nota a ser enviada.
     * @throws UnregisteredUserException            se o remetente ou o destinatário não estiverem registrados.
     * @throws UserCannotSendNoteToHimselfException se o usuário tentar enviar uma nota para si mesmo.
     * @throws InvalidFunctionDueEnemyException     se o destinatário for um inimigo do remetente.
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
     * Lê a próxima nota de um usuário registrado.
     * <p>
     * Este método verifica se o usuário está registrado na sessão ativa e se o usuário tem notas em sua fila.
     * Caso haja uma nota, ela é removida da fila e o conteúdo da nota é retornado. Se o usuário não estiver registrado
     * ou se não houver notas, exceções apropriadas serão lançadas.
     *
     * @param id o identificador do usuário que deseja ler a nota.
     * @return o conteúdo da próxima nota na fila de notas do usuário.
     * @throws UnregisteredUserException se o usuário não estiver registrado na sessão ativa.
     * @throws ThereAreNoNotesException  se não houver notas na fila do usuário.
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
     * Cria uma nova comunidade e a associa a um usuário.
     * <p>
     * Este método verifica se o usuário está registrado na sessão ativa e se a comunidade com o nome fornecido
     * já existe. Caso não exista uma comunidade com o nome especificado, a nova comunidade é criada e o usuário
     * é associado como proprietário. A comunidade é então adicionada ao mapa de comunidades.
     *
     * @param id          o identificador do usuário que está criando a comunidade.
     * @param name        o nome da comunidade a ser criada.
     * @param description a descrição da comunidade a ser criada.
     * @throws UnregisteredUserException       se o usuário não estiver registrado na sessão ativa.
     * @throws CommunityAlreadyExistsException se já existir uma comunidade com o nome especificado.
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
     * Retorna a descrição de uma comunidade específica.
     * <p>
     * Este método verifica se a comunidade especificada existe no sistema. Caso a comunidade seja encontrada,
     * a descrição da comunidade é retornada. Se a comunidade não existir, uma exceção será lançada.
     *
     * @param name o nome da comunidade para a qual a descrição será retornada.
     * @return a descrição da comunidade.
     * @throws CommunityDoesNotExistsException se a comunidade especificada não existir.
     */

    public String getDescriptionCommunity(String name) throws CommunityDoesNotExistsException {
        if (communityMap.containsKey(name)) {
            return communityMap.get(name).getDescription();
        } else {
            throw new CommunityDoesNotExistsException();
        }
    }

    /**
     * Retorna o login do proprietário de uma comunidade.
     * <p>
     * Este método verifica se a comunidade especificada existe no sistema. Caso a comunidade seja encontrada,
     * o login do proprietário da comunidade é retornado. Se a comunidade não existir, uma exceção será lançada.
     *
     * @param name o nome da comunidade para a qual o login do proprietário será retornado.
     * @return o login do proprietário da comunidade.
     * @throws CommunityDoesNotExistsException se a comunidade especificada não existir.
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
     * Retorna uma lista de membros de uma comunidade específica.
     * <p>
     * Este método verifica se a comunidade especificada existe no sistema. Caso a comunidade seja encontrada,
     * a lista de membros da comunidade é retornada como uma string formatada. Se a comunidade não existir,
     * uma exceção será lançada.
     *
     * @param name o nome da comunidade para a qual a lista de membros será retornada.
     * @return uma string contendo os membros da comunidade, formatada de acordo com o método {@link Community#getMembersString}.
     * @throws CommunityDoesNotExistsException se a comunidade especificada não existir.
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
     * Retorna a lista de comunidades de um usuário registrado.
     * <p>
     * Este método verifica se o usuário está registrado no sistema. Caso o usuário seja encontrado,
     * a lista de comunidades à qual o usuário pertence é retornada como uma string formatada. Se o usuário
     * não estiver registrado, uma exceção será lançada.
     *
     * @param login o login do usuário cuja lista de comunidades será retornada.
     * @return uma string contendo as comunidades do usuário formatadas de acordo com o método {@link UtilsString#formatArrayList}.
     * @throws UnregisteredUserException se o usuário não estiver registrado no sistema.
     */

    public String getCommunity(String login) throws UnregisteredUserException {
        if (!(getUsersMap().containsKey(login))) {
            throw new UnregisteredUserException();
        }

        return UtilsString.formatArrayList(usersMap.get(login).getCommunityList());
    }

    /**
     * Adiciona um usuário a uma comunidade existente.
     * <p>
     * Este método verifica se o usuário está registrado na sessão ativa e se a comunidade especificada
     * existe. Caso o usuário ainda não faça parte da comunidade, ele é adicionado à lista de membros da
     * comunidade e a comunidade é associada ao usuário. Se o usuário já fizer parte da comunidade ou se
     * a comunidade não existir, exceções são lançadas.
     *
     * @param id           o identificador do usuário que deseja entrar na comunidade.
     * @param comunityName o nome da comunidade à qual o usuário deseja ser adicionado.
     * @throws UnregisteredUserException             se o usuário não estiver registrado na sessão ativa.
     * @throws CommunityDoesNotExistsException       se a comunidade especificada não existir.
     * @throws UserIsAlreadyInThisCommunityException se o usuário já estiver presente na comunidade.
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
     * Este método verifica se o usuário está registrado na sessão ativa e se a comunidade de destino
     * existe. Caso ambas as condições sejam atendidas, a mensagem é criada e enviada para todos os
     * membros da comunidade, colocando-a na fila de mensagens de cada um.
     *
     * @param id                o identificador do usuário que está enviando a mensagem.
     * @param receiverCommunity o nome da comunidade para a qual a mensagem será enviada.
     * @param message           o conteúdo da mensagem a ser enviada para os membros da comunidade.
     * @throws UnregisteredUserException       se o usuário não estiver registrado na sessão ativa.
     * @throws CommunityDoesNotExistsException se a comunidade especificada não existir.
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
     * Lê a próxima mensagem da fila de mensagens de um usuário registrado.
     * <p>
     * Este método verifica se o usuário está registrado na sessão ativa e, em seguida, recupera a próxima
     * mensagem da fila de mensagens desse usuário. Caso o usuário não esteja registrado ou se não houver
     * mensagens na fila, exceções apropriadas são lançadas.
     *
     * @param id o identificador do usuário que deseja ler a mensagem.
     * @return o conteúdo da próxima mensagem na fila do usuário.
     * @throws UnregisteredUserException   se o usuário não estiver registrado na sessão ativa.
     * @throws ThereAreNoMessagesException se não houver mensagens na fila do usuário.
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
     * Verifica se um determinado usuário é fã de outro usuário (ídolo).
     *
     * @param login o login do possível fã.
     * @param idol  o login do usuário que pode ser o ídolo.
     * @return {@code true} se o usuário identificado por {@code login} estiver na lista de fãs do {@code idol},
     * {@code false} caso contrário.
     */

    public Boolean isFan(String login, String idol) {
        return usersMap.get(idol).getFansList().contains(login);
    }

    /**
     * Adiciona um usuário como ídolo do usuário autenticado, ou seja, o usuário autenticado se torna fã do ídolo especificado.
     *
     * <p>Este método realiza diversas verificações antes de registrar a relação de fã:
     * <ul>
     *   <li>Verifica se o usuário autenticado está com sessão ativa.</li>
     *   <li>Verifica se o ídolo especificado está registrado no sistema.</li>
     *   <li>Impede que o usuário se torne fã de alguém que já considera como ídolo.</li>
     *   <li>Impede que o usuário se torne fã de si mesmo.</li>
     *   <li>Impede que o usuário se torne fã de alguém que o tenha como inimigo.</li>
     * </ul>
     *
     * @param id       o identificador da sessão do usuário que deseja adicionar um ídolo
     * @param idolName o login do usuário que será marcado como ídolo
     * @throws UnregisteredUserException          se o usuário autenticado ou o ídolo não estiver registrado
     * @throws UserAlreadyIsAnIdolException       se o ídolo já estiver na lista de fãs
     * @throws UserCannotBeAFanOfHimselfException se o usuário tentar se tornar fã de si mesmo
     * @throws InvalidFunctionDueEnemyException   se o ídolo tiver o usuário autenticado como inimigo
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
     * Retorna uma representação em formato de string dos fãs (usuários que marcaram como crush)
     * do usuário identificado pelo login fornecido.
     *
     * <p>Este método acessa a conta do usuário pelo login informado e retorna a lista
     * de fãs no formato de {@code String}, conforme definido por {@code getFansString()}.
     *
     * @param login o login do usuário cuja lista de fãs será recuperada
     * @return uma {@code String} contendo os logins ou nomes dos usuários que têm o usuário como crush
     */

    public String getFans(String login) {
        UserAccount userAccount = usersMap.get(login);

        return userAccount.getFansString();
    }

    /**
     * Verifica se o usuário especificado está na lista de crushes do usuário autenticado.
     *
     * <p>Este método consulta a lista de crushes associada à sessão ativa identificada por {@code id}
     * e retorna {@code true} se o login informado em {@code crush} estiver presente nessa lista.
     *
     * @param id    o identificador da sessão do usuário autenticado
     * @param crush o login do usuário a ser verificado como crush
     * @return {@code true} se o usuário especificado estiver na lista de crushes, {@code false} caso contrário
     * @throws UnregisteredUserException se o usuário não estiver com a sessão ativa
     */

    public Boolean isCrush(String id, String crush) throws UnregisteredUserException {
        if (!activeSessions.containsKey(id)) {
            throw new UnregisteredUserException();
        }

        return activeSessions.get(id).getCrushsList().contains(crush);
    }

    /**
     * Adiciona um usuário à lista de crushes do usuário autenticado.
     *
     * <p>Este método realiza diversas validações antes de adicionar o crush:
     * <ul>
     *   <li>Verifica se o usuário está autenticado (sessão ativa).</li>
     *   <li>Verifica se o usuário a ser marcado como crush está registrado no sistema.</li>
     *   <li>Impede que um usuário adicione alguém que já esteja em sua lista de crushes.</li>
     *   <li>Impede que o usuário adicione a si mesmo como crush.</li>
     *   <li>Impede que o usuário adicione como crush alguém que o tenha como inimigo.</li>
     * </ul>
     *
     * @param id    o identificador da sessão do usuário que está adicionando o crush
     * @param crush o login do usuário a ser adicionado como crush
     * @throws UnregisteredUserException            se o usuário autenticado ou o usuário a ser adicionado não estiver registrado
     * @throws UserIsAlreadyYourCrushException      se o usuário já estiver na lista de crushes
     * @throws UserCannotBeACrushOfHimselfException se o usuário tentar se adicionar como crush
     * @throws InvalidFunctionDueEnemyException     se o usuário a ser adicionado tiver o usuário autenticado como inimigo
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
     * Envia automaticamente uma mensagem para usuários que possuem uma paquera recíproca com o usuário autenticado.
     *
     * <p>Este método percorre a lista de crushes do usuário identificado por {@code id} e,
     * para cada crush, verifica se há reciprocidade (ou seja, se o crush também marcou o usuário como crush).
     * Caso haja reciprocidade, é enviada uma nota automática com a mensagem "{nome} é seu paquera - Recado do Jackut.".
     *
     * @param id o identificador da sessão do usuário autenticado
     * @throws UserCannotSendNoteToHimselfException se o usuário tentar enviar uma nota para si mesmo
     * @throws UnregisteredUserException            se o usuário não estiver com uma sessão ativa
     * @throws InvalidFunctionDueEnemyException     se o envio da nota não for permitido por relação de inimizade
     */

    public void automaticMessageForCrush(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        ArrayList<String> crushList = activeSessions.get(id).getCrushsList();

        for (String crush : crushList) {
            if (usersMap.containsKey(crush)) {
                if (usersMap.get(crush).getCrushsList().contains(activeSessions.get(id).getLogin())) {
                    sendNote(id, crush, activeSessions.get(id).getUserName() + " é seu paquera - Recado do Jackut.");
                }
            }
        }
    }

    /**
     * Retorna uma representação em formato de string dos usuários marcados como "crush" pelo usuário autenticado.
     *
     * <p>Antes de obter a lista de crushes, o método executa uma verificação automática
     * através de {@code automaticMessageForCrush(id)}, que pode lançar exceções com base em restrições de uso:
     * <ul>
     *   <li>Usuários não podem marcar a si mesmos como crush.</li>
     *   <li>Usuários não registrados ou não autenticados não podem acessar essa funcionalidade.</li>
     *   <li>Usuários não podem enviar notas ou marcar como crush alguém que seja considerado inimigo.</li>
     * </ul>
     *
     * @param id o identificador da sessão do usuário solicitando a lista de crushes
     * @return uma {@code String} contendo os nomes dos usuários marcados como crush
     * @throws UserCannotSendNoteToHimselfException se o usuário tentar enviar uma nota para si mesmo durante o processo
     * @throws UnregisteredUserException            se o usuário não estiver com uma sessão ativa
     * @throws InvalidFunctionDueEnemyException     se a funcionalidade for inválida devido à relação de inimizade
     */

    public String getCrushs(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        automaticMessageForCrush(id);

        return activeSessions.get(id).getCrushsString();
    }

    /**
     * Adiciona um usuário à lista de inimigos do usuário atualmente autenticado.
     *
     * <p>Este método realiza as seguintes validações antes de adicionar o inimigo:
     * <ul>
     *   <li>Verifica se o usuário atual está com a sessão ativa.</li>
     *   <li>Verifica se o inimigo especificado está registrado no sistema.</li>
     *   <li>Verifica se o inimigo já está presente na lista de inimigos.</li>
     *   <li>Verifica se o usuário não está tentando se adicionar como inimigo.</li>
     * </ul>
     *
     * @param id        o identificador da sessão do usuário que deseja adicionar um inimigo
     * @param enemyName o nome do usuário a ser adicionado como inimigo
     * @throws UnregisteredUserException            se o usuário atual ou o inimigo não estiver registrado ou autenticado
     * @throws UserIsAlreadyYourEnemyException      se o usuário especificado já for um inimigo
     * @throws UserCannotBeAEnemyOfHimselfException se o usuário tentar se adicionar como inimigo
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
     * Remove um usuário do sistema com base no seu identificador de sessão.
     *
     * <p>Este método realiza as seguintes ações:
     * <ul>
     *   <li>Verifica se o usuário está registrado em uma sessão ativa. Caso contrário, lança uma exceção.</li>
     *   <li>Remove a primeira nota na fila de anotações dos usuários para quem o usuário atual enviou notas.</li>
     *   <li>Remove comunidades em que o usuário é o dono, e também remove o nome da comunidade das listas dos membros.</li>
     *   <li>Remove o usuário dos mapas de usuários registrados e de sessões ativas.</li>
     * </ul>
     *
     * @param id o identificador da sessão do usuário a ser removido
     * @throws UnregisteredUserException se o usuário não estiver registrado em uma sessão ativa
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
