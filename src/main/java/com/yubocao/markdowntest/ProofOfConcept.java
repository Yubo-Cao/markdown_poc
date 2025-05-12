package com.yubocao.markdowntest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProofOfConcept extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        SelectableTextFlow textFlow = new SelectableTextFlow(root);

        textFlow.addText(bold("Hello"), plain(", "), italic("world"), plain("!\n"));
        textFlow.addText(plain("Here is some "), code("inline code"), plain(".\n"));
        textFlow.addText(plain("This is "), bold("bold"), plain(" and this is "), italic("italic"), plain("."));

        textFlow.setLayoutX(50);
        textFlow.setLayoutY(50);

        root.getChildren().add(textFlow);

        Scene scene = new Scene(root, 400, 300);

        textFlow.setupCopyAccelerator(scene);

        stage.setTitle("Markdown Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Text bold(String content) {
        Text text = new Text(content);
        text.setStyle("-fx-font-weight: bold; -fx-font-family: 'Microsoft YaHei UI'");
        return text;
    }

    private Text italic(String content) {
        Text text = new Text(content);
        text.setStyle("-fx-font-style: italic; -fx-font-family: 'Microsoft YaHei UI'");
        return text;
    }

    private Text code(String content) {
        Text text = new Text(content);
        text.setStyle("-fx-font-family: 'Maple Mono NF CN'; -fx-fill: #c41e3a");
        return text;
    }

    private Text plain(String content) {
        Text text = new Text(content);
        text.setStyle("-fx-font-family: 'Microsoft YaHei UI'");
        return text;
    }
}