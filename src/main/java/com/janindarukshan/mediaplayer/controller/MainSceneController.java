package com.janindarukshan.mediaplayer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

public class MainSceneController {

    public AnchorPane anchrPaneControls;
    public AnchorPane anchrPaneMediaView;
    public ImageView imgFile;
    public ImageView imgMusic;
    public ImageView imgPause;
    public ImageView imgPlay;
    public ImageView imgStop;
    public ImageView imgVideo;
    public ImageView imgVolume1;
    public Label lblName;
    public Label lblProgress;
    public MediaView mdView;
    public AnchorPane root;
    public Slider sldrProgress;
    public Slider sldrVolume;

    public void imgOnMouseEntered(MouseEvent event) {

    }

    public void imgOnMousedClicked(MouseEvent event) {

    }

    public void mdViewOnDragDropped(DragEvent event) {

    }

    public void mdViewOnDragOver(DragEvent event) {

    }

}
