package wang.ulane.gen;

import wang.ulane.gen.main.TableDef;
import wang.ulane.gen.service.CodeGeneratorManager;

/**
 * 代码生成器启动项
 */
public class CodeGeneratorMain {

    public static void main(String[] args) {
        CodeGeneratorManager cgm = new CodeGeneratorManager();
        for(TableDef td:CodeGeneratorManager.TABLES){
        	cgm.genCodeMain(td.getTableName(), td.getModelName(), td.getMapperSufName(), td.getGenKey());
        }
    }
}
