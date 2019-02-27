package org.apereo.cas.configuration.model.core.authentication;

import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

import lombok.Getter;
import lombok.Setter;
import org.apereo.services.persondir.support.QueryType;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is {@link JdbcPrincipalAttributesProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@RequiresModule(name = "cas-server-support-person-directory", automated = true)
@Getter
@Setter
public class JdbcPrincipalAttributesProperties extends AbstractJpaProperties {

    private static final long serialVersionUID = 6915428382578138387L;

    /**
     * The SQL statement to execute and fetch attributes.
     * The syntax of the query must be {@code SELECT * FROM table WHERE {0}}.
     * The {@code WHERE} clause is dynamically generated by CAS.
     */
    private String sql;

    /**
     * Designed to work against a table where there is a mapping of one row to one user.
     * The fields in the table structure is assumed to match {@code username|name|lastname|address}
     * where there is only a single row per user.
     * Setting this setting to {@code false} will force CAS to work against a table where
     * there is a mapping of one row to one user.
     * The fields in the table structure is assumed to match {@code username|attr_name|attr_value}
     * where there is more than one row per username.
     */
    private boolean singleRow = true;

    /**
     * If the SQL should only be run if all attributes listed in the mappings exist in the query.
     */
    private boolean requireAllAttributes = true;

    /**
     * When constructing the final person object from the attribute repository,
     * indicate how the username should be canonicalized.
     */
    private CaseCanonicalizationMode caseCanonicalization = CaseCanonicalizationMode.NONE;

    /**
     * Indicates how multiple attributes in a query should be concatenated together.
     * The other option is OR.
     */
    private QueryType queryType = QueryType.AND;

    /**
     * Used only when there is a mapping of many rows to one user.
     * This is done using a key-value structure where the key is the
     * name of the "attribute name" column  the value is the name of the "attribute value" column.
     * If the table structure is as such:
     * <pre>
     * -----------------------------
     * uid | attr_name  | attr_value
     * -----------------------------
     * tom | first_name | Thomas
     * </pre>
     * Then a column mapping must be specified to teach CAS to use {@code attr_name}
     * and {@code attr_value} for attribute names and values.
     */
    private Map<String, String> columnMappings = new HashMap<>();

    /**
     * Username attribute(s) to use when running the SQL query.
     */
    private List<String> username = new ArrayList<>();

    /**
     * The order of this attribute repository in the chain of repositories.
     * Can be used to explicitly position this source in chain and affects
     * merging strategies.
     */
    private int order;

    /**
     * Map of attributes to fetch from the database.
     * Attributes are defined using a key-value structure
     * where CAS allows the attribute name/key to be renamed virtually
     * to a different attribute. The key is the attribute fetched
     * from the data source and the value is the attribute name CAS should
     * use for virtual renames.
     */
    private Map<String, String> attributes = new HashMap();

    /**
     * String id that identifies this resolver.
     */
    private String id;
}
