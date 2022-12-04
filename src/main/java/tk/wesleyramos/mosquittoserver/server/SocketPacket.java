package tk.wesleyramos.mosquittoserver.server;

import org.json.JSONArray;
import org.json.JSONObject;
import tk.wesleyramos.mosquittoserver.Mosquitto;

import java.util.ArrayList;
import java.util.List;

public class SocketPacket {

    private static int CURRENT_ID = 0;

    private final JSONObject content;

    public SocketPacket(SocketPacketType type) {
        this.content = new JSONObject().put("type", type.name()).put("body", new JSONObject());
        this.from("mosquittoserver");
    }

    public SocketPacket(String content) {
        this.content = new JSONObject(content);
    }

    public SocketPacket from(SocketClient client) {
        return from(client.getName());
    }

    public SocketPacket from(String name) {
        content.put("id", name + "#" + CURRENT_ID++).put("from", name);
        return this;
    }

    public SocketPacket target(SocketClient client) {
        return target(client.getName());
    }

    public SocketPacket target(String serverName) {
        content.put("target", serverName);
        return this;
    }

    public SocketPacket set(String key, Object value) {
        content.getJSONObject("body").put(key, value);
        return this;
    }

    public boolean isSet(String key) {
        return content.getJSONObject("body").has(key);
    }

    public SocketPacket remove(String key) {
        content.getJSONObject("body").remove(key);
        return this;
    }

    public String getString(String key) {
        return getBody().getString(key);
    }

    public int getInt(String key) {
        return getBody().getInt(key);
    }

    public long getLong(String key) {
        return getBody().getLong(key);
    }

    public double getDouble(String key) {
        return getBody().getDouble(key);
    }

    public boolean getBoolean(String key) {
        return getBody().getBoolean(key);
    }

    public JSONObject getJSONObject(String key) {
        return getBody().getJSONObject(key);
    }

    public float getFloat(String key) {
        return getBody().getFloat(key);
    }

    public JSONArray getJSONArray(String key) {
        return getBody().getJSONArray(key);
    }

    public String getId() {
        return content.getString("id");
    }

    public String getFrom() {
        return content.getString("from");
    }

    public List<SocketClient> getFromClients() {
        List<SocketClient> clients = new ArrayList<>();

        for (SocketClient client : Mosquitto.service.getClients()) {
            if (client.getName() != null && client.getName().equals(getFrom())) {
                clients.add(client);
            }
        }

        return clients;
    }

    public String getTarget() {
        return content.getString("target");
    }

    public List<SocketClient> getTargetClient() {
        List<SocketClient> clients = new ArrayList<>();

        for (SocketClient client : Mosquitto.service.getClients()) {
            if (client.getName() != null && client.getName().equals(getTarget())) {
                clients.add(client);
            }
        }

        return clients;
    }

    public SocketPacketType getType() {
        return SocketPacketType.valueOf(content.getString("type"));
    }

    public JSONObject getBody() {
        return content.getJSONObject("body");
    }

    public boolean isValid() {
        return content.has("id") && content.has("from") && content.has("target") && content.has("type") && content.has("body");
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
