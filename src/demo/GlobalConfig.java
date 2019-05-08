package demo;

public class GlobalConfig {

    private static Configuration configuration;

    public static void setGlobalConfig(Configuration config) {
        configuration = config;
    }

    public static Configuration get() {
        return configuration;
    }

}
