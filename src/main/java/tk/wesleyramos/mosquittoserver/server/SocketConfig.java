package tk.wesleyramos.mosquittoserver.server;

import org.json.JSONArray;
import org.json.JSONObject;
import tk.wesleyramos.mosquittoserver.MosquittoColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class SocketConfig {

    private static final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random random = new Random();
    private static final File[] files = new File[2];

    private JSONObject properties = new JSONObject();
    private JSONArray credentials = new JSONArray();

    public void start() throws IOException {
        files[0] = new File(System.getProperty("use.dir"), "config.json");
        files[1] = new File(System.getProperty("use.dir"), "credentials.json");

        if (!files[0].exists() && files[0].createNewFile()) {
            FileWriter writer = new FileWriter(files[0]);
            writer.write(this.properties.put("address", "127.0.0.1").put("port", 26906).toString(4) + "\n");
            writer.flush();
            writer.close();
        }

        if (!files[1].exists() && files[1].createNewFile()) {
            FileWriter writer = new FileWriter(files[1]);
            writer.write(this.credentials.toString(4) + "\n");
            writer.flush();
            writer.close();
        }

        this.properties = new JSONObject(new String(Files.readAllBytes(files[0].toPath())));
        this.credentials = new JSONArray(new String(Files.readAllBytes(files[1].toPath())));

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Config]: " + MosquittoColor.WHITE_BRIGHT + "os arquivos foram lidos e carregados com sucesso. Até a próxima!" + MosquittoColor.RESET);
    }

    public void stop() {
        this.properties.clear();
        this.credentials.clear();

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server]: " + MosquittoColor.WHITE_BRIGHT + "os arquivos foram totalmente salvos com sucesso" + MosquittoColor.RESET);
    }

    public boolean testCredentials(String credentials) {
        for (Object object : this.credentials) {
            if (credentials.equals(object)) {
                return true;
            }
        }

        return false;
    }

    public String getNewCredentials() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 32; i++) {
            builder.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }

        this.credentials.put(builder.toString());
        this.updateCredentials();

        return builder.toString();
    }

    public void revokeCredentials(String credentials) {
        for (int i = 0; i < this.credentials.length(); i++) {
            if (this.credentials.getString(i).equals(credentials)) {
                this.credentials.remove(i);
            }
        }

        this.updateCredentials();
    }

    public void updateCredentials() {
        try {
            FileWriter writer = new FileWriter(files[1]);
            writer.write(this.credentials.toString(4) + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return properties.getString("address");
    }

    public Integer getPort() {
        return properties.getInt("port");
    }

    public JSONObject getProperties() {
        return properties;
    }

    public void setProperties(JSONObject properties) {
        this.properties = properties;
    }

    public JSONArray getCredentials() {
        return credentials;
    }

    public void setCredentials(JSONArray credentials) {
        this.credentials = credentials;
    }
}
