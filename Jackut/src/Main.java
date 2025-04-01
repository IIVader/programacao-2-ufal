import easyaccept.EasyAccept;

public class Main {
    public static void main(String[] args) {
        String facade = "br.ufal.ic.p2.jackut.Facade";
        String[] tests = {
                "tests/us1_1.txt",
                "tests/us1_2.txt",
                "tests/us2_1.txt",
                "tests/us2_2.txt",
                "tests/us3_1.txt",
                "tests/us3_2.txt",
                "tests/us4_1.txt",
                "tests/us4_2.txt",
        };

        for (String test : tests) {
            String[] argsEA = {facade, test};
            EasyAccept.main(argsEA);
        }
    }
}