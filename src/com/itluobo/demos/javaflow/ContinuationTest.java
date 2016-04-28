package com.itluobo.demos.javaflow;

import org.apache.commons.javaflow.Continuation;
import org.apache.commons.javaflow.ContinuationClassLoader;
import org.apache.commons.javaflow.bytecode.transformation.asm.AsmClassTransformer;
import org.apache.commons.javaflow.utils.RewritingUtils;

import java.io.File;
import java.net.URL;


/**
 * Created by kenvi on 16/4/28.
 */
public class ContinuationTest {
    public static void main(String[] args) throws Exception{
        String className = "com/itluobo/demos/javaflow/MyRunnable";
        String inFile = ContinuationTest.class.getResource("/").getPath()+ className + ".class";
        String outFile = "/tmp/classes/" + className + ".class";
//        RewritingUtils.rewriteClassFile(
//              new File(inFile),
//                new AsmClassTransformer(),
//                new File(outFile)
//        );

        ClassLoader cl = new ContinuationClassLoader(
                new URL[]{new File("/tmp/classes").toURL()},
                ContinuationTest.class.getClassLoader());

        Class clazz = cl.loadClass("com.itluobo.demos.javaflow.MyRunnable");
        MyRunnable runnable = (MyRunnable)clazz.newInstance();

        Continuation c = Continuation.startWith(runnable);
        System.out.println("returned a continuation");
    }
}
