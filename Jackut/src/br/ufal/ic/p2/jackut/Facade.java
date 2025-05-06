package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.community.ThereAreNoMessagesException;
import br.ufal.ic.p2.jackut.exceptions.jackutsystem.*;
import br.ufal.ic.p2.jackut.exceptions.note.ThereAreNoNotesException;
import br.ufal.ic.p2.jackut.exceptions.note.UserCannotSendNoteToHimselfException;
import br.ufal.ic.p2.jackut.exceptions.profile.InvalidAttributeProvidedException;
import br.ufal.ic.p2.jackut.exceptions.user.*;

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

    public void adicionarAmigo(String id, String amigo) throws UnregisteredUserException, RequestAlreadySendedException, UserAlreadyIsFriendException, UserCannotAddHimselfException, InvalidFunctionDueEnemyException {
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

    public void enviarRecado(String id, String destinatario, String recado) throws UnregisteredUserException, UserCannotSendNoteToHimselfException, InvalidFunctionDueEnemyException {
        jackutSystem.sendNote(id, destinatario, recado);
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

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param id O ID do usuário que está criando a comunidade.
     * @param nome O nome da comunidade a ser criada.
     * @param descricao A descrição da comunidade.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws CommunityAlreadyExistsException Se já existir uma comunidade com o mesmo nome.
     */

    public void criarComunidade(String id, String nome, String descricao) throws UnregisteredUserException, CommunityAlreadyExistsException {
        jackutSystem.createCommunity(id, nome, descricao);
    }

    /**
     * Obtém a descrição de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return A descrição da comunidade.
     * @throws CommunityDoesNotExistsException Se a comunidade não existir.
     */

    public String getDescricaoComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getDescriptionCommunity(nome);
    }

    /**
     * Obtém o dono de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return O nome do dono da comunidade.
     * @throws CommunityDoesNotExistsException Se a comunidade não existir.
     */

    public String getDonoComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getOwnerCommunity(nome);
    }

    /**
     * Obtém os membros de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return Uma lista com os membros da comunidade.
     * @throws CommunityDoesNotExistsException Se a comunidade não existir.
     */

    public String getMembrosComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getMembersCommunity(nome);
    }

    /**
     * Obtém as comunidades de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma lista de comunidades associadas ao usuário.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     */

    public String getComunidades(String login) throws UnregisteredUserException {
        return jackutSystem.getCommunity(login);
    }

    /**
     * Adiciona um usuário a uma comunidade.
     *
     * @param id O ID do usuário a ser adicionado.
     * @param nome O nome da comunidade.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws CommunityDoesNotExistsException Se a comunidade não existir.
     * @throws UserIsAlreadyInThisCommunityException Se o usuário já for membro da comunidade.
     */

    public void adicionarComunidade(String id, String nome) throws UnregisteredUserException, CommunityDoesNotExistsException, UserIsAlreadyInThisCommunityException {
        jackutSystem.addComunity(id, nome);
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param id O ID do usuário que está enviando a mensagem.
     * @param comunidadeReceptora O nome da comunidade para a qual a mensagem será enviada.
     * @param mensagem O conteúdo da mensagem.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws CommunityDoesNotExistsException Se a comunidade não existir.
     */

    public void enviarMensagem(String id, String comunidadeReceptora, String mensagem) throws UnregisteredUserException, CommunityDoesNotExistsException {
        jackutSystem.sendMessage(id, comunidadeReceptora, mensagem);
    }

    /**
     * Lê a mensagem recebida por um usuário.
     *
     * @param id O ID do usuário que está lendo a mensagem.
     * @return O conteúdo da mensagem recebida.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws ThereAreNoMessagesException Se não houver mensagens para o usuário.
     */

    public String lerMensagem(String id) throws UnregisteredUserException, ThereAreNoMessagesException {
        return jackutSystem.readMessage(id);
    }

    /**
     * Verifica se um usuário é fã de outro usuário.
     *
     * @param login O login do usuário.
     * @param idolo O login do ídolo.
     * @return Verdadeiro se o usuário for fã do ídolo, falso caso contrário.
     */

    public Boolean ehFa(String login, String idolo) {
        return jackutSystem.isFan(login, idolo);
    }


    /**
     * Adiciona um ídolo a um usuário.
     *
     * @param id O ID do usuário que está adicionando um ídolo.
     * @param nomeIdolo O nome do ídolo a ser adicionado.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws UserAlreadyIsAnIdolException Se o usuário já for ídolo de outra pessoa.
     * @throws UserCannotBeAFanOfHimselfException Se o usuário tentar ser fã de si mesmo.
     * @throws InvalidFunctionDueEnemyException Se a função não for válida devido a um inimigo.
     */

    public void adicionarIdolo(String id, String nomeIdolo) throws UnregisteredUserException, UserAlreadyIsAnIdolException, UserCannotBeAFanOfHimselfException, InvalidFunctionDueEnemyException {
        jackutSystem.addIdol(id, nomeIdolo);
    }


    /**
     * Obtém os fãs de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma lista de fãs do usuário.
     */

    public String getFas(String login) {
        return jackutSystem.getFans(login);
    }

    /**
     * Verifica se um usuário tem uma paquera com outro usuário.
     *
     * @param id O ID do usuário.
     * @param paquera O ID da paquera.
     * @return Verdadeiro se o usuário tem uma paquera com o outro usuário, falso caso contrário.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     */

    public Boolean ehPaquera(String id, String paquera) throws UnregisteredUserException {
        return jackutSystem.isCrush(id, paquera);
    }

    /**
     * Adiciona uma paquera a um usuário.
     *
     * @param id O ID do usuário que está adicionando uma paquera.
     * @param paquera O ID da pessoa a ser adicionada como paquera.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws UserIsAlreadyYourCrushException Se o usuário já tiver a pessoa como paquera.
     * @throws UserCannotBeACrushOfHimselfException Se o usuário tentar ser paquera de si mesmo.
     * @throws InvalidFunctionDueEnemyException Se a função não for válida devido a um inimigo.
     */

    public void adicionarPaquera(String id, String paquera) throws UnregisteredUserException, UserIsAlreadyYourCrushException, UserCannotBeACrushOfHimselfException, InvalidFunctionDueEnemyException {
        jackutSystem.addCrush(id, paquera);
    }

    /**
     * Obtém as paqueras de um usuário.
     *
     * @param id O ID do usuário.
     * @return Uma lista das paqueras do usuário.
     * @throws UserCannotSendNoteToHimselfException Se o usuário tentar mandar uma mensagem para si mesmo.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws InvalidFunctionDueEnemyException Se a função não for válida devido a um inimigo.
     */

    public String getPaqueras(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        return jackutSystem.getCrushs(id);
    }

    /**
     * Adiciona um inimigo a um usuário.
     *
     * @param id O ID do usuário que está adicionando um inimigo.
     * @param inimigoNome O nome do inimigo a ser adicionado.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     * @throws UserIsAlreadyYourEnemyException Se o usuário já tiver a pessoa como inimigo.
     * @throws UserCannotBeAEnemyOfHimselfException Se o usuário tentar ser inimigo de si mesmo.
     */

    public void adicionarInimigo(String id, String inimigoNome) throws UnregisteredUserException, UserIsAlreadyYourEnemyException, UserCannotBeAEnemyOfHimselfException {
        jackutSystem.addEnemy(id, inimigoNome);
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param id O ID do usuário a ser removido.
     * @throws UnregisteredUserException Se o usuário não estiver registrado.
     */

    public void removerUsuario(String id) throws UnregisteredUserException {
        jackutSystem.removeUser(id);
    }

    /**
     * Encerra o sistema Jackut, liberando os recursos utilizados.
     */

    public void encerrarSistema() {
        jackutSystem.closeSystem();
    }

    ;

}
