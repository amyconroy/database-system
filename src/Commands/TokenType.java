package Commands;

public enum TokenType {
    USE("USE"),
    CREATE("CREATE"),
    DATABASE("DATABASE"),
    TABLE("TABLE"),
    DROP("DROP"),
    ALTER("ALTER"),
    INSERT("INSERT"),
    SELECT("SELECT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    FROM("FROM"),
    JOIN("JOIN"),
    WHERE("WHERE"),
    INTO("INTO"),
    SET("SET"),
    VALUES("VALUES"),
    AND("AND"),
    ON("ON"),
    ADD("ADD"),
    NAME("[A-z]"),
    COMMA(","),
    STAR("*"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    END(";");

    private final String token;

    TokenType(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}

