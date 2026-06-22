package gerenciador.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ComandoExecutor {

    public static void executar(String comando) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(comando.split(" "));
        executarProcesso(pb);
    }

    public static void executar(String... comandoEArgs) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(comandoEArgs);
        executarProcesso(pb);
    }

    private static void executarProcesso(ProcessBuilder pb) throws Exception {
        Process processo = pb.start();
        int exitCode = processo.waitFor();
        
        if (exitCode != 0) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()))) {
                String erroLog = errorReader.readLine();
                throw new Exception("Falha ao executar comando: " + (erroLog != null ? erroLog : "Código de saída: " + exitCode));
            }
        }
    }
}