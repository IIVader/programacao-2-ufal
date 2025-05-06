package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exceção personalizada que indica que uma função não pode ser executada
 * devido à presença de um inimigo. Esta classe estende {@link Exception} e
 * é lançada quando o usuário tenta realizar uma ação que é inválida devido
 * à presença de um inimigo específico.
 */

public class InvalidFunctionDueEnemyException extends Exception {

    /**
     * Construtor que cria uma nova instância da exceção com uma mensagem personalizada.
     * A mensagem inclui o nome do inimigo, indicando que a função é inválida devido a esse inimigo.
     *
     * @param enemyName O nome do inimigo que torna a função inválida.
     */
    public InvalidFunctionDueEnemyException(String enemyName) {
        super("Função inválida: " + enemyName + " é seu inimigo.");
    }
}
