package com.steam.datahanding;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = HbaseConfig.CONF_PREFIX)
public class HbaseConfig {

    public static final String CONF_PREFIX = "hbase.conf";

    private Map<String,String> confMaps=new HashMap<String, String>() {{
        put("hbase.zookeeper.quorum","master,slave01,slave02");
        put("hbase.zookeeper.property.clientPort","2181");
        put("hbase.master", "master:16010");
    }};

    public Map<String, String> getconfMaps() {
        return confMaps;
    }
    public void setconfMaps(Map<String, String> confMaps) {
        this.confMaps = confMaps;
    }
}
