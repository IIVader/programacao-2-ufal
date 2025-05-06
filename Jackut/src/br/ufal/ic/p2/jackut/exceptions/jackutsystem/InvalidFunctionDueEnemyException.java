package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

/**
 * Exce��o personalizada que indica que uma fun��o n�o pode ser executada
 * devido � presen�a de um inimigo. Esta classe estende {@link Exception} e
 * � lan�ada quando o usu�rio tenta realizar uma a��o que � inv�lida devido
 * � presen�a de um inimigo espec�fico.
 */

public class InvalidFunctionDueEnemyException extends Exception {

    /**
     * Construtor que cria uma nova inst�ncia da exce��o com uma mensagem personalizada.
     * A mensagem inclui o nome do inimigo, indicando que a fun��o � inv�lida devido a esse inimigo.
     *
     * @param enemyName O nome do inimigo que torna a fun��o inv�lida.
     */
    public InvalidFunctionDueEnemyException(String enemyName) {
        super("Fun��o inv�lida: " + enemyName + " � seu inimigo.");
    }
}
