package com.github.sdcxy.common;

/**
 * @ClassName Constants
 * @Description TODO
 * @Author lxx
 * @Date 2019/9/15 19:47
 **/
public class Constants {

    // 默认配置文件路径
    public static final String DEFAULT_APPLICATION_YAML_PATH = "/src/main/resources/application.yml";
    // 作者
    public static final String AUTHOR = "sdcxy";

    public static final String SPRING = "spring";
    public static final String DATASOURCE = "datasource";
    public static final String DRIVER_CLASS_NAME = "driver-class-name";
    public static final String JDBC_URL = "jdbc-url";
    public static final String URL = "url";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    /**
     * 数据库驱动
     */

    public static final String MYSQL = "com.mysql.jdbc.Driver"; // mysql 5.+ 版本

    public static final String MYSQL_PLUS = "com.mysql.cj.jdbc.Driver";  // mysql 6.0 以上的版本

    public static final String SQL_SERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";


    // 自定义模板 默认找templates文件目录下的这些命名文件 只需要配置 xml 自定义位置
    public static final String CONTROLLER_TEMPLATE = "/templates/controller.java.vm";
    public static final String ENTITY_TEMPLATE = "/templates/entity.java.vm";
    public static final String MAPPER_TEMPLATE = "/templates/mapper.java.vm";
    public static final String MAPPER_XML_TEMPLATE = "/templates/mapper.xml.vm";
    public static final String SERVICE_TEMPLATE = "/templates/service.java.vm";
    public static final String SERVICEIMPL_TEMPLATE = "/templates/serviceImpl.java.vm";


    //
    // 根目录
    public static final String RootPath = System.getProperty("user.dir");
    // java 目录
    public static final String JavaPath = RootPath  + "/src/main/java";
    // 配置文件目录
    public static final String ResourcePath = RootPath + "/src/main/resources";

}
