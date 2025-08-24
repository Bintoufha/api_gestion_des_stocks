package com.bintoufha.gestionStocks.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class InterceptorSql implements StatementInspector {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorSql.class);

    // ⚠️ on peux injecter dynamiquement depuis Spring Security / Contexte
    private final String uuidEntreprise = "123e4567-e89b-12d3-a456-426614174000";

    @Override
    public String inspect(String sql) {
        logger.info("Requête SQL interceptée : {}", sql);

        if (StringUtils.hasLength(sql) && sql.trim().toLowerCase().startsWith("select")) {
            if (sql.toLowerCase().contains("where")) {
                sql = sql + " AND uuid_entreprise = '" + uuidEntreprise + "'";
            } else {
                sql = sql + " WHERE uuid_entreprise = '" + uuidEntreprise + "'";
            }
        }
        return sql;
    }
}
