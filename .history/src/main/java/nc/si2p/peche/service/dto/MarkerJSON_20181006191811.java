package nc.si2p.peche.service.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.text.ParseException;
import javax.imageio.ImageIO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkerJSON {
    private byte[] icon;
    private Long id;
    private String titre;
    private String description;
    private String email;
    private byte[] pj;
    private String ip;
    private Float lat;
    private Float lng;
    private LocalDate date;

    public MarkerJSON(
        @JsonProperty("id") int id,
        @JsonProperty("icon") String icon,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("email") String email,
        @JsonProperty("ip") String ip,
        @JsonProperty("lat") String lat,
        @JsonProperty("lng") String lng,
        @JsonProperty("date") String date
    ) throws IOException, ParseException {
      setId(id);
      setIcon(icon);
      setTitre(title);
      setDescription(description);
      setEmail(email);
      setIp(ip);
      setLat(lat);
      setLng(lng);
      setDate(date);
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date2 the date to set
     * @throws ParseException
     */
    public void setDate(String date2) throws ParseException {
        this.date = parseDate(date2);
    }

    /**
     * @return the lng
     */
    public Float getLng() {
        return lng;
    }

    /**
     * @param lng the lng to set
     */
    public void setLng(String lng) {
        this.lng = Float.parseFloat(lng);
    }

    /**
     * @return the lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(String lat) {
        this.lat = Float.parseFloat(lat);
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the pj
     */
    public byte[] getPj() {
        return pj;
    }

    /**
     * @param pj the pj to set
     * @throws IOException
     */
    public void setPj(String pj) throws IOException {
        URL url = new URL("https://peche.nc/wp-content/uploads/markers_uploads/" + pj);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedImage image = ImageIO.read(connection.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, connection.getContentType(), baos );
        this.pj = baos.toByteArray();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
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
        ImageIO.write(image, connection.getContentType(), baos );
        this.icon = baos.toByteArray();
    }
    private LocalDate parseDate(String date) throws ParseException {
        String pattern = "yyyy-mm-dd hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return new java.sql.Date( simpleDateFormat.parse(date).getTime() ).toLocalDate();
    }
}