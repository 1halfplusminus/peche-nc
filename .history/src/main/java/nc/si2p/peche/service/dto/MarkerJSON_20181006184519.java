package nc.si2p.peche.service.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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
        this.id = (long) id;
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
        URLConnection connection = new URL("https://peche.nc/wp-content/uploads/icons/" + icon).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedImage image = ImageIO.read(connection.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos );
        this.icon = baos.toByteArray();
    }
}