package com.itluobo.demos.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hannahzhang on 16/4/27.
 */
public class Tester {

    public static void main(String[] args) throws Exception{
        /** demo 1 **/


        /** demo 2 **/
        demo2();


    }

    public static void demo1() throws Exception {
        modifyMethod("com.itluobo.demos.javassist.Hello","test");
        Hello hello = new Hello();
        hello.test();
    }

    public static void demo2() throws Exception {
        Map<String,String> resultMap = new HashMap<String, String>();
        resultMap.put("$1==null", "new java.util.ArrayList()");

        mock("com.itluobo.demos.javassist.ServiceImpl","service",resultMap);

        Service service = new ServiceImpl();

        List<Integer> result = service.service( null);
        if(result == null){
            System. out.println( "return null");
        } else{
            System. out.println( "return not null" );
        }

    }


    public static void modifyMethod(String targetClassName, String targetMethodName) throws Exception{

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(targetClassName);
        CtMethod method = ctClass.getDeclaredMethod(targetMethodName);

        CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);

        String methodName = method.getLongName();
        String oldName = method.getName()+ "$Impl";

        method.setName(oldName);
        StringBuilder body = new StringBuilder();
        body.append( "{long start = System.currentTimeMillis();" );

        if(method.getReturnType()==CtClass. voidType){
            body.append( oldName+ "($$);");
        } else{
            body.append( "Object result = " +oldName+"($$);" );
        }
        body.append( " long end = System.currentTimeMillis();"
                + "System.out.println(\"" +methodName+ "\""+
                "\"time used:\"+" + "(end - start));" );

        if(method.getReturnType()==CtClass. voidType){
            body.append( "}");
        } else{
            body.append( "return result;}" );
        }
        newMethod.setBody(body.toString());
        ctClass.addMethod(newMethod);
        String dir = Tester.class.getResource("/").getPath();
        ctClass.writeFile(dir);



    }

    public static void mock(String targetClassName,String targetMethodName,Map<String,String> resultMap) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(targetClassName);
        CtMethod method = ctClass.getDeclaredMethod(targetMethodName);

        CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);

        String oldName = method.getName()+ "$Impl";
        method.setName(oldName);

        StringBuilder body = new StringBuilder();
        body.append("{");

        for (Map.Entry<String,String> entry : resultMap.entrySet()) {
            body.append("if(").append(entry.getKey()).append(")").append("return ")
                    .append(entry.getValue()).append(";");
        }

        if(method.getReturnType()==CtClass. voidType){
            body.append( oldName).append("($$);");
        } else{
            body.append( "return ").append(oldName).append("($$);").append("}");
        }


        newMethod.setBody(body.toString());
        ctClass.addMethod(newMethod);
        String dir = Tester.class.getResource("/").getPath();
        ctClass.writeFile(dir);

    }
}
