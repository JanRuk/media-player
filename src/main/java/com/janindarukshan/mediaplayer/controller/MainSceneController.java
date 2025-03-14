package com.janindarukshan.mediaplayer.controller;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;

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
    public AnchorPane root;
    public Slider sldrProgress;
    public Slider sldrVolume;
    public MediaView mdView;
    public Label lblProgress;
    ImageView icon;
    FileChooser fileChooser;
    File file;
    MediaPlayer mediaPlayer;
    String fileName;

    public void initialize() {
        // Initialize volume slider
        sldrVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
            }
        });

        // Initialize progress slider
        sldrProgress.setOnMousePressed(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        });

        sldrProgress.setOnMouseReleased(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(sldrProgress.getValue()));
                mediaPlayer.play();
            }
        });
    }

    public void imgOnMouseEntered(MouseEvent event) {
        ScaleTransition ft = new ScaleTransition(Duration.millis(200), (ImageView) event.getTarget());
        ft.setFromX(1);
        ft.setFromY(1);
        ft.setToX(1.1);
        ft.setToY(1.1);
        ft.playFromStart();
    }

    public void imgOnMousedClicked(MouseEvent event) {
        icon = (ImageView) event.getSource();
        if (icon == imgFile) {
            loadFile(chooseFile());
        } else if (icon == imgPlay) {
            if (mediaPlayer != null) {
                mediaPlayer.play();
                lblName.setText(fileName);
                setProgressBar();
                imgMusic.setVisible(false);
                imgVideo.setVisible(false);
            } else {
                lblName.setText("Please select a media file");
            }
        } else if (icon == imgStop) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                imgMusic.setVisible(true);
                imgVideo.setVisible(true);
            } else {
                lblName.setText("Please select a media file");
            }
        } else if (icon == imgPause) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            } else {
                lblName.setText("Please select a media file");
            }
        }
    }

    public void loadFile(File file) {
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mdView.setMediaPlayer(mediaPlayer);
            mdView.setPreserveRatio(true);
            fileName = file.getName();
            String extension = fileName.substring(file.getName().lastIndexOf(".") + 1).toUpperCase();
            if (extension.equals("MP3") || extension.equals("WAV")) {
                mdView.setVisible(false);
            } else {
                mdView.setVisible(true);
            }
            mediaPlayer.play();
            imgMusic.setVisible(false);
            imgVideo.setVisible(false);
            setProgressBar();
            lblName.setText(fileName);
        }
    }

    public void setProgressBar() {
        mediaPlayer.setOnReady(() -> {
            sldrProgress.setMax(mediaPlayer.getTotalDuration().toSeconds());
            lblProgress.setText(formatTime(Duration.ZERO) + " / " + formatTime(mediaPlayer.getTotalDuration()));
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            if (!sldrProgress.isValueChanging()) {
                sldrProgress.setValue(newTime.toSeconds());
                lblProgress.setText(formatTime(newTime) + " / " + formatTime(mediaPlayer.getTotalDuration()));
            }
        });

        sldrProgress.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (sldrProgress.isValueChanging()) {
                mediaPlayer.seek(Duration.seconds(sldrProgress.getValue()));
            }
        });
    }

    private String formatTime(Duration time) {
        int hours = (int) time.toHours();
        int minutes = (int) (time.toMinutes() % 60);
        int seconds = (int) (time.toSeconds() % 60);
        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public void mdViewOnDragDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            file = dragboard.getFiles().getFirst();
            loadFile(file);
        }
    }

    public void mdViewOnDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
        dragEvent.consume();
    }

    public File chooseFile() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv", "*.wmv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        file = fileChooser.showOpenDialog(root.getScene().getWindow());
        return file;
    }
}