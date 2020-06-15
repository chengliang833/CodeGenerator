package wang.ulane.gen;

import wang.ulane.gen.service.CodeGeneratorManager;

/**
 * 代码生成器启动项
 */
public class CodeGeneratorMain {

    public static void main(String[] args) {
        new CodeGeneratorManager().genCodeMain();
    }
    
}
