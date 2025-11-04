package com.example.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.oss.driver.api.core.CqlSession;

import java.net.InetSocketAddress;

@Configuration
public class CassandraConnection {

    @Value("${cassandra.contactpoints}")
    private String contactPoint;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.datacenter}")
    private String datacenter;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Bean
    public CqlSession cassandraSession() {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoint, port))
                .withLocalDatacenter(datacenter)
                .withKeyspace(keyspace)
                .build();

        System.out.println("✅ Conexión exitosa a Cassandra -> Keyspace: " + keyspace);
        return session;
    }
}
