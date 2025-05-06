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

    public void adicionarAmigo(String id, String amigo) throws UnregisteredUserException, RequestAlreadySendedException, UserAlreadyIsFriendException, UserCannotAddHimselfException, InvalidFunctionDueEnemyException {
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

    public void enviarRecado(String id, String destinatario, String recado) throws UnregisteredUserException, UserCannotSendNoteToHimselfException, InvalidFunctionDueEnemyException {
        jackutSystem.sendNote(id, destinatario, recado);
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

    /**
     * Cria uma nova comunidade no sistema.
     *
     * @param id O ID do usu�rio que est� criando a comunidade.
     * @param nome O nome da comunidade a ser criada.
     * @param descricao A descri��o da comunidade.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws CommunityAlreadyExistsException Se j� existir uma comunidade com o mesmo nome.
     */

    public void criarComunidade(String id, String nome, String descricao) throws UnregisteredUserException, CommunityAlreadyExistsException {
        jackutSystem.createCommunity(id, nome, descricao);
    }

    /**
     * Obt�m a descri��o de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return A descri��o da comunidade.
     * @throws CommunityDoesNotExistsException Se a comunidade n�o existir.
     */

    public String getDescricaoComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getDescriptionCommunity(nome);
    }

    /**
     * Obt�m o dono de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return O nome do dono da comunidade.
     * @throws CommunityDoesNotExistsException Se a comunidade n�o existir.
     */

    public String getDonoComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getOwnerCommunity(nome);
    }

    /**
     * Obt�m os membros de uma comunidade.
     *
     * @param nome O nome da comunidade.
     * @return Uma lista com os membros da comunidade.
     * @throws CommunityDoesNotExistsException Se a comunidade n�o existir.
     */

    public String getMembrosComunidade(String nome) throws CommunityDoesNotExistsException {
        return jackutSystem.getMembersCommunity(nome);
    }

    /**
     * Obt�m as comunidades de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return Uma lista de comunidades associadas ao usu�rio.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     */

    public String getComunidades(String login) throws UnregisteredUserException {
        return jackutSystem.getCommunity(login);
    }

    /**
     * Adiciona um usu�rio a uma comunidade.
     *
     * @param id O ID do usu�rio a ser adicionado.
     * @param nome O nome da comunidade.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws CommunityDoesNotExistsException Se a comunidade n�o existir.
     * @throws UserIsAlreadyInThisCommunityException Se o usu�rio j� for membro da comunidade.
     */

    public void adicionarComunidade(String id, String nome) throws UnregisteredUserException, CommunityDoesNotExistsException, UserIsAlreadyInThisCommunityException {
        jackutSystem.addComunity(id, nome);
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param id O ID do usu�rio que est� enviando a mensagem.
     * @param comunidadeReceptora O nome da comunidade para a qual a mensagem ser� enviada.
     * @param mensagem O conte�do da mensagem.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws CommunityDoesNotExistsException Se a comunidade n�o existir.
     */

    public void enviarMensagem(String id, String comunidadeReceptora, String mensagem) throws UnregisteredUserException, CommunityDoesNotExistsException {
        jackutSystem.sendMessage(id, comunidadeReceptora, mensagem);
    }

    /**
     * L� a mensagem recebida por um usu�rio.
     *
     * @param id O ID do usu�rio que est� lendo a mensagem.
     * @return O conte�do da mensagem recebida.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws ThereAreNoMessagesException Se n�o houver mensagens para o usu�rio.
     */

    public String lerMensagem(String id) throws UnregisteredUserException, ThereAreNoMessagesException {
        return jackutSystem.readMessage(id);
    }

    /**
     * Verifica se um usu�rio � f� de outro usu�rio.
     *
     * @param login O login do usu�rio.
     * @param idolo O login do �dolo.
     * @return Verdadeiro se o usu�rio for f� do �dolo, falso caso contr�rio.
     */

    public Boolean ehFa(String login, String idolo) {
        return jackutSystem.isFan(login, idolo);
    }


    /**
     * Adiciona um �dolo a um usu�rio.
     *
     * @param id O ID do usu�rio que est� adicionando um �dolo.
     * @param nomeIdolo O nome do �dolo a ser adicionado.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws UserAlreadyIsAnIdolException Se o usu�rio j� for �dolo de outra pessoa.
     * @throws UserCannotBeAFanOfHimselfException Se o usu�rio tentar ser f� de si mesmo.
     * @throws InvalidFunctionDueEnemyException Se a fun��o n�o for v�lida devido a um inimigo.
     */

    public void adicionarIdolo(String id, String nomeIdolo) throws UnregisteredUserException, UserAlreadyIsAnIdolException, UserCannotBeAFanOfHimselfException, InvalidFunctionDueEnemyException {
        jackutSystem.addIdol(id, nomeIdolo);
    }


    /**
     * Obt�m os f�s de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return Uma lista de f�s do usu�rio.
     */

    public String getFas(String login) {
        return jackutSystem.getFans(login);
    }

    /**
     * Verifica se um usu�rio tem uma paquera com outro usu�rio.
     *
     * @param id O ID do usu�rio.
     * @param paquera O ID da paquera.
     * @return Verdadeiro se o usu�rio tem uma paquera com o outro usu�rio, falso caso contr�rio.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     */

    public Boolean ehPaquera(String id, String paquera) throws UnregisteredUserException {
        return jackutSystem.isCrush(id, paquera);
    }

    /**
     * Adiciona uma paquera a um usu�rio.
     *
     * @param id O ID do usu�rio que est� adicionando uma paquera.
     * @param paquera O ID da pessoa a ser adicionada como paquera.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws UserIsAlreadyYourCrushException Se o usu�rio j� tiver a pessoa como paquera.
     * @throws UserCannotBeACrushOfHimselfException Se o usu�rio tentar ser paquera de si mesmo.
     * @throws InvalidFunctionDueEnemyException Se a fun��o n�o for v�lida devido a um inimigo.
     */

    public void adicionarPaquera(String id, String paquera) throws UnregisteredUserException, UserIsAlreadyYourCrushException, UserCannotBeACrushOfHimselfException, InvalidFunctionDueEnemyException {
        jackutSystem.addCrush(id, paquera);
    }

    /**
     * Obt�m as paqueras de um usu�rio.
     *
     * @param id O ID do usu�rio.
     * @return Uma lista das paqueras do usu�rio.
     * @throws UserCannotSendNoteToHimselfException Se o usu�rio tentar mandar uma mensagem para si mesmo.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws InvalidFunctionDueEnemyException Se a fun��o n�o for v�lida devido a um inimigo.
     */

    public String getPaqueras(String id) throws UserCannotSendNoteToHimselfException, UnregisteredUserException, InvalidFunctionDueEnemyException {
        return jackutSystem.getCrushs(id);
    }

    /**
     * Adiciona um inimigo a um usu�rio.
     *
     * @param id O ID do usu�rio que est� adicionando um inimigo.
     * @param inimigoNome O nome do inimigo a ser adicionado.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
     * @throws UserIsAlreadyYourEnemyException Se o usu�rio j� tiver a pessoa como inimigo.
     * @throws UserCannotBeAEnemyOfHimselfException Se o usu�rio tentar ser inimigo de si mesmo.
     */

    public void adicionarInimigo(String id, String inimigoNome) throws UnregisteredUserException, UserIsAlreadyYourEnemyException, UserCannotBeAEnemyOfHimselfException {
        jackutSystem.addEnemy(id, inimigoNome);
    }

    /**
     * Remove um usu�rio do sistema.
     *
     * @param id O ID do usu�rio a ser removido.
     * @throws UnregisteredUserException Se o usu�rio n�o estiver registrado.
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
