package com.codegen;

import com.codegen.main.TableDef;
import com.codegen.service.CodeGeneratorManager;

/**
 * 代码生成器启动项
 */
public class CodeGeneratorMain {

    public static void main(String[] args) {
        CodeGeneratorManager cgm = new CodeGeneratorManager();
        for(TableDef td:CodeGeneratorManager.TABLES){
        	cgm.genCodeMain(td.getTableName(), td.getModelName(), td.getMapperSufName());
        }
    }
}
