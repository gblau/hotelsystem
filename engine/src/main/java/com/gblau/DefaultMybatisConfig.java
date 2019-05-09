package com.gblau;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 使用{@link MapperScan}代替{@link MapperScannerConfigurer}配置
 * @author gblau
 * @date 2017-08-30
 */
@MapperScan(basePackages = "com.gblau.engine.mapper")
public class DefaultMybatisConfig {
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    /**
     * Spring boot默认的数据源只有四种
     * 1. 如果使用{@link DataSourceBuilder#build()}，依靠自动配置的算法，hibernate无法创建默认类型以外的DataSource。
     *    使用{@link DataSourceBuilder#type(Class)}就可以覆盖算法，直接使用自己的配置。
     * 2. 如果使用默认配置，而且是单数据源，可以删去这个方法，加入四种数据源中其中一种的maven依赖就可以自动加载该数据源。
     *    在Spring boot约定的application.xml直接配置spring.datasource.xxx参数, sessionFactory中增加DataSource参数传入即可。
     * 3. 如果想创建自己的DataSource，或者需要配置多个DataSource，请使用下列配置。
     * 4. 如果自定义了dataSource生产，则Spring约定被破坏，不会有任何dataSource的自动配置使用(包括sessionFactory在内也需要手动配置)。
     *
     * sessionFactory中必须有DataSource参数传入，不能直接调用该方法，不然获取不到必要的连接参数！
     *
     * 参考: <a>https://stackoverflow.com/questions/35302392/in-spring-boot-using-c3p0-simultaneously-with-jdbctemplate-and-hibernate<a/>
     * @see DataSourceBuilder#findType() 自动配置的算法: 默认、HikariCP、CommonPool、CommonPool2
     * @return {@link DataSource}
     */
    @Bean("readDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(ComboPooledDataSource.class).build();
    }

    /**
     * 创建Mybatis数据库需要一个数据源，如果按照上方法配置的DataSource，则不能直接调用上面的方法。
     * 因为上面的application参数是托管给Spring的，如果直接调用{@link #readDataSource()}则无法获取jdbc url等参数
     * 事实上直接调用{@link #readDataSource()}并不是单例，也不会有连接池的效果。
     *
     * {@link ConfigurationProperties}注解可以将application配置的参数按照返回值bean类中字段的get、set方法名称注入
     *
     * @param dataSource Spring维护的{@link DataSource}实例
     * @return {@link SqlSessionFactoryBean}
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sessionFactory(@Qualifier("readDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sFactoryBean = new SqlSessionFactoryBean();
        sFactoryBean.setDataSource(dataSource);
        sFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return sFactoryBean;
    }
}
