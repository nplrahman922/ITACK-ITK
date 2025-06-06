package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoAnimator extends JPanel {

    private JLabel displayLabel;
    private List<ImageIcon> frames;
    private int currentFrameIndex = 0;
    private Timer animationTimer;
    private int delayMs; // Durasi tampilan setiap frame dalam milidetik
    private boolean imagesLoadedSuccessfully = false;

    public PhotoAnimator(int delayMs) {
        this.delayMs = delayMs;
        this.frames = new ArrayList<>();
        this.displayLabel = new JLabel();
        this.displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.displayLabel.setVerticalAlignment(SwingConstants.CENTER);
        setLayout(new BorderLayout());
        add(displayLabel, BorderLayout.CENTER);
        this.setOpaque(false); // Agar sesuai dengan setOpaque(false) pada panelFoto sebelumnya
    }

    public void loadImages(String pathPrefix, int startNum, int endNum, String suffix, int targetWidth, int targetHeight) {
        frames.clear();
        imagesLoadedSuccessfully = false;

        for (int i = startNum; i <= endNum; i++) {
            String imageName = pathPrefix + i + suffix;
            URL imageUrl = getClass().getResource(imageName);

            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image image = originalIcon.getImage();

                if (targetWidth > 0 || targetHeight > 0) {
                    int scaleW = (targetWidth <= 0) ? -1 : targetWidth;
                    int scaleH = (targetHeight <= 0) ? -1 : targetHeight;
                    if (scaleW != -1 || scaleH != -1) {
                        Image scaledImage = image.getScaledInstance(scaleW, scaleH, Image.SCALE_SMOOTH);
                        frames.add(new ImageIcon(scaledImage));
                    } else {
                        frames.add(originalIcon);
                    }
                } else {
                    frames.add(originalIcon);
                }
                // System.out.println("Berhasil memuat: " + imageName); // Uncomment untuk debug
            } else {
                System.err.println("Gagal menemukan gambar: " + imageName);
            }
        }

        if (!frames.isEmpty()) {
            displayLabel.setIcon(frames.get(0));
            ImageIcon firstFrame = frames.get(0);
            int prefW = (targetWidth > 0) ? targetWidth : firstFrame.getIconWidth();
            int prefH = (targetHeight > 0) ? targetHeight : firstFrame.getIconHeight();
            setPreferredSize(new Dimension(prefW, prefH));
            imagesLoadedSuccessfully = true;
        } else {
            displayLabel.setText("Tidak ada gambar animasi.");
            displayLabel.setForeground(Color.DARK_GRAY);
            int prefW = (targetWidth > 0) ? targetWidth : 300;
            int prefH = (targetHeight > 0) ? targetHeight : 200;
            setPreferredSize(new Dimension(prefW, prefH));
            imagesLoadedSuccessfully = false;
        }
        revalidate();
    }

    public void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        if (imagesLoadedSuccessfully && frames.size() > 1) {
            currentFrameIndex = 0;
            displayLabel.setIcon(frames.get(currentFrameIndex));
            animationTimer = new Timer(this.delayMs, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentFrameIndex = (currentFrameIndex + 1) % frames.size();
                    displayLabel.setIcon(frames.get(currentFrameIndex));
                }
            });
            animationTimer.start();
        } else if (imagesLoadedSuccessfully && !frames.isEmpty()) {
            displayLabel.setIcon(frames.get(0));
        }
    }

    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    public boolean isAnimationLoadedSuccessfully() {
        return imagesLoadedSuccessfully;
    }
}