package gerenciador.service;

// Tópico D: Trocar prioridade de um processo em execução 

import gerenciador.util.ComandoExecutor;

public class ProcessoPrioridadeService {

    
    public void alterarPrioridade(long pid, int novoNice) throws Exception {
        // Valores válidos no Linux: -20 a 19
        ComandoExecutor.executar("renice -n " + novoNice + " -p " + pid);
    }
}