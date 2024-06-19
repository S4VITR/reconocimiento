package com.reconocimiento.main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import com.reconocimiento.components.inicio.TablaProfesores;
import com.reconocimiento.components.materias.TablaMaterias;
import com.reconocimiento.camara.Camara;
import com.reconocimiento.components.asistencia.TablaAsistenciaProfesores;
import com.reconocimiento.components.horarios.TablaHorarioProfesores;
import com.reconocimiento.components.reconocimiento.CamaraInicializada;
import com.reconocimiento.components.registro.RegistroDatosProfesores;
import com.reconocimiento.components.reconocimiento.ReconocimientoDatosProfesores;
import com.reconocimiento.components.menu.Menu;

import static com.reconocimiento.components.menu.Menu.*;

public class Main extends JFrame {

    public Main()throws Exception {
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(202, 207, 210));
        this.getContentPane().add(new Menu());

        addComponents();

        this.setSize(1200, 700);
        this.setResizable(false);
        this.setUndecorated(true);

        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 20, 20);
        this.setShape(forma);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        settingsFrame();
        new Camara().iniciarCamara();
    }
    private void addComponents() {
        Dashboard panelDash = new Dashboard();
        panelDash.setVisible(true);
        Profesores panelData = new Profesores();
        panelData.setVisible(false);

        CamaraInicializada camera = new CamaraInicializada();
        camera.setVisible(false);
        ReconocimientoDatosProfesores dataEmployee = new ReconocimientoDatosProfesores();
        dataEmployee.setVisible(false);
        RegistroDatosProfesores registroDatosProfesores = new RegistroDatosProfesores();
        registroDatosProfesores.setVisible(false);

        dashboard.addActionListener(e -> {
            panelDash.setVisible(true);
            panelData.setVisible(false);

            camera.setVisible(false);
            dataEmployee.setVisible(false);

            registroDatosProfesores.setVisible(false);
        });
        data.addActionListener(e -> {
            panelDash.setVisible(false);
            panelData.setVisible(true);

            camera.setVisible(false);
            dataEmployee.setVisible(false);
            
            registroDatosProfesores.setVisible(false);
        });
        recognize.addActionListener(e -> {
            panelDash.setVisible(false);
            panelData.setVisible(false);

            camera.setVisible(true);
            dataEmployee.setVisible(true);

            registroDatosProfesores.setVisible(false);
        });

        this.getContentPane().add(panelDash);
        this.getContentPane().add(panelData);

        this.getContentPane().add(camera);
        this.getContentPane().add(dataEmployee);

        // this.getContentPane().add(registroDatosProfesores);
    }
    private void settingsFrame() {
        exit.addActionListener(e -> System.exit(0));
    }
    public static class Dashboard extends JPanel {
        public Dashboard() {
            this.setLayout(null);

            this.setBounds(210, 10, 980, 680);
            this.setOpaque(false);

            this.add(new TablaAsistenciaProfesores());
            this.add(new TablaMaterias());
            this.add(new TablaHorarioProfesores());
        }
    }
    public static class Profesores extends JPanel {
        public Profesores() {
            this.setLayout(null);

            this.setBounds(210, 10, 980, 680);
            this.setOpaque(false);

            this.add(new TablaProfesores());
        }
    }
    public static void main(String[] args) throws Exception {
        new Main();
    }
}
