package gerenciador.service;

// Tópico D: Trocar prioridade de um processo em execução 

import gerenciador.util.ComandoExecutor;

public class ProcessoPrioridadeService {

    
    public static void alterarPrioridade(long pid, int novoNice) throws Exception {
        ComandoExecutor.executar("renice -n " + novoNice + " -p " + pid);
    }
}