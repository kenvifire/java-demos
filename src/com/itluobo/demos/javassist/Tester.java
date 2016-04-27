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

        //��ԭ�������Ʋ���һ���µķ���
        CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);

        //������ԭ����
        String methodName = method.getLongName();
        String oldName = method.getName()+ "$Impl";

        method.setName(oldName);
        StringBuilder body = new StringBuilder();
        body.append( "{long start = System.currentTimeMillis();" );

        //����з���ֵ�����¼����ֵ��û���򲻼�¼
        if(method.getReturnType()==CtClass. voidType){
            body.append( oldName+ "($$);");
        } else{
            body.append( "Object result = " +oldName+"($$);" );
        }
        body.append( " long end = System.currentTimeMillis();"
                + "System.out.println(\"" +methodName+ "\""+
                "\"time used:\"+" + "(end - start));" );

        //����з���ֵ�������return ���
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
