package br.ufal.ic.p2.jackut.exceptions.jackutsystem;

public class InvalidFunctionDueEnemyException extends Exception{
    public InvalidFunctionDueEnemyException(String enemyName) {
        super("Função inválida: " + enemyName + " é seu inimigo.");
    }
}
