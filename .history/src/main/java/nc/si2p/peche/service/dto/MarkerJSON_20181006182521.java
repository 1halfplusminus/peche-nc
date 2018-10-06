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
    private Long id;

    public MarkerJSON(
        @JsonProperty("id") int id,
        @JsonProperty("icon") String icon
    ) throws IOException {
      setId(id);
      setIcon(icon);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = new Integer(id).longValue();
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
        URL urlInput = new URL("https://peche.nc/wp-content/uploads/icons/" + icon + ".png");
        BufferedImage image = ImageIO.read(urlInput);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos );
        this.icon = baos.toByteArray();
    }
}