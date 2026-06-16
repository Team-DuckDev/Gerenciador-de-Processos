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
}