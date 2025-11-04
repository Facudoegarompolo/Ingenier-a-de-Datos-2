package com.example.cassandra;

import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CassandraMedicionCRUD {

    private final CqlSession session;

    // Prepared statements
    private final PreparedStatement insertMedicionPorSensorId;
    private final PreparedStatement insertMedicionPorPais;
    private final PreparedStatement insertMedicionPorCiudad;
    private final PreparedStatement insertMedicionPorSensorName;
    private final PreparedStatement insertMedicionPorEstado;
    private final PreparedStatement insertMedicionPorPaisTemp;
    private final PreparedStatement insertMedicionPorPaisHum;

    public CassandraMedicionCRUD(CqlSession session) {
        this.session = session;
        
        this.insertMedicionPorSensorId = session.prepare(
                "INSERT INTO mediciones_por_sensor_id (" +
                        "sensor_id, fecha_hora, medicion_id, temperatura, humedad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?);"
        );

        this.insertMedicionPorPais = session.prepare(
                "INSERT INTO mediciones_por_pais (" +
                        "sensor_id, pais, fecha_hora, medicion_id, temperatura, humedad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);"
        );

        this.insertMedicionPorCiudad = session.prepare(
                "INSERT INTO mediciones_por_ciudad (" +
                        "sensor_id, ciudad, fecha_hora, medicion_id, pais, temperatura, humedad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
        );

        this.insertMedicionPorSensorName = session.prepare(
                "INSERT INTO mediciones_por_sensor_name (" +
                        "sensor_id, sensor_name, fecha_hora, medicion_id, temperatura, humedad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);"
        );

        this.insertMedicionPorEstado = session.prepare(
                "INSERT INTO mediciones_por_estado (" +
                        "sensor_id, estado, fecha_hora, medicion_id, temperatura, humedad) " +
                "VALUES (?, ?, ?, ?, ?, ?);"
        );

        this.insertMedicionPorPaisTemp = session.prepare(
                "INSERT INTO mediciones_por_país_por_temperatura (" +
                        "sensor_id, pais, temperatura, fecha_hora, medicion_id, humedad, ciudad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
        );

        this.insertMedicionPorPaisHum = session.prepare(
                "INSERT INTO mediciones_por_país_por_humedad (" +
                        "sensor_id, pais, humedad, fecha_hora, medicion_id, temperatura, ciudad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
        );
    }

    // ✅ Main method to save a measurement across ALL tables
    public UUID guardarMedicion(
            UUID sensorId,
            String sensorName,
            String pais,
            String ciudad,
            int temperatura,
            int humedad,
            String estado
    ) {
        UUID medicionId = Uuids.timeBased();
        Instant fechaHora = Instant.now(); // timestamp

        // Execute inserts in all tables
        session.execute(insertMedicionPorSensorId.bind(
                sensorId, fechaHora, medicionId, temperatura, humedad, estado
        ));

        session.execute(insertMedicionPorPais.bind(
                sensorId, pais, fechaHora, medicionId, temperatura, humedad, estado
        ));

        session.execute(insertMedicionPorCiudad.bind(
                sensorId, ciudad, fechaHora, medicionId, pais, temperatura, humedad, estado
        ));

        session.execute(insertMedicionPorSensorName.bind(
                sensorId, sensorName, fechaHora, medicionId, temperatura, humedad, estado
        ));

        session.execute(insertMedicionPorEstado.bind(
                sensorId, estado, fechaHora, medicionId, temperatura, humedad
        ));

        session.execute(insertMedicionPorPaisTemp.bind(
                sensorId, pais, temperatura, fechaHora, medicionId, humedad, ciudad, estado
        ));

        session.execute(insertMedicionPorPaisHum.bind(
                sensorId, pais, humedad, fechaHora, medicionId, temperatura, ciudad, estado
        ));

        return medicionId;
    }
}
