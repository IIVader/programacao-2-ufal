package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.jackutsystem.CommunityAlreadyExistsException;
import br.ufal.ic.p2.jackut.exceptions.jackutsystem.CommunityDoesNotExistsException;
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

/**
 * <p> Classe fachada que implementa a interface do sistema Jackut. </p>
 */

public class Facade {
    JackutSystem jackutSystem = new JackutSystem();

    /**
     * Reseta o sistema Jackut, removendo todos os dados de usuários, amigos e recados.
     */

    public void zerarSistema() {
        jackutSystem.clearData();
    }

    /**
     * Cria um novo usuário no sistema.
     *
     * @param login O login do novo usuário, que deve ser único.
     * @param senha A senha do novo usuário.
     * @param nome  O nome do novo usuário.
     * @throws UserAlreadyExistsException Se o login já estiver sendo utilizado por outro usuário.
     * @throws LoginInvalidException      Se o login fornecido não for válido.
     * @throws PasswordInvalidException   Se a senha fornecida não atender aos critérios definidos.
     */

    public void criarUsuario(String login, String senha, String nome) throws UserAlreadyExistsException, LoginInvalidException, PasswordInvalidException {
        jackutSystem.createUser(login, senha, nome);
    }

    /**
     * Retorna o valor de um atributo específico de um usuário.
     *
     * @param login    O login do usuário.
     * @param atributo O atributo desejado (por exemplo, "nome", "senha", etc.).
     * @return O valor do atributo solicitado.
     * @throws InvalidAttributeProvidedException Se o atributo solicitado for inválido.
     * @throws UnregisteredUserException         Se o usuário não estiver registrado no sistema.
     */

    public String getAtributoUsuario(String login, String atributo) throws InvalidAttributeProvidedException, UnregisteredUserException {
        return jackutSystem.getUserAttribute(login, atributo);
    }

    /**
     * Abre uma sessão para um usuário com login e senha válidos.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O identificador único da sessão aberta.
     * @throws InvalidLoginOrPasswordException Se o login ou a senha forem inválidos.
     */


    public String abrirSessao(String login, String senha) throws InvalidLoginOrPasswordException {
        return jackutSystem.openSession(login, senha);
    }

    /**
     * Edita um atributo do perfil de um usuário.
     *
     * @param id       O identificador do usuário.
     * @param atributo O atributo a ser editado.
     * @param valor    O novo valor do atributo.
     * @throws UnregisteredUserException Se o usuário não estiver registrado no sistema.
     */

    public void editarPerfil(String id, String atributo, String valor) throws UnregisteredUserException {
        jackutSystem.editProfile(id, atributo, valor);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login      O login do usuário.
     * @param loginAmigo O login do possível amigo.
     * @return {@code true} se os dois usuários forem amigos; {@code false} caso contrário.
     * @throws UnregisteredUserException Se algum dos dois usuários não estiver registrado no sistema.
     */

    public boolean ehAmigo(String login, String loginAmigo) throws UnregisteredUserException {
        return jackutSystem.isFriend(login, loginAmigo);
    }

    /**
     * Envia uma solicitação de amizade para outro usuário.
     *
     * @param id    O identificador do usuário que está enviando a solicitação.
     * @param amigo O login do usuário que receberá a solicitação de amizade.
     * @throws UnregisteredUserException     Se algum dos usuários não estiver registrado.
     * @throws RequestAlreadySendedException Se a solicitação já tiver sido enviada anteriormente.
     * @throws UserAlreadyIsFriendException  Se os usuários já forem amigos.
     * @throws UserCannotAddHimselfException Se o usuário tentar enviar amizade para si mesmo.
     */

    public void adicionarAmigo(String id, String amigo) throws UnregisteredUserException, RequestAlreadySendedException, UserAlreadyIsFriendException, UserCannotAddHimselfException {
        jackutSystem.addFriend(id, amigo);
    }

    /**
     * Envia um recado de um usuário para outro.
     *
     * @param id           O identificador do remetente.
     * @param destinatario O login do destinatário do recado.
     * @param recado       O conteúdo do recado.
     * @throws UnregisteredUserException            Se algum dos usuários não estiver registrado.
     * @throws UserCannotSendNoteToHimselfException Se o usuário tentar enviar um recado para si mesmo.
     */

    public void enviarRecado(String id, String destinatario, String recado) throws UnregisteredUserException, UserCannotSendNoteToHimselfException {
        jackutSystem.SendNote(id, destinatario, recado);
    }

    /**
     * Lê os recados recebidos por um usuário.
     *
     * @param id O identificador do usuário.
     * @return Uma string contendo os recados recebidos por este usuário.
     * @throws UnregisteredUserException Se o usuário não estiver registrado no sistema.
     * @throws ThereAreNoNotesException  Se o usuário não tiver nenhum recado.
     */

    public String lerRecado(String id) throws UnregisteredUserException, ThereAreNoNotesException {
        return jackutSystem.readNote(id);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma string representando os amigos do usuário.
     */

    public String getAmigos(String login) {
        return jackutSystem.getFriends(login);
    }

    public void criarComunidade(String id, String nome, String descricao) throws UnregisteredUserException, CommunityAlreadyExistsException {
        jackutSystem.createCommunity(id, nome, descricao);
    }

    public String getDescricaoComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getDescriptionCommunity(nome);
    }

    public String getDonoComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getOwnerCommunity(nome);
    }

    public String getMembrosComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getMembersCommunity(nome);
    }

    /**
     * Encerra o sistema Jackut, liberando os recursos utilizados.
     */

    public void encerrarSistema() {
        jackutSystem.closeSystem();
    }

    ;

}
