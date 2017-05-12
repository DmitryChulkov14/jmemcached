package external.project;

import net.jurinson.jmemcached.client.Client;
import net.jurinson.jmemcached.client.impl.JMemcachedClientFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class ExternalProject {

    public static void main(String[] args) throws Exception {
        try (Client client = JMemcachedClientFactory.buildNewClient("localhost", 9010)) {
            client.put("test", "Hello world");
            System.out.println(client.get("test"));

            client.remove("test");
            System.out.println(client.get("test"));

            client.put("test", "Hello world");
            client.put("test", new BusinessObject("TEST"));
            System.out.println(client.get("test"));

            client.clear();
            System.out.println(client.get("test"));

            client.put("jurinson", "Jurinson JMemcached", 2, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(3);
            System.out.println(client.get("jurinson"));
        }
    }

    private static class BusinessObject implements Serializable {
        private String name;
        BusinessObject(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("BusinessObject{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
