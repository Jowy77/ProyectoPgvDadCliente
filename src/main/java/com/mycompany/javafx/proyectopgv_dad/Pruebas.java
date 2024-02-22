/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafx.proyectopgv_dad;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class Pruebas extends Application {

    private XYChart.Series<Number, Number> series = new XYChart.Series<>();

    @Override
    public void start(Stage stage) {
        // Configurar el eje X
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Tiempo");

        // Configurar el eje Y
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Porcentaje de uso de la CPU");

        // Crear el LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Porcentaje de uso de la CPU");

        // Agregar la serie de datos al LineChart
        lineChart.getData().add(series);

        // Crear un Timeline para actualizar periódicamente los datos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    double cpuUsage = getCPULoad();
                    series.getData().add(new XYChart.Data<>(System.currentTimeMillis(), cpuUsage));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Configurar la escena
        Scene scene = new Scene(lineChart, 800, 600);

        // Configurar el escenario
        stage.setScene(scene);
        stage.setTitle("CPU Usage Line Chart");
        stage.show();
    }

    private double getCPULoad() {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] loadTicks = processor.getSystemCpuLoadTicks();
        return processor.getSystemCpuLoadBetweenTicks(loadTicks) * 100;
    }

    public static void main(String[] args) {
        launch(args);
    }
}