package JoanRuiz.mindnet.util.validators;

import JoanRuiz.mindnet.exception.ImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageValidator {

    public static boolean isValidImageUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");  // Solo obtener encabezados
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Obtener el Content-Type
            String contentType = connection.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new ImageException("Invalid Content-Type");
            }

            // Validar si realmente es una imagen descargando los primeros bytes
            BufferedImage image = ImageIO.read(url);
            if (image == null) {
                throw new ImageException("Invalid image");
            }

            return true;

        } catch (IOException | ImageException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}