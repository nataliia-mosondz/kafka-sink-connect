package demo.kafka.config;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;
import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder;

import java.util.Map;

public class MySinkConnectorConfig extends AbstractConfig {

    public MySinkConnectorConfig(Map<?, ?> originals) {
        super(config(), originals); //connector uses Json schema
    }

    public static ConfigDef config() {
        return new ConfigDef();
    }
}
