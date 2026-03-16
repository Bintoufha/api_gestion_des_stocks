package com.bintoufha.gestionStocks.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class InterceptorSql implements StatementInspector {

    @Override
    public String inspect(String sql) {

        if (sql.toLowerCase().startsWith("select")) {

            String year = MDC.get("year");
            String idEntreprise = MDC.get("idEntreprise");

            // Sanity check
            if (year == null && idEntreprise == null) return sql;

            // Format année 20XX–20XX → extraire 1ère partie
            // exemple : "2024-2025" -> 2024
            Integer yearStart = null;

            if (year != null && year.contains("-")) {
                yearStart = Integer.parseInt(year.split("-")[0]);
            }

            // Filtre entreprise
            if (idEntreprise != null) {
                sql += (sql.contains("where") ? " AND " : " WHERE ")
                    + " uuid_entreprise = '" + idEntreprise + "'";
            }

            // Filtre année sur la colonne creationDate
            if (yearStart != null) {
                sql += (sql.contains("where") ? " AND " : " WHERE ")
                    + " EXTRACT(YEAR FROM createdate) = " + yearStart;
            }
        }

        return sql;
    }
}
