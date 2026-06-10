package gerenciador.service;

import java.util.ArrayList;
import java.util.List;

import gerenciador.model.Processo;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

// Tópico A e B: Listar processos de um usuário e consultar informações 

public class ProcessoConsultaService {

    private final OperatingSystem os;

    public ProcessoConsultaService() {
        SystemInfo si = new SystemInfo();
        this.os = si.getOperatingSystem();
    }

    public List<Processo> listarProcessosDoUsuario(String usuarioDesejado) {
        List<Processo> listaProcessos = new ArrayList<>();
        List<OSProcess> processosOS = os.getProcesses(null, null, 0);

        for (OSProcess p : processosOS) {
            if (usuarioDesejado.isBlank() || p.getUser().equalsIgnoreCase(usuarioDesejado)) {
                Processo proc = new Processo();
                proc.setPid(p.getProcessID()); 
                proc.setNice(p.getPriority()); 
                proc.setUser(p.getUser());     
                proc.setStatus(p.getState().name()); 
                proc.setCommand(p.getName());

                long cpuTimeSeconds = (p.getUserTime() + p.getKernelTime()) / 1000; // TIME [cite: 22]
                proc.setTime(cpuTimeSeconds);

                listaProcessos.add(proc);
            }
        }
        return listaProcessos;
    }
}