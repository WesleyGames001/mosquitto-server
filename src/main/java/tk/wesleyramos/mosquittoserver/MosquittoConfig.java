package tk.wesleyramos.mosquittoserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class MosquittoConfig {

    private File configFile;
    private File credentialsFile;
    private JSONObject configJson;
    private JSONArray credentialsJson;

    public void start() throws IOException {
        this.configFile = new File(System.getProperty("use.dir"), "config.json");
        this.credentialsFile = new File(System.getProperty("use.dir"), "credentials.json");

        if (!configFile.exists() && configFile.createNewFile()) {
            FileWriter writer = new FileWriter(configFile);
            writer.write(new JSONObject().put("address", "127.0.0.1").put("port", 26906).toString(4) + "\n");
            writer.flush();
            writer.close();
        }

        if (!credentialsFile.exists() && credentialsFile.createNewFile()) {
            FileWriter writer = new FileWriter(credentialsFile);
            writer.write(new JSONArray() + "\n");
            writer.flush();
            writer.close();
        }

        this.configJson = new JSONObject(new String(Files.readAllBytes(configFile.toPath())));
        this.credentialsJson = new JSONArray(new String(Files.readAllBytes(credentialsFile.toPath())));

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Config]: " + MosquittoColor.WHITE_BRIGHT + "os arquivos foram lidos e carregados com sucesso");
    }

    public void stop() {
        this.configJson.clear();
        this.credentialsJson.clear();

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server]: " + MosquittoColor.WHITE_BRIGHT + "os arquivos foram totalmente salvos com sucesso");
    }

    public boolean testCredentials(String credentials) {
        for (Object object : credentialsJson) {
            if (credentials.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public String getNewCredentials() {
        String alphanumeric = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

        Random random = new Random();
        StringBuilder credentials = new StringBuilder();

        for (int i = 0; i < 32; i++) {
            credentials.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }

        this.credentialsJson.put(credentials.toString());
        this.updateCredentials();

        return credentials.toString();
    }

    public void revokeCredentials(String credentials) {
        for (int i = 0; i < credentialsJson.length(); i++) {
            if (credentialsJson.getString(i).equals(credentials)) {
                credentialsJson.remove(i);
            }
        }

        this.updateCredentials();
    }

    public void updateCredentials() {
        try {
            FileWriter writer = new FileWriter(credentialsFile);
            writer.write(credentialsJson + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return configJson.getString("address");
    }

    public Integer getPort() {
        return configJson.getInt("port");
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public File getCredentialsFile() {
        return credentialsFile;
    }

    public void setCredentialsFile(File credentialsFile) {
        this.credentialsFile = credentialsFile;
    }

    public JSONObject getConfigJson() {
        return configJson;
    }

    public void setConfigJson(JSONObject configJson) {
        this.configJson = configJson;
    }

    public JSONArray getCredentialsJson() {
        return credentialsJson;
    }

    public void setCredentialsJson(JSONArray credentialsJson) {
        this.credentialsJson = credentialsJson;
    }
}
