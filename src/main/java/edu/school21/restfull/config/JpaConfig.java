package edu.school21.restfull.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Profile("!test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("edu.school21.restfull.repository")
@EntityScan("edu.school21.restfull.model")
public class JpaConfig extends JpaBaseConfiguration {

	protected JpaConfig(DataSource dataSource,
						JpaProperties properties,
						ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
		super(dataSource, properties, jtaTransactionManager);
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(false);
		adapter.setDatabasePlatform("org.eclipse.persistence.platform.database.PostgreSQLPlatform");
		adapter.setDatabase(Database.POSTGRESQL);

		return adapter;
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		final Map<String, Object> props = new HashMap<>();
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class);

		return props;
	}

}
