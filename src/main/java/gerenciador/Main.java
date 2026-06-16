package gerenciador;

import java.util.List;

import gerenciador.model.Processo;
import gerenciador.service.ProcessoConsultaService;
import gerenciador.service.ProcessoEstadoService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;


public class Main extends Application {

    ProcessoConsultaService consulta = new ProcessoConsultaService();
    String usuario = System.getProperty("user.name");

    @Override
    public void start(Stage stage) {
        System.out.println(usuario);
        List<Processo> processos = consulta.listarProcessosDoUsuario(usuario);

        VBox root = new VBox(10);

        // Criação da tabela
        TableView<Processo> tabela = createTable(processos);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> atualizarTabela(tabela))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        root.getChildren().add(tabela);
        Scene scene = new Scene(root, 300, 200);

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
        TableColumn<Processo, Integer> pidCol = new TableColumn<>("PID");
        pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));

        TableColumn<Processo, String> commandCol = new TableColumn<>("Nome");
        commandCol.setCellValueFactory(new PropertyValueFactory<>("command"));

        TableColumn<Processo, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Processo, Integer> timeCol = new TableColumn<>("Tempo");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Processo, String> userCol = new TableColumn<>("Usuario");
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableColumn<Processo, String> prioridadeCol = new TableColumn<>("Prioridade");
        prioridadeCol.setCellValueFactory(
            cellData -> new SimpleStringProperty(
                cellData.getValue().getPrioridadeDescricao()
            )
        );

        tabela.getColumns().addAll(pidCol, commandCol, statusCol, timeCol, userCol, prioridadeCol);
        tabela.getItems().addAll(processos);

        tabela.setRowFactory(tv -> creaTableRow());

        return tabela;
    }

    public TableRow<Processo> creaTableRow() {
        TableRow<Processo> row = new TableRow<>();

            ContextMenu contextMenu = new ContextMenu();

            MenuItem finalizarProcesso = new MenuItem("Finalizar Processo");
            MenuItem detalhes = new MenuItem("Ver Detalhes");
            MenuItem alterarPrioridade = new MenuItem("Alterar Prioridade");

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

            //detalhes.setOnAction(event -> {
            //    Processo processo = row.getItem();
            //    if (processo != null) {
            //        mostrarDetalhes(processo);
            //    }
            //});
//
            //alterarPrioridade.setOnAction(event -> {
            //    Processo processo = row.getItem();
            //    if (processo != null) {
            //        alterarPrioridade(processo);
            //    }
            //});
//
            contextMenu.getItems().addAll(
                    finalizarProcesso,
                    detalhes,
                    alterarPrioridade
            );

            row.setContextMenu(contextMenu);

            return row;
    }

}
