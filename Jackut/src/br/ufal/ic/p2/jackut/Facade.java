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
     * Reseta o sistema Jackut, removendo todos os dados de usu�rios, amigos e recados.
     */

    public void zerarSistema() {
        jackutSystem.clearData();
    }

    /**
     * Cria um novo usu�rio no sistema.
     *
     * @param login O login do novo usu�rio, que deve ser �nico.
     * @param senha A senha do novo usu�rio.
     * @param nome  O nome do novo usu�rio.
     * @throws UserAlreadyExistsException Se o login j� estiver sendo utilizado por outro usu�rio.
     * @throws LoginInvalidException      Se o login fornecido n�o for v�lido.
     * @throws PasswordInvalidException   Se a senha fornecida n�o atender aos crit�rios definidos.
     */

    public void criarUsuario(String login, String senha, String nome) throws UserAlreadyExistsException, LoginInvalidException, PasswordInvalidException {
        jackutSystem.createUser(login, senha, nome);
    }

    /**
     * Retorna o valor de um atributo espec�fico de um usu�rio.
     *
     * @param login    O login do usu�rio.
     * @param atributo O atributo desejado (por exemplo, "nome", "senha", etc.).
     * @return O valor do atributo solicitado.
     * @throws InvalidAttributeProvidedException Se o atributo solicitado for inv�lido.
     * @throws UnregisteredUserException         Se o usu�rio n�o estiver registrado no sistema.
     */

    public String getAtributoUsuario(String login, String atributo) throws InvalidAttributeProvidedException, UnregisteredUserException {
        return jackutSystem.getUserAttribute(login, atributo);
    }

    /**
     * Abre uma sess�o para um usu�rio com login e senha v�lidos.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O identificador �nico da sess�o aberta.
     * @throws InvalidLoginOrPasswordException Se o login ou a senha forem inv�lidos.
     */


    public String abrirSessao(String login, String senha) throws InvalidLoginOrPasswordException {
        return jackutSystem.openSession(login, senha);
    }

    /**
     * Edita um atributo do perfil de um usu�rio.
     *
     * @param id       O identificador do usu�rio.
     * @param atributo O atributo a ser editado.
     * @param valor    O novo valor do atributo.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado no sistema.
     */

    public void editarPerfil(String id, String atributo, String valor) throws UnregisteredUserException {
        jackutSystem.editProfile(id, atributo, valor);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login      O login do usu�rio.
     * @param loginAmigo O login do poss�vel amigo.
     * @return {@code true} se os dois usu�rios forem amigos; {@code false} caso contr�rio.
     * @throws UnregisteredUserException Se algum dos dois usu�rios n�o estiver registrado no sistema.
     */

    public boolean ehAmigo(String login, String loginAmigo) throws UnregisteredUserException {
        return jackutSystem.isFriend(login, loginAmigo);
    }

    /**
     * Envia uma solicita��o de amizade para outro usu�rio.
     *
     * @param id    O identificador do usu�rio que est� enviando a solicita��o.
     * @param amigo O login do usu�rio que receber� a solicita��o de amizade.
     * @throws UnregisteredUserException     Se algum dos usu�rios n�o estiver registrado.
     * @throws RequestAlreadySendedException Se a solicita��o j� tiver sido enviada anteriormente.
     * @throws UserAlreadyIsFriendException  Se os usu�rios j� forem amigos.
     * @throws UserCannotAddHimselfException Se o usu�rio tentar enviar amizade para si mesmo.
     */

    public void adicionarAmigo(String id, String amigo) throws UnregisteredUserException, RequestAlreadySendedException, UserAlreadyIsFriendException, UserCannotAddHimselfException {
        jackutSystem.addFriend(id, amigo);
    }

    /**
     * Envia um recado de um usu�rio para outro.
     *
     * @param id           O identificador do remetente.
     * @param destinatario O login do destinat�rio do recado.
     * @param recado       O conte�do do recado.
     * @throws UnregisteredUserException            Se algum dos usu�rios n�o estiver registrado.
     * @throws UserCannotSendNoteToHimselfException Se o usu�rio tentar enviar um recado para si mesmo.
     */

    public void enviarRecado(String id, String destinatario, String recado) throws UnregisteredUserException, UserCannotSendNoteToHimselfException {
        jackutSystem.SendNote(id, destinatario, recado);
    }

    /**
     * L� os recados recebidos por um usu�rio.
     *
     * @param id O identificador do usu�rio.
     * @return Uma string contendo os recados recebidos por este usu�rio.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado no sistema.
     * @throws ThereAreNoNotesException  Se o usu�rio n�o tiver nenhum recado.
     */

    public String lerRecado(String id) throws UnregisteredUserException, ThereAreNoNotesException {
        return jackutSystem.readNote(id);
    }

    /**
     * Retorna a lista de amigos de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return Uma string representando os amigos do usu�rio.
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
