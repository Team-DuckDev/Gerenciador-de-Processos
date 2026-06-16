package gerenciador;

import java.util.List;

import gerenciador.model.Processo;
import gerenciador.service.ProcessoConsultaService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    ProcessoConsultaService consulta = new ProcessoConsultaService();

    @Override
    public void start(Stage stage) {
        List<Processo> processos = consulta.listarProcessosDoUsuario("mathe");

        VBox root = new VBox(10);

        // Criação da tabela
        TableView<Processo> tabela = new TableView<>();
        TableColumn<Processo, Integer> pidCol = new TableColumn<>("PID");
        pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
        TableColumn<Processo, String> commandCol = new TableColumn<>("Nome");
        commandCol.setCellValueFactory(new PropertyValueFactory<>("command"));
        TableColumn<Processo, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tabela.getColumns().addAll(pidCol, commandCol, statusCol);
        tabela.getItems().addAll(processos);

        root.getChildren().add(tabela);
        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("Gerenciador de Processos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
