package com.github.sdcxy.generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.SqlServerTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.github.sdcxy.common.Constants;
import com.github.sdcxy.common.DataSource;
import com.github.sdcxy.common.YamlUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SuperGenerator
 * @Description TODO
 * @Author lxx
 * @Date 2019/9/15 19:45
 **/
public class SuperGenerator {

    /**
     * 获取 dataSourceConfig
     * @version 1.0.0
     * @return
     */
    protected DataSourceConfig getDataSourceConfig() {
        // 实例化generator dataSourceConfig
        DataSourceConfig dataSourceConfig =  new DataSourceConfig();
        // 从yml 文件中读取 dataSource
        DataSource dataSource = YamlUtils.readDataSourceYaml();
        // 获取driver-class-name
        String driverClassName = dataSource.getDriverClassName();
        // 判断驱动类型 设置数据库类型
        if (driverClassName.equals(Constants.MYSQL) || driverClassName.equals(Constants.MYSQL_PLUS)) {
            dataSourceConfig.setDbType(DbType.MYSQL);
            // 数据库类型的转换
            dataSourceConfig.setTypeConvert(new MySqlTypeConvert(){
                @Override
                public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                    //  数据库类型转换
                    if (fieldType.toLowerCase().equals("bit")) {
                        return DbColumnType.BOOLEAN;
                    }
                    if (fieldType.toLowerCase().equals("date")) {
                        return DbColumnType.LOCAL_DATE;
                    }
                    if (fieldType.toLowerCase().equals("time")) {
                        return DbColumnType.LOCAL_TIME;
                    }
                    if (fieldType.toLowerCase().equals("tinyint")) {
                        return DbColumnType.BOOLEAN;
                    }
                    if (fieldType.toLowerCase().equals("datetime")) {
                        return DbColumnType.LOCAL_DATE_TIME;
                    }
                    return super.processTypeConvert(globalConfig, fieldType);
                }
            });
        }else if (driverClassName.equals(Constants.SQL_SERVER)) {
            dataSourceConfig.setDbType(DbType.SQL_SERVER);
            // 数据库类型转换
            dataSourceConfig.setTypeConvert(new SqlServerTypeConvert(){
                @Override
                public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                    return super.processTypeConvert(globalConfig, fieldType);
                }
            });
        }
        dataSourceConfig.setDriverName(driverClassName)
                // 设置数据库url
                .setUrl(dataSource.getUrl())
                // 设置数据库用户名
                .setUsername(dataSource.getUsername())
                // 设置数据库密码
                .setPassword(dataSource.getPassword());
        return dataSourceConfig;
    }

    /**
     * 获取 StrategyConfig 生成策略配置
     * @param tableNames 数据库表名
     * @param tablePrefix 数据库表前缀
     * @version 1.0.0
     * @return
     */
    protected StrategyConfig getStrategyConfig(String[] tableNames,String[] tablePrefix) {
        try{
            if (tableNames == null ) { throw new NullPointerException("填写的数据库表名不能为空"); }
        }catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                // 全局大写命名
                .setCapitalMode(false)
                // 数据库表名
                .setInclude(tableNames)
                // 表名生成策略 驼峰命名
                .setNaming(NamingStrategy.underline_to_camel)
                // 实体是否使用Lombok插件
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                // 控制层是否使用Rest风格
                .setRestControllerStyle(false)
                // 【实体】是否生成字段常量（默认 false）
                .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                .setEntityBuilderModel(false);
        if (tablePrefix != null) {
            // 去除表前缀
            strategyConfig.setTablePrefix(tablePrefix);
        }
        return strategyConfig;
    }

    /**
     * 获取 PackageConfig
     * @param parentPackageName 父级包
     * @param moduleName 模块名
     * @version 1.0.0
     * @return
     */
    protected PackageConfig getPackageConfig(String parentPackageName,String moduleName) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig
                // 实体类
                .setEntity("model/entity")
                // mapper类
                .setMapper("mapper")
                // 服务类
                .setService("service")
                // 服务实现类
                .setServiceImpl("service.impl")
                // 前端控制器类
                .setController("controller");
        if (StringUtils.isNotEmpty(parentPackageName)){
            // 设置父级包
            packageConfig.setParent(parentPackageName);
        } else {
            // 设置默认父级包
            packageConfig.setParent("com.github.sdcxy");
        }
        if (StringUtils.isNotEmpty(moduleName)) {
            // 设置模块名
            packageConfig.setModuleName(moduleName);
        }
        return packageConfig;
    }

    /**
     *  获取 GlobalConfig
     * @version 1.0.0
     * @return
     */
    protected GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                // 作者
                .setAuthor(Constants.AUTHOR)
                // 输出目录
                .setOutputDir(Constants.JavaPath)
                // 是否启动AR模式
                .setActiveRecord(false)
                // XML ResultMap
                .setBaseResultMap(false)
                // XML columnList
                .setBaseColumnList(false)
                // XML 二级缓存
                .setEnableCache(false)
                // 是否生成 kotlin 代码
                .setKotlin(false)
                // 是否覆盖文件
                .setFileOverride(false)
                // 生成后不打开
                .setOpen(false)
                // 是否开启swagger2
                .setSwagger2(false)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%s")
                .setXmlName("%sMapper")
                .setMapperName("%sMapper")
                .setServiceName("I%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sRestController");

    }


    /**
     *  获取 TemplateConfig
     * @version 1.0.0
     * @return
     */
    protected TemplateConfig getTemplateConfig(){
        // 不配置Mapper.xml文件的生成，后面会配置xml文件生成的自定义存放位置
        return new TemplateConfig().setXml(null);
    }

    /**
     * 获取 InjectionConfig 自定义配置
     * @version 1.0.0
     * @return
     */
    protected InjectionConfig getInjectionConfig(){
        // 自定义配置
        return new InjectionConfig() {
            @Override
            public void initMap() {
                // 自定义填充mapper;
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.singletonList(new FileOutConfig(Constants.MAPPER_XML_TEMPLATE){
            // 自定义mapper.xml输出目录 resources/mapper/tableInfo.getEntityName().xml
            @Override
            public String outputFile(TableInfo tableInfo) {
                return  Constants.ResourcePath + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
    }


    /**
     *  获取 AutoGenerator
     * @param parentPackageName 父级包
     * @param moduleName 模块名
     * @param tableNames 数据库表名
     * @param tablePrefix 数据库表名前缀
     * @version 1.0.1
     * @return
     */
    public AutoGenerator getAutoGenerator(String parentPackageName, String moduleName,
                                          String[] tableNames, String[] tablePrefix
    ){
        return new AutoGenerator()
                // 全局配置
                .setGlobalConfig(getGlobalConfig())
                // 包名配置
                .setPackageInfo(getPackageConfig(parentPackageName,moduleName))
                // 模板配置
                .setTemplate(getTemplateConfig())
                // 自定义配置
                .setCfg(getInjectionConfig())
                //策略配置
                .setStrategy(getStrategyConfig(tableNames,tablePrefix))
                // 配置数据源
                .setDataSource(getDataSourceConfig());
    }
}
