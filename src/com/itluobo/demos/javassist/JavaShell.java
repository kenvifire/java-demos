package com.itluobo.demos.javassist;

import javassist.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by kenvi on 16/4/28.
 */
public class JavaShell {

    public static Object javaEval(String src)
            throws CannotCompileException, IllegalArgumentException,
            SecurityException, IllegalAccessException,
    InvocationTargetException

    {
        //1、生成一个随机的Class名称
        ClassPool pool = ClassPool. getDefault();
        CtClass cc = pool.makeClass( "Eval$Class" + System.currentTimeMillis()
                + Math. random());
        StringBuilder sb = new StringBuilder();
        //2、把src 放入run方法体里
        sb.append( "public static Object run(Object[] args){" );
        sb.append(src);
        sb.append( "return null;}");
        //3、把生成的方法加入到Class上
        CtMethod cm = CtNewMethod. make(sb.toString(), cc);
        cc.addMethod(cm);
        //4、调用生成的方法
        return cc.toClass().getMethods()[0].invoke( null, (Object)null );

    }
    public static void main(String [] args) throws Exception, SecurityException, CannotCompileException, IllegalAccessException, InvocationTargetException{

        BufferedReader br = new BufferedReader( new InputStreamReader(System.in ));
        String line = null;
        StringBuilder codeBuffer = new StringBuilder();
        while((line = br.readLine()) != null ){
            //exit为退出命令
            if(line.equalsIgnoreCase( "exit"))
                break;
            try{
                javaEval(codeBuffer.toString() + line);
                codeBuffer.append(line);
            } catch(CannotCompileException e){
                //输出编译错误信息
                System. err.println(e.getMessage());
            }
            System. out.println();
        }
        br.close();
    }


}
