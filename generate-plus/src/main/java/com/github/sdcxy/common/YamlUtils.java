package com.github.sdcxy.common;

import org.ho.yaml.Yaml;
import org.ho.yaml.exception.YamlException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName YamlUtils
 * @Description TODO
 * @Author lxx
 * @Date 2019/9/15 20:10
 **/
public class YamlUtils {

    public static DataSource readDataSourceYaml(){
        DataSource dataSource = new DataSource();
        try {
            String filePath = Constants.RootPath + Constants.DEFAULT_APPLICATION_YAML_PATH;
            // 获取application.yml
            File applicationFile = new File(filePath);

            if (applicationFile.exists()) {
                // 获取 application.yml 文件的属性
                Map applicationMap = Yaml.loadType(applicationFile, HashMap.class);
                if ( applicationMap != null ){
                    // 获取spring下的所有属性
                    Map springMap = (HashMap)applicationMap.get(Constants.SPRING);
                    if (springMap != null ){
                        Map dataSourceMap = (HashMap)springMap.get(Constants.DATASOURCE);
                        if (dataSourceMap != null ) {
                            // 设置 driver-class-name
                            if (dataSourceMap.get(Constants.DRIVER_CLASS_NAME) != null) {
                                dataSource.setDriverClassName(dataSourceMap.get(Constants.DRIVER_CLASS_NAME) + "");
                            }else {
                                throw new NullPointerException("driver-class-name property was not found");
                            }
                            // 设置url 或者 jdbc-url
                            if (dataSourceMap.get(Constants.URL) != null) {
                                dataSource.setUrl(dataSourceMap.get(Constants.URL) + "");
                            }else {
                                if (dataSourceMap.get(Constants.JDBC_URL) != null) {
                                    dataSource.setUrl(Constants.JDBC_URL);
                                }else {
                                    throw new NullPointerException("url or jdbc-url property was not found");
                                }
                            }
                            // 设置 username
                            if (dataSourceMap.get(Constants.USERNAME) != null) {
                                dataSource.setUsername(dataSourceMap.get(Constants.USERNAME) + "");
                            }else {
                                throw new NullPointerException("username property was not found");
                            }
                            // 设置 password
                            if (dataSourceMap.get(Constants.PASSWORD) != null) {
                                dataSource.setPassword(dataSourceMap.get(Constants.PASSWORD) + "");
                            }else {
                                throw new NullPointerException("password property was not found");
                            }

                        } else {
                            throw new NullPointerException("datasource property was not found");
                        }
                    } else {
                        throw new NullPointerException("spring property was not found");
                    }
                } else {
                    throw new NullPointerException("application.yml all property was not found");
                }
            } else {
                throw new FileNotFoundException("application.yml was not found");
            }
        } catch (FileNotFoundException e){
            System.out.println("application.yml was not found");
        } catch (YamlException e) {
            System.out.println("解析yml文件不是一个list或map集合");
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return dataSource;
    }
}
