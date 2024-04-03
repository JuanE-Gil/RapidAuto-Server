package com.example.demo.Service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageCompressor {

    public static byte[] compressImage(byte[] imageData) throws IOException {
        if (imageData == null) {
            return null; // Devuelve null si la imagen es nula
        }

        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

        if (originalImage == null) {
            return null; // Devuelve null si no se puede leer la imagen
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(originalImage.getWidth(), originalImage.getHeight()) // Mantener las dimensiones originales
                .outputFormat("jpg")
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }

}


