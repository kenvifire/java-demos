package com.itluobo.demos.asm;

import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * Created by hannahzhang on 16/4/23.
 */
public class ClassWriteDemo {

    public static void main(String[] args) throws Exception{
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[] {"pkg/Mesurable"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        byte[] b = cw.toByteArray();
        File file = new File("/tmp/Comparable.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(b);
        fos.close();
    }

}
