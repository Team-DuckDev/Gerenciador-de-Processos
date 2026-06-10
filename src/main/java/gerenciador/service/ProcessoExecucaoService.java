package gerenciador.service;

// Tópico C e D: Executar novo processo [cite: 27]
//Trocar prioridade ANTES de executar um processo
public class ProcessoExecucaoService {

    public void executarNovoProcesso(String comando) throws Exception {
        Runtime.getRuntime().exec(comando);
    }

    public void executarNovoProcessoComPrioridade(String comando, int nice) throws Exception {
        String comandoCompleto = "nice -n " + nice + " " + comando;
        Runtime.getRuntime().exec(comandoCompleto);
    }
}