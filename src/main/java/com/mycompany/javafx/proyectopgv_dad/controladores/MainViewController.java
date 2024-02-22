package com.mycompany.javafx.proyectopgv_dad.controladores;

import com.google.gson.Gson;
import com.mycompany.javafx.proyectopgv_dad.App;
import com.mycompany.javafx.proyectopgv_dad_servidor.modelo.ModeloServidor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.mycompany.javafx.proyectopgv_dad.utils.Utils;
import com.mycompany.javafx.proyectopgv_dad_servidor.modelo.Proceso;
import com.mycompany.javafx.proyectopgv_dad_servidor.modelo.Servicio;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oshi.SystemInfo;

/**
 * FXML Controller class
 *
 * @author Iproy
 */
public class MainViewController implements Initializable {

    private Stage stage;
    
    private Scene scene;
    
    private Parent root;

    private static final Gson gson = new Gson();

    XYChart.Series series;

    XYChart.Series seriesRed;

    private SystemInfo system;

    String mensajeReporte = "";

    public static List<ModeloServidor> servidoresConectados = new ArrayList<>();

    private double xOffset = 0;
    
    private double yOffset = 0;
    
    double umbralRam;
    
    double umbralCpu;

    @FXML
    private Label sistemaOperativoLabel;
    @FXML
    private Label procesadorLabel;
    @FXML
    private Label discoLabel;
    @FXML
    private Label redLabel;
    @FXML
    private Label memoriaLabel;
    @FXML
    private Label porcentajeCPULabel;
    @FXML
    private LineChart<?, ?> lineChartCPU;
    @FXML
    private Label memoriaRamTotalLabel;
    @FXML
    private Label memoriaRamUsadaLabel;
    @FXML
    private ProgressBar memoriaProgresBar;
    @FXML
    private Label memoriaProgressLabel;
    @FXML
    private Label nombreDiscoLabel;
    @FXML
    private Label espacioTotalDiscoLabel;
    @FXML
    private Label espacioDisponibleDiscoLabel;
    @FXML
    private PieChart discoDuroPieChart;
    @FXML
    private TableView<Proceso> tableViewProcess;
    @FXML
    private TableView<Servicio> tableViewServices;
    @FXML
    private Label ipLabel;
    @FXML
    private Label macLabel;
    @FXML
    private LineChart<Number, Number> redLineChart;
    @FXML
    private ListView<String> servidoresListView;
    @FXML
    private Label temperaturaLabel;

    /**
     * Inicializa el controlador después de que su raíz FXML ha sido
     * completamente procesada.
     *
     * @param url URL de la raíz del objeto de recursos
     * @param rb El ResourceBundle que se puede localizar con la raíz
     * especificada de la URL o null si no hay ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        umbralCpu = 50.0;
        umbralRam = 50.0;
        
        // Inicialización de SystemInfo para obtener información del sistema
        system = new SystemInfo();

        // Configuración de la etiqueta del eje Y del gráfico de línea de la CPU
        lineChartCPU.getYAxis().setLabel("Porcentaje de uso de la CPU");

        // Inicialización de los datos de procesos y servicios
        datosProcesos();
        datosServicios();

        // Creación de una nueva serie para el gráfico de línea de la CPU y añadirla al gráfico
        series = new XYChart.Series();
        lineChartCPU.getData().add(series);

        // Creación de una nueva serie para el gráfico de línea de la red y añadirla al gráfico
        seriesRed = new XYChart.Series();
        redLineChart.getData().add(seriesRed);

        // Configuración de la etiqueta del eje Y del gráfico de línea de la red
        redLineChart.getYAxis().setLabel("Uso de red (trafico)");

        // Creación de un hilo para el servidor
        Thread servidorThread = new Thread(() -> {
            try {
                // Creación de un socket del servidor en el puerto 12345
                ServerSocket servidorSocket = new ServerSocket(12345);
                System.out.println("Servidor central a la espera de conexiones...");

                while (true) {
                    // Esperar y aceptar una conexión entrante de un cliente
                    Socket clienteSocket = servidorSocket.accept();
                    System.out.println("Nuevo servidor conectado");

                    // Obtener el JSON enviado por el servidor remoto a través del socket
                    BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                    String json = in.readLine();

                    // Convertir el JSON a un objeto ModeloServidor utilizando Gson
                    ModeloServidor datosServidor = gson.fromJson(json, ModeloServidor.class);

                    // Verificar si el servidor ya está en la lista de servidores conectados
                    boolean encontrado = false;
                    for (ModeloServidor servidor : servidoresConectados) {
                        if (servidor.getNombreServidor().equals(datosServidor.getNombreServidor())) {
                            encontrado = true;
                            // Actualizar los datos del servidor existente en la lista
                            servidor.setDireccionIP(datosServidor.getDireccionIP());
                            servidor.setDireccionMac(datosServidor.getDireccionMac());
                            servidor.setEspacioDisponibleDisco(datosServidor.getEspacioDisponibleDisco());
                            servidor.setEspacioTotalEnDisco(datosServidor.getEspacioTotalEnDisco());
                            servidor.setInfoDisco(datosServidor.getInfoDisco());
                            servidor.setMemoriaRamTotal(datosServidor.getMemoriaRamTotal());
                            servidor.setMemoriaRamUsada(datosServidor.getMemoriaRamUsada());
                            servidor.setObetenerInfoDelSistema(datosServidor.getObetenerInfoDelSistema());
                            servidor.setPorcentajeRamUsado(datosServidor.getPorcentajeRamUsado());
                            servidor.setPorcentajeUsoProcesador(datosServidor.getPorcentajeUsoProcesador());
                            servidor.setUsoTotalDeRed(datosServidor.getUsoTotalDeRed());
                            servidor.setPorcentajeDoubleProcesador(datosServidor.getPorcentajeDoubleProcesador());
                            servidor.setProcesos(datosServidor.getProcesos());
                            servidor.setServicios(datosServidor.getServicios());
                            break;
                        }
                    }

                    // Si el servidor no está en la lista, se agrega
                    if (!encontrado) {
                        agregarServidorConectado(datosServidor);
                    }

                    // Imprimir los nombres de los servidores conectados
                    servidoresConectados.forEach(servidor -> System.out.println(servidor.getNombreServidor() + " - " + servidor.getMemoriaRamUsada()));

                    // Esperar 5 segundos antes de aceptar la siguiente conexión
                    TimeUnit.SECONDS.sleep(5);

                    // Cerrar el socket del cliente
                    clienteSocket.close();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        //INDEX PARA CONTROLAR EL CAMBO DE SERVIDOR
        AtomicInteger indexNuevo = new AtomicInteger(-1);

        //HILO PARA PINTAR LA PANTALLA
        Thread actualizarVistaThread = new Thread(() -> {

            while (true) {

                Platform.runLater(() -> {

                    int selectedIndex = servidoresListView.getSelectionModel().getSelectedIndex();

                    if (selectedIndex > -1) {

                        if (selectedIndex != indexNuevo.get()) {
                            //PARA LIMPIAR LAS GRAFICAS AL CAMBIAR DE SERVER
                            series.getData().clear();
                            seriesRed.getData().clear();

                            //PARA PINTAR EL PIE CHAR AL CAMBIAR DE DE SERVER CON LA INFORMACION DEL DISCO
                            discoDuroPieChart.getData().clear();

                            ObservableList<PieChart.Data> pieChartData
                                    = FXCollections.observableArrayList(
                                            new PieChart.Data("Disponible: ", servidoresConectados.get(selectedIndex).getEspacioTotalEnDisco()),
                                            new PieChart.Data("Total: ", servidoresConectados.get(selectedIndex).getEspacioDisponibleDisco()));

                            pieChartData.forEach(data
                                    -> data.nameProperty().bind(
                                            Bindings.concat(data.getName(), "", data.pieValueProperty())
                                    )
                            );

                            discoDuroPieChart.getData().addAll(pieChartData);

                            indexNuevo.set(selectedIndex);
                        }

                        //ACTUALIZAR DATOS DEL SERVIDOR SELECCIONADO DE SERVIDOR
                        String[] datosIniciales = servidoresConectados.get(selectedIndex).getObetenerInfoDelSistema();

                        sistemaOperativoLabel.setText(datosIniciales[0]);
                        procesadorLabel.setText(datosIniciales[1]);
                        discoLabel.setText(datosIniciales[2]);
                        redLabel.setText(datosIniciales[3]);
                        memoriaLabel.setText(datosIniciales[4]);

                        mensajeReporte = "REPORTE DEL SERVIDOR: " + servidoresConectados.get(selectedIndex).getNombreServidor() + "\n"
                                + "SISTEMA OPERATIVO: " + datosIniciales[0] + "\n"
                                + "PROCESADOR: " + datosIniciales[1] + "\n"
                                + "DISCO DURO: " + datosIniciales[2] + "\n"
                                + "RED: " + datosIniciales[3] + "\n"
                                + "MEMORIA TOTAL DEL EQUIPO: " + datosIniciales[4];

                        //TAB CPU
                        double usoCPUDouble = servidoresConectados.get(selectedIndex).getPorcentajeDoubleProcesador();
                        //String porcentajeDeUsoCPU = servidoresConectados.get(selectedIndex).getPorcentajeUsoProcesador();
                        porcentajeCPULabel.setText(String.format("%.2f", usoCPUDouble) + "%");

                        series.getData().add(new XYChart.Data<>(String.valueOf(series.getData().size() + 1), usoCPUDouble));

                        if (usoCPUDouble > umbralCpu) {
                            Utils.mostrarInformacion("CUIDADO CPU", "EL PORCENTAJE DE USO DE LA CPU HA PASADO DEL" + umbralCpu+ "%");
                        }

                        //-----------------------------------------
                        //TAB MEMORIA
                        long memoriaRamUsada = servidoresConectados.get(selectedIndex).getMemoriaRamUsada();
                        long memoriaRamTotal = servidoresConectados.get(selectedIndex).getMemoriaRamTotal();
                        
                        memoriaRamUsadaLabel.setText(Utils.formatBytes(memoriaRamUsada));
                        memoriaRamTotalLabel.setText(Utils.formatBytes(memoriaRamTotal));
                        
                        double porcentajeRamUsado = ((double)memoriaRamUsada/(double)memoriaRamTotal) * 100;
                        
                        memoriaProgresBar.setProgress(porcentajeRamUsado / 100);
                        memoriaProgressLabel.setText(String.format("%.2f", porcentajeRamUsado) + " % de RAM usado");

                        if (porcentajeRamUsado > umbralRam) {
                            Utils.mostrarInformacion("CUIDADO RAM", "EL PORCENTAJE USO DE LA MEMORIA RAM HA PASADO DEL " + umbralRam + "%");
                        }

                        String[] discoDuroInfo = servidoresConectados.get(selectedIndex).getInfoDisco();

                        nombreDiscoLabel.setText(discoDuroInfo[0]);
                        espacioTotalDiscoLabel.setText(discoDuroInfo[1]);
                        espacioDisponibleDiscoLabel.setText(discoDuroInfo[2]);

                        //-----------------------------------------
                        //TAB PROCESOS/SERVICIOS
                        tableViewProcess.getItems().clear();
                        List<Proceso> procesos = servidoresConectados.get(selectedIndex).getProcesos();
                        ObservableList<Proceso> observableProcesos = FXCollections.observableArrayList(procesos);
                        observableProcesos.sort(Comparator.comparing(oSProcess -> oSProcess.getPrioridad()));
                        tableViewProcess.setItems(observableProcesos);

                        tableViewServices.getItems().clear();
                        List<Servicio> servicios = servidoresConectados.get(selectedIndex).getServicios();
                        ObservableList<Servicio> observableServicios = FXCollections.observableArrayList(servicios);
                        observableServicios.sort(Comparator.comparing(osService -> osService.getPID()));
                        tableViewServices.setItems(observableServicios);

                        /*refrescoDeDatosProceso();
                        refrescoDatosDeServicio();*/
                        //-----------------------------------------
                        //TAB RED
                        ipLabel.setText(servidoresConectados.get(selectedIndex).getDireccionIP()[0]);
                        macLabel.setText(servidoresConectados.get(selectedIndex).getDireccionMac());

                        seriesRed.getData().add(new XYChart.Data<>(String.valueOf(seriesRed.getData().size() + 1), Utils.formatBytesToDouble(servidoresConectados.get(selectedIndex).getUsoTotalDeRed())));
                        //-----------------------------------------
                    }
                });

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        servidorThread.setDaemon(true);
        servidorThread.start();

        actualizarVistaThread.setDaemon(true);
        actualizarVistaThread.start();
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void enviarCorreo(ActionEvent event) {
        Utils.mostrarInformacion("e-mail", "Se ha enviado un e-mail a tu cuenta de google con algo de informacion sobre el servidor(S.O.,RAM,CPU...).");
    }

    @FXML
    private void volverListaServers(ActionEvent event) {
        try {
            root = App.loadFXML("LoginView");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            //ESTO ES PARA PODER ARRASTRAR LA PANTALL CLICKANDO
            scene.getRoot().setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            scene.getRoot().setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //ESTE METODO PINTA ASIGNA LAS COLUMNAS DE LA TABLA PROCESOS
    private void datosProcesos() {

        TableColumn<Proceso, String> pidColumn = new TableColumn<>("PID");
        pidColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPID()));

        TableColumn<Proceso, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        TableColumn<Proceso, String> stateColumn = new TableColumn<>("Estado");
        stateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado()));

        TableColumn<Proceso, String> priorityColumn = new TableColumn<>("Prioridad");
        priorityColumn.setCellValueFactory(data -> {
            return new SimpleStringProperty(data.getValue().getPrioridad());
        });
        tableViewProcess.getColumns().addAll(nameColumn, pidColumn, stateColumn, priorityColumn);
        tableViewProcess.getSortOrder().add(nameColumn);
    }

    //ESTE METODO PINTA ASIGNA LAS COLUMNAS DE LA TABLA SERVICIOS
    private void datosServicios() {

        TableColumn<Servicio, String> nameServiceColumn = new TableColumn<>("Nombre");
        nameServiceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        TableColumn<Servicio, String> servicePIDNameColumn = new TableColumn<>("PID");
        servicePIDNameColumn.setCellValueFactory(data -> {

            return new SimpleStringProperty(data.getValue().getPID());
        });

        TableColumn<Servicio, String> serviceStateColumn = new TableColumn<>("Estado del servicio");
        serviceStateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstadoDelServicio()));

        tableViewServices.getColumns().addAll(nameServiceColumn, servicePIDNameColumn, serviceStateColumn);
        tableViewServices.getSortOrder().add(nameServiceColumn);
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        Utils.mostrarInformacion("REPORTE", mensajeReporte);
    }

    @FXML
    private void conseguirAyuda(ActionEvent event) {
        Utils.mostrarInformacion("Help", "Si necesitas cualquier tipo de asistencia envia un correo a: 'serversmanagementhelp@gmail.com'\n"
                + "Uno de nuestros tecnicos te atendera lo antes posible.");
    }

    @FXML
    private void abrirInformacion(ActionEvent event) {
        Utils.mostrarInformacion("About", "-Jowy S.A. no se compromete del mal uso que puedas hacer de esta aplicacion\n"
                + "-Servers Management build 232.12.121.123\n"
                + "Pagina web: https://github.com/Jowy77/ProyectoPGV_DAD");
    }

    private synchronized void agregarServidorConectado(ModeloServidor datosServidor) {
        servidoresConectados.add(datosServidor);
        System.out.println("Servidor agregado a la lista de servidores conectados: " + datosServidor.getNombreServidor());
    }

    @FXML
    private void buscarServidores(ActionEvent event) {

        Platform.runLater(() -> {
            servidoresListView.getItems().clear();
            servidoresConectados.forEach(servidor -> {
                servidoresListView.getItems().add(servidor.getNombreServidor());
            });
        });
    }

    @FXML
    private void cambiarUmbralCpu(ActionEvent event) {
        String nuevoUmbral = Utils.showTextInputDialog("Introduce el nuevo umbral para la CPU");
        
        if(!Utils.validarString(nuevoUmbral)){
            Utils.mostrarInformacion("Error", "No puede estar vacio.");
        }else if(!Utils.esDouble(nuevoUmbral)){
            Utils.mostrarInformacion(nuevoUmbral, "Solo puedes introducir numeros.");
        }else{
            umbralCpu = Double.parseDouble(nuevoUmbral);
            Utils.mostrarInformacion("Info", "Umbral de CPU modificado");
        }
    }

    @FXML
    private void cambiarUmbralRam(ActionEvent event) {
        String nuevoUmbral = Utils.showTextInputDialog("Introduce el nuevo umbral para la RAM");
        
        if(!Utils.validarString(nuevoUmbral)){
            Utils.mostrarInformacion("Error", "No puede estar vacio.");
        }else if(!Utils.esDouble(nuevoUmbral)){
            Utils.mostrarInformacion(nuevoUmbral, "Solo puedes introducir numeros.");
        }else{
            umbralRam = Double.parseDouble(nuevoUmbral);
            Utils.mostrarInformacion("Info", "Umbral de RAM modificado");
        }
    }

}
