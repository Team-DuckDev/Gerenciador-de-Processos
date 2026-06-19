package gerenciador;

import java.util.List;

import gerenciador.model.Processo;
import gerenciador.service.ProcessoConsultaService;
import gerenciador.service.ProcessoEstadoService;
import gerenciador.service.ProcessoPrioridadeService;
import gerenciador.service.ProcessoExecucaoService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;


public class Main extends Application {

    ProcessoConsultaService consulta = new ProcessoConsultaService();
    //String usuario = System.getProperty("user.name");
    String usuario = "matheus-soares";

    @Override
    public void start(Stage stage) {
        List<Processo> processos = consulta.listarProcessosDoUsuario(usuario);

        Label titulo = new Label("Gerenciador de Processos");
        titulo.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
        """);

        TableView<Processo> tabela = createTable(processos);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> atualizarTabela(tabela))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        TextField comandoField = new TextField();
        comandoField.setPromptText("Digite o comando do processo");

        ComboBox<String> prioridadeCombo = new ComboBox<>();
        prioridadeCombo.getItems().addAll(
            "Muito baixa",
            "Baixa",
            "Normal"
        );
        prioridadeCombo.setValue("Normal");

        Button executarButton = new Button("Executar");

        executarButton.setOnAction(event -> {
            String comando = comandoField.getText();

            if (comando == null || comando.isBlank()) {
                return;
            }

            try {
                String prioridade = prioridadeCombo.getValue();

                switch (prioridade) {
                    case "Muito baixa":
                        ProcessoExecucaoService.executarNovoProcessoComPrioridade(comando, 19);
                        break;

                    case "Baixa":
                        ProcessoExecucaoService.executarNovoProcessoComPrioridade(comando, 6);
                        break;

                    default:
                        ProcessoExecucaoService.executarNovoProcesso(comando);
                        break;
                }

                comandoField.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox painelExecucao = new HBox(10);
        painelExecucao.getChildren().addAll(
            comandoField,
            prioridadeCombo,
            executarButton
        );

        VBox root = new VBox(15);

        root.setStyle("""
            -fx-background-color: #1E1E1E;
            -fx-padding: 20;
        """);

        root.getChildren().addAll(titulo, painelExecucao, tabela);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Scene scene = new Scene(root, 1200, 700);

        stage.setTitle("Gerenciador de Processos");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public void atualizarTabela(TableView<Processo> tabela) {
        List<Processo> processos = consulta.listarProcessosDoUsuario(usuario);
        tabela.setItems(FXCollections.observableArrayList(processos));
    }

    public TableView<Processo> createTable(List<Processo> processos) {

        TableView<Processo> tabela = new TableView<>();

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        tabela.setStyle("""
            -fx-background-color: #252526;
            -fx-control-inner-background: #252526;
            -fx-table-cell-border-color: transparent;
            -fx-font-size: 13px;
        """);

        TableColumn<Processo, Integer> pidCol = new TableColumn<>("PID");
        pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));

        TableColumn<Processo, String> commandCol = new TableColumn<>("Processo");
        commandCol.setCellValueFactory(new PropertyValueFactory<>("command"));

        TableColumn<Processo, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Processo, Integer> timeCol = new TableColumn<>("CPU");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Processo, String> userCol = new TableColumn<>("Usuário");
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableColumn<Processo, String> prioridadeCol = new TableColumn<>("Prioridade");
        prioridadeCol.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                cellData.getValue().getPrioridadeDescricao()
            )
        );

        tabela.getColumns().addAll(pidCol, commandCol, statusCol, timeCol, userCol, prioridadeCol);

        tabela.setItems(FXCollections.observableArrayList(processos));

        tabela.setRowFactory(tv -> createStyledRow());

        return tabela;
    }

    public TableRow<Processo> creaTableRow() {
        TableRow<Processo> row = new TableRow<>();

            ContextMenu contextMenu = new ContextMenu();

            MenuItem finalizarProcesso = new MenuItem("Finalizar Processo");
            MenuItem bloquearProcesso = new MenuItem("Bloquear Processo");
            MenuItem reiniciarProcesso = new MenuItem("Reiniciar Processo");
            MenuItem continuarProcesso = new MenuItem("Continuar processo");

            finalizarProcesso.setOnAction(event -> {
                Processo processo = row.getItem();
                if (processo != null) {
                    try {
                        ProcessoEstadoService.finalizarProcesso(processo.getPid());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            bloquearProcesso.setOnAction(event -> {
                Processo processo = row.getItem();
                if (processo != null) {
                    try {
                        ProcessoEstadoService.bloquearProcesso(processo.getPid());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            reiniciarProcesso.setOnAction(event -> {
                Processo processo = row.getItem();
                if (processo != null) {
                    try {
                        ProcessoEstadoService.reiniciarProcesso(processo.getPid());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            continuarProcesso.setOnAction(event -> {
                Processo processo = row.getItem();
                if (processo != null) {
                    try {
                        ProcessoEstadoService.continuarProcesso(processo.getPid());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            contextMenu.getItems().addAll(
                    finalizarProcesso,
                    bloquearProcesso,
                    reiniciarProcesso,
                    continuarProcesso,
                    new SeparatorMenuItem(),
                    createAlterarPrioridadeMenu(row)
            );

            row.setContextMenu(contextMenu);

            return row;
    }

    public TableRow<Processo> createStyledRow() {

        TableRow<Processo> row = creaTableRow();

        row.itemProperty().addListener((obs, oldItem, processo) -> {

            if (processo == null) {
                row.setStyle("");
                return;
            }

            String status = processo.getStatus();

            switch (status.toUpperCase()) {

                case "RUNNING":
                    row.setStyle("""
                        -fx-background-color: rgba(46,204,113,0.15);
                        -fx-text-fill: white;
                    """);
                    break;

                case "STOPPED":
                    row.setStyle("""
                        -fx-background-color: rgba(231,76,60,0.15);
                        -fx-text-fill: white;
                    """);
                    break;

                case "SLEEPING":
                    row.setStyle("""
                        -fx-background-color: rgba(0,20,105,0.15);
                        -fx-text-fill: white;
                    """);
                    break;

                default:
                    row.setStyle("""
                        -fx-background-color: #252526;
                        -fx-text-fill: white;
                    """);
            }
        });

        return row;
    }

    public Menu createAlterarPrioridadeMenu(TableRow<Processo> row) {
        Menu alterarPrioridade = new Menu("Alterar Prioridade");

        MenuItem prioridadeMuitoBaixa = new MenuItem("Muito baixa");
        MenuItem prioridadeBaixa = new MenuItem("Baixa");
        MenuItem prioridadeNormal = new MenuItem("Normal");
        MenuItem prioridadeAlta = new MenuItem("Alta");
        MenuItem prioridadeMuitoAlta = new MenuItem("Muito alta");

        prioridadeMuitoBaixa.setOnAction(event -> {
            Processo processo = row.getItem();
            if (processo != null) {
                try {
                    ProcessoPrioridadeService.alterarPrioridade(processo.getPid(), 19);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prioridadeBaixa.setOnAction(event -> {
            Processo processo = row.getItem();
            if (processo != null) {
                try {
                    ProcessoPrioridadeService.alterarPrioridade(processo.getPid(), 6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prioridadeNormal.setOnAction(event -> {
            Processo processo = row.getItem();
            if (processo != null) {
                try {
                    ProcessoPrioridadeService.alterarPrioridade(processo.getPid(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prioridadeAlta.setOnAction(event -> {
            Processo processo = row.getItem();
            if (processo != null) {
                try {
                    ProcessoPrioridadeService.alterarPrioridade(processo.getPid(), -6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prioridadeMuitoAlta.setOnAction(event -> {
            Processo processo = row.getItem();
            if (processo != null) {
                try {
                    ProcessoPrioridadeService.alterarPrioridade(processo.getPid(), -20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        

        alterarPrioridade.getItems().addAll(
            prioridadeMuitoBaixa,
            prioridadeBaixa,
            prioridadeNormal,
            prioridadeAlta,
            prioridadeMuitoAlta
        );

        return alterarPrioridade;
    }

}
