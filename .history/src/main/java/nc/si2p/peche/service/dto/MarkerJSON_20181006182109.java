package nc.si2p.peche.service.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkerJSON {
    private byte[] icon;
    private int id;

    public MarkerJSON(
        @JsonProperty("id") int id,
        @JsonProperty("icon") String icon
    ) {
      
    }

    /**
     * @return the icon
     */
    public byte[] getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     * @throws IOException
     */
    public void setIcon(String icon) throws IOException {
        URL urlInput = new URL("https://peche.nc/wp-content/uploads/icons/wow.png");
        BufferedImage image = ImageIO.read(urlInput);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos );
        this.icon = baos.toByteArray();
    }
}