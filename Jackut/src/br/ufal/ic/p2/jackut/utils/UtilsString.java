package br.ufal.ic.p2.jackut.utils;

import br.ufal.ic.p2.jackut.models.UserAccount;

import java.util.List;

/**
 * Classe utilitária para manipulação de strings relacionadas a listas de objetos.
 */

public class UtilsString {

    /**
     * Converte uma lista de objetos {@link UserAccount} em uma string formatada.
     *
     * @param arrayList a lista de {@link UserAccount} a ser formatada
     * @return uma representação em string da lista no formato {obj1,obj2,...,objN}, ou "{}" se a lista estiver vazia.
     */

    public static <T> String formatArrayList(List<UserAccount> arrayList) {
        if (arrayList.isEmpty()) {
            return "{}";
        }

        StringBuilder formattedString = new StringBuilder("{");
        for (int i = 0; i < arrayList.size(); i++) {
            formattedString.append(arrayList.get(i).toString());
            if (i != arrayList.size() - 1) {
                formattedString.append(",");
            }
        }
        formattedString.append("}");
        return formattedString.toString();
    }
}
