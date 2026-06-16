package gerenciador.service;

import gerenciador.util.ComandoExecutor;

public class ProcessoEstadoService {

    // Tópico C: Bloquear [cite: 25]
    public static void bloquearProcesso(long pid) throws Exception {
        ComandoExecutor.executar("kill -STOP " + pid);
    }

    // Tópico C: Continuar [cite: 26]
    public static void continuarProcesso(long pid) throws Exception {
        ComandoExecutor.executar("kill -CONT " + pid);
    }

    // Tópico C: Finalizar [cite: 29]
    public static void finalizarProcesso(long pid) throws Exception {
        ComandoExecutor.executar("kill -15 " + pid); 
    }
    
    // Tópico C: Reiniciar [cite: 28]
    public static void reiniciarProcesso(long pid) throws Exception {
        ComandoExecutor.executar("kill -HUP " + pid);
    }

    //A partir daqui, as funções finalizam, bloqueam, desbloqueam e reiniciam o grupo de processos que pertecem ao app


    // Tópico C: Finalizar Árvore de Processos (App completo)
    // Irá finalizar todos processos daquele app
    public static void finalizarArvoreDeProcessos(long pid) throws Exception {
        // O comando 'ps -o pgid= -p PID' busca o ID do grupo do processo.
        // O sinal negativo antes do PGID (ex: kill -15 -PGID) avisa o Linux para matar o grupo todo.
        String comandoShell = "sh -c \"kill -15 -$(ps -o pgid= -p " + pid + " | tr -d ' ')\"";
        ComandoExecutor.executar(comandoShell);
}

// Bloquear toda a árvore
    public static void bloquearArvoreDeProcessos(long pid) throws Exception {
        String comandoShell = "sh -c \"kill -STOP -$(ps -o pgid= -p " + pid + " | tr -d ' ')\"";
        ComandoExecutor.executar(comandoShell);
    }

    // Continuar toda a árvore
    public static void continuarArvoreDeProcessos(long pid) throws Exception {
        String comandoShell = "sh -c \"kill -CONT -$(ps -o pgid= -p " + pid + " | tr -d ' ')\"";
        ComandoExecutor.executar(comandoShell);
    }

    // Reiniciar toda a árvore
    public static void reiniciarArvoreDeProcessos(long pid) throws Exception {
        String comandoShell = "sh -c \"kill -HUP -$(ps -o pgid= -p " + pid + " | tr -d ' ')\"";
        ComandoExecutor.executar(comandoShell);
    }
}