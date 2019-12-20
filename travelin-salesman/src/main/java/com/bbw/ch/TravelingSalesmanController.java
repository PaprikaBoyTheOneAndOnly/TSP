package com.bbw.ch;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TravelingSalesmanController implements Initializable {
    private Map<String, Node> nodes;

    public TravelingSalesmanController() {
        this.nodes = new HashMap<>();
    }

    @FXML
    private Canvas canvas;

    @FXML
    private Text smallestWay, reset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        TravelingSalesman ts = new TravelingSalesman();

        reset.setOnMouseClicked(event -> {
            this.nodes.clear();
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            this.smallestWay.setText("");
        });

        canvas.setOnMouseClicked(event -> {
            if (nodes.size() < 8) {
                double x = event.getX(), y = event.getY();
                Node newNode = new Node(x, y, nodes.size() + 1 + "");
                this.nodes.put(newNode.getName(), newNode);
                if (this.nodes.size() > 1) {
                    this.smallestWay.setText("Smallest way: "
                            + round(ts.getSmallestWay(new ArrayList<>(this.nodes.values())))
                            + " -> " + ts.getPoints());
                }

                for (Node node : nodes.values()) {
                    gc.strokeLine(x, y, node.getX(), node.getY());
                    double way = node.calcWay(newNode);
                    double xx = (x + node.getX()) / 2;
                    double yy = (y + node.getY()) / 2;
                    gc.strokeText(String.valueOf(round(way)), xx, yy);
                }

                gc.fillOval(x - 25, y - 25, 50, 50);
                gc.setFill(Color.WHITE);
                gc.setFont(new Font(20));
                gc.fillText(newNode.getName(), x - 5, y + 5);
                gc.setFill(Color.BLACK);
            }
        });
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.;
    }
}
