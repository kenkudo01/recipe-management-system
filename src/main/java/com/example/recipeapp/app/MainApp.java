package com.example.recipeapp.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final double MAIN_SCREEN_WIDTH = 1200;
    private static final double MAIN_SCREEN_HEIGHT = 600;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/recipeapp/view/MainView.fxml")
        );

        Scene scene = new Scene(loader.load(), MAIN_SCREEN_WIDTH, MAIN_SCREEN_HEIGHT);
        stage.setTitle("Recipe Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
