package br.ufal.ic.p2.jackut.utils;

import br.ufal.ic.p2.jackut.models.UserAccount;

import java.io.*;
import java.util.HashMap;

/**
 * Classe utilitária para serialização e desserialização de objetos.
 * Essa classe permite armazenar e recuperar objetos do tipo {@link UserAccount}
 */

public class Serealization {

    /**
     * Caminho do diretório onde os arquivos de serialização serão armazenados.
     */

    private static String pathFile = "src/br/ufal/ic/p2/jackut/database";

    /**
     * Serializa um objeto do tipo {@link HashMap} contendo contas de usuários.
     *
     * @param accounts Mapa contendo os usuários a serem serializados.
     * @param fileName Nome do arquivo onde os dados serão armazenados.
     */

    public static void serealizeObject(HashMap<String, UserAccount> accounts, String fileName) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pathFile + File.separator + fileName + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa um objeto do tipo {@code HashMap<String, UserAccount>} a partir de um arquivo.
     *
     * <p>O método tenta carregar um arquivo serializado contendo um mapa de usuários.
     * Se o arquivo não existir, um novo {@code HashMap} vazio será retornado.</p>
     *
     * @param fileName O nome do arquivo (sem a extensão) a ser desserializado.
     * @return Um {@code HashMap<String, UserAccount>} contendo os usuários armazenados no arquivo
     *         ou um mapa vazio se o arquivo não for encontrado ou ocorrer um erro na desserialização.
     */

    public static HashMap<String, UserAccount> deserializeObject(String fileName) {

        try {
            File file = new File(pathFile + File.separator + fileName + ".ser");
            if (!file.exists()) {
                return new HashMap<>();
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathFile + File.separator + fileName + ".ser"));
            return (HashMap<String, UserAccount>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
