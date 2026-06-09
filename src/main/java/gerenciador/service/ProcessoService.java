package gerenciador.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gerenciador.model.Processo;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class ProcessoService {

    private final OperatingSystem os;

    public ProcessoService() {
        // Leitura do Sistema Operacional usando a biblioteca OSHI
        SystemInfo si = new SystemInfo();
        this.os = si.getOperatingSystem();
    }

    // a) e b) Listar processos e consultar informações
    public List<Processo> listarProcessosDoUsuario(String usuarioDesejado) {
        List<Processo> listaProcessos = new ArrayList<>();
        
        // Pega todos os processos rodando no Linux. 
        // Os argumentos (null, null, 0) servem para trazer todos sem filtro prévio na API 6.x
        List<OSProcess> processosOS = os.getProcesses(null, null, 0);

        for (OSProcess p : processosOS) {
            // Filtra pelo usuário exato (ou traz tudo se o usuário passar uma string vazia)
            if (usuarioDesejado.isBlank() || p.getUser().equalsIgnoreCase(usuarioDesejado)) {
                
                Processo proc = new Processo();
                proc.setPid(p.getProcessID());
                proc.setUser(p.getUser());
                proc.setCommand(p.getName());
                
                // OSHI traduz o State do Linux para um Enum (RUNNING, SLEEPING, STOPPED, etc.)
                proc.setStatus(p.getState().name());
                
                // Priority no OSHI se alinha ao NICE no Linux para processos de usuário
                proc.setNice(p.getPriority()); 
                
                // Converte milissegundos de tempo de CPU para segundos
                long cpuTimeSeconds = (p.getUserTime() + p.getKernelTime()) / 1000;
                proc.setTime(cpuTimeSeconds);

                listaProcessos.add(proc);
            }
        }
        return listaProcessos;
    }

    // c) Alterar estado de um processo (Sinais de controle POSIX)

    public void bloquearProcesso(long pid) throws Exception {
        executarComandoNoTerminal("kill -STOP " + pid);
    }

    public void continuarProcesso(long pid) throws Exception {
        executarComandoNoTerminal("kill -CONT " + pid);
    }

    public void finalizarProcesso(long pid) throws Exception {
        executarComandoNoTerminal("kill -15 " + pid); 
    }
    
    public void reiniciarProcesso(long pid) throws Exception {
        executarComandoNoTerminal("kill -HUP " + pid);
    }

    // d) Trocar prioridade de execução (NICE)
    public void alterarPrioridade(long pid, int novoNice) throws Exception {
        // Valores válidos no Linux: -20 (mais prioritário) a 19 (menos prioritário)
        executarComandoNoTerminal("renice -n " + novoNice + " -p " + pid);
    }

    // --- Motor de Execução de Comandos ---
    
    private void executarComandoNoTerminal(String comando) throws Exception {
        Process processo = Runtime.getRuntime().exec(comando);
        
        // Aguarda o comando finalizar para saber se deu erro
        int exitCode = processo.waitFor();
        
        if (exitCode != 0) {
            // Se cair aqui, a pessoa na interface precisa ver uma mensagem de erro
            // O getErrorStream pega a mensagem nativa do terminal do Linux
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
            String erroLog = errorReader.readLine();
            throw new Exception("Falha ao executar: " + (erroLog != null ? erroLog : "Acesso negado ou PID inválido."));
        }
    }
}