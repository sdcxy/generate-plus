package com.github.sdcxy.generate;

/**
 * @ClassName GeneratorFactory
 * @Description TODO
 * @Author lxx
 * @Date 2019/9/15 21:57
 **/
public class GeneratorFactory extends SuperGenerator{

    public void generate(String parentPackageName, String moduleName, String[] tableName, String[] tablePrefix){
        getAutoGenerator(parentPackageName,moduleName,tableName,tablePrefix).execute();
    }

}
