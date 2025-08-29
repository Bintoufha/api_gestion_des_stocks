package com.bintoufha.gestionStocks.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class InterceptorSql implements StatementInspector {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorSql.class);

    //  on  injecter dynamiquement depuis Spring Security / Contexte
    private final String uuidEntreprise = "123e4567-e89b-12d3-a456-426614174000";

    @Override
    public String inspect(String sql) {
        //logger.info("Requête SQL interceptée : {}", sql);

        if (StringUtils.hasLength(sql) && sql.trim().toLowerCase().startsWith("select")) {
            final String entityName = sql.substring(7,sql.indexOf("."));
            final String idEntreprise = MDC.get("idEntreprise");
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("roles")
                    && StringUtils.hasLength(idEntreprise)
            ) {
                if (sql.toLowerCase().contains("where")) {
                    sql = sql + " AND " + entityName+ ".uuid_entreprise ="+ idEntreprise;
                } else {
                    sql = sql + " WHERE " + entityName + ".uuid_entreprise ="+ idEntreprise;
                }
            }
        }
        return sql;
    }
}
