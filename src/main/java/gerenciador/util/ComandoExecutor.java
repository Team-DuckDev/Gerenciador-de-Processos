package gerenciador.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ComandoExecutor {

    public static void executar(String comando) throws Exception {
        Process processo = Runtime.getRuntime().exec(comando);
        int exitCode = processo.waitFor();
        
        if (exitCode != 0) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
            String erroLog = errorReader.readLine();
            throw new Exception("Falha ao executar comando: " + (erroLog != null ? erroLog : ""));
        }
    }
}