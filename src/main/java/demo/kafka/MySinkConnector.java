package demo.kafka;

import com.github.jcustenborder.kafka.connect.utils.VersionUtil;
import com.github.jcustenborder.kafka.connect.utils.config.*;
import demo.kafka.config.MySinkConnectorConfig;
import demo.kafka.connector.MySinkTask;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySinkConnector extends SinkConnector {
    private static Logger log = LoggerFactory.getLogger(MySinkConnector.class);
    private MySinkConnectorConfig config;
    private Map<String, String> configSettings;

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> configs = new ArrayList<>(maxTasks);
        Map<String, String> taskConfig = new HashMap<>(configSettings);
        for (int i = 0; i < maxTasks; i++) {
            configs.add(taskConfig);
        }
        return configs;
    }

    @Override
    public void start(Map<String, String> settings) {
        config = new MySinkConnectorConfig(settings);
        configSettings = settings;
    }


    @Override
    public void stop() {
    }

    @Override
    public ConfigDef config() {
        return MySinkConnectorConfig.config();
    }

    @Override
    public Class<? extends Task> taskClass() {
        return MySinkTask.class;
    }

    @Override
    public String version() {
        return VersionUtil.version(this.getClass());
    }
}
