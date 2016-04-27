package com.itluobo.demos.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * Created by hannahzhang on 16/4/27.
 */
public class Tester {

    public static void main(String[] args) throws Exception{
        modifyMethod("com.itluobo.demos.javassist.Hello","test");
        Hello hello = new Hello();
        hello.test();

    }


    public static void modifyMethod(String targetClassName, String targetMethodName) throws Exception{

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(targetClassName);
        CtMethod method = ctClass.getDeclaredMethod(targetMethodName);

        //从原方法复制产生一个新的方法
        CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);

        //重命名原方法
        String methodName = method.getLongName();
        String oldName = method.getName()+ "$Impl";

        method.setName(oldName);
        StringBuilder body = new StringBuilder();
        body.append( "{long start = System.currentTimeMillis();" );

        //如果有返回值，则记录返回值，没有则不记录
        if(method.getReturnType()==CtClass. voidType){
            body.append( oldName+ "($$);");
        } else{
            body.append( "Object result = " +oldName+"($$);" );
        }
        body.append( " long end = System.currentTimeMillis();"
                + "System.out.println(\"" +methodName+ "\""+
                "\"time used:\"+" + "(end - start));" );

        //如果有返回值，则添加return 语句
        if(method.getReturnType()==CtClass. voidType){
            body.append( "}");
        } else{
            body.append( "return result;}" );
        }
        newMethod.setBody(body.toString());
        ctClass.addMethod(newMethod);
        String dir = Tester.class.getResource("/").getPath();
        ctClass.writeFile(dir);

        System.out.println(dir);


    }
}
