package demo.kafka.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import demo.kafka.config.MySinkConnectorConfig;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.github.jcustenborder.kafka.connect.utils.VersionUtil;

public class MySinkTask extends SinkTask {
    /*
      Your connector should never use System.out for logging. All of your classes should use slf4j
      for logging
   */
    private static Logger log = LoggerFactory.getLogger(MySinkTask.class);

    CSVWriter csvWriter;
    MySinkConnectorConfig config;
    ObjectMapper objectMapper;

    @Override
    public void start(Map<String, String> settings) {
        this.config = new MySinkConnectorConfig(settings);

        objectMapper = new ObjectMapper();
        try {
            this.csvWriter = new CSVWriter(new FileWriter("messages_" + UUID.randomUUID().toString() + ".csv"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        String[] headers = {"offset", "partition_id", "topic","payload"};
        csvWriter.writeNext(headers);
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        records.forEach(record -> {
            String[] line = {String.valueOf(record.kafkaOffset()), String.valueOf(record.kafkaPartition()), record.topic(), record.value().toString()};
            csvWriter.writeNext(line);
            csvWriter.flushQuietly();
        });
    }

    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> map) {
        csvWriter.flushQuietly();
    }

    @Override
    public void stop() {
        try {
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String version() {
        return VersionUtil.version(this.getClass());
    }
}
