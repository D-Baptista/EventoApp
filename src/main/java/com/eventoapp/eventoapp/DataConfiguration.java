@Configuration
public class DataConfiguration {
    
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dSource = new DriverManagerDataSource();
        // Usa variáveis de ambiente ou valores padrão (para rodar local)
        dSource.setDriverClassName("org.postgresql.Driver"); 
        dSource.setUrl(System.getenv("SPRING_DATASOURCE_URL")); 
        dSource.setUsername(System.getenv("SPRING_DATASOURCE_USERNAME"));
        dSource.setPassword(System.getenv("SPRING_DATASOURCE_PASSWORD"));
        return dSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL); // Mudar de MYSQL para POSTGRESQL
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        adapter.setPrepareConnection(true);
        return adapter;
    }
}
