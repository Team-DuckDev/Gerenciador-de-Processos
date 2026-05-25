Gerenciador de Processos Linux
Este é um projeto prático desenvolvido para a disciplina de Sistemas Operacionais. O objetivo é implementar um Gerenciador de Processos para ambiente GNU/Linux que permita entender na prática conceitos como time sharing, escalonamento de tarefas e estados de processos.

Funcionalidades:

O sistema interage diretamente com o SO para realizar as seguintes operações:

Visualização: Listagem dos processos ativos filtrados por um usuário específico.

Consulta de Detalhes: Exibição de informações em tempo real de um processo:

PID (Identificador)

NICE (Prioridade)

USER (Dono do processo)

TIME (Tempo de CPU consumido)

STATUS (Estado atual)

Controle de Estados: Envio de sinais para Bloquear, Continuar, Executar, Reiniciar e Finalizar processos.

Ajuste de Prioridade: Alteração do valor de NICE tanto para processos que já estão rodando quanto antes do início da execução.