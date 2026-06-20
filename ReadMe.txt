\|Gerenciador de Processos Linux|/
Este é um projeto prático desenvolvido para a disciplina de Sistemas Operacionais. O objetivo é implementar um Gerenciador de Processos (GP) para ambiente GNU/Linux que permita entender na prática conceitos como time sharing, escalonamento de tarefas e estados de processos.

\|Funcionalidades:

O sistema interage diretamente com o SO para realizar as seguintes operações:

_Visualização: Listagem dos processos ativos filtrados por um usuário específico.

_Consulta de Detalhes: Exibição de informações em tempo real de um processo:

PID (Identificador)

NICE (Prioridade)

USER (Dono do processo)

TIME (Tempo de CPU consumido)

STATUS (Estado atual)

_Controle de Estados: Envio de sinais para Bloquear, Continuar, Executar, Reiniciar e Finalizar processos.

_Ajuste de Prioridade: Alteração do valor de NICE tanto para processos que já estão rodando quanto antes do início da execução.


\|Estrutura Principal do Projeto:

Gerenciador-de-Processos/
├── pom.xml
├── ReadMe.txt
└── src/
    └── main/
        └── java/
            └── gerenciador/
                ├── Main.java
                ├── model/
                │   └── Processo.java
                ├── service/
                │   ├── ProcessoConsultaService.java
                │   ├── ProcessoEstadoService.java
                │   ├── ProcessoExecucaoService.java
                │   └── ProcessoPrioridadeService.java
                ├── util/
                │   └── ComandoExecutor.java
                └── view/
                    └── read.txt

_Linguagem : Java
_Apache Maven: para automação de compilação e gerenciamento de dependências.

>>Ao usar o GP como usuário comum, não é possível usar todos os recursos oferecidos por ele<<

_pom.xml : Guarda as configurações do projeto, versão do Java e dependências.
_Processo.java : Representa um Processo e seus atributos no SO.Fornece informações de prioridade, construtor para criação de Processos, etc.

_ProcessoConsultaService.java : Lista os processos ativos e puxa suas informações (incluindo usuário e tempo de CPU).
_ProcessoEstadoService.java : Controla o Status dos processos. Pausa, despausa, fecha ou reinicia um processo individual ou um grupo inteiro de uma vez.
_ProcessoExecucaoService.java : Inicia um processo, permitindo definir sua prioridade inicial.
_ProcessoPrioridadeService.java : Altera a prioridade de um processo que já está rodando.

_ComandoExecutor.java : Recebe um comando e o executa em sagundo plano no SO. Espera o término da execução do comando para voltar a rodar o Java. Trata erro de execução.
