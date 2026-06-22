package gerenciador.service;

import java.util.ArrayList;
import java.util.List;

public class ProcessoExecucaoService {

    public static void executarNovoProcesso(String comando) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.start();
    }

    public static void executarNovoProcessoComPrioridade(String comando, int nice) throws Exception {
        List<String> comandoCompleto = new ArrayList<>();
        comandoCompleto.add("nice");
        comandoCompleto.add("-n");
        comandoCompleto.add(String.valueOf(nice));
        
        for (String arg : comando.split(" ")) {
            comandoCompleto.add(arg);
        }

        ProcessBuilder pb = new ProcessBuilder(comandoCompleto);
        pb.start();
    }
}