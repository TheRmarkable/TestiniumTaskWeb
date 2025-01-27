package enums;

public enum PropertiesType {

    CONNECTION_STRING("connectionString"),
    DB_CLASS("dbClass"),
    DB_USER("dbUser"),
    DB_PASSWORD("dbPassword"),
    WEBHOOK("webhook");

    public final String propertiesTypeText;

    PropertiesType(String value) {
        this.propertiesTypeText = value;
    }

    public String getText() {
        return propertiesTypeText;
    }
}