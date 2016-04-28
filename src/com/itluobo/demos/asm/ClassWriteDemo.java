package com.itluobo.demos.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;


/**
 * Created by hannahzhang on 16/4/23.
 */
public class ClassWriteDemo implements Opcodes{

    public static void main(String[] args) throws Exception{
        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;

        cw.visit(49,ACC_PUBLIC + ACC_SUPER,
                "Hello",
                null,
                "java/lang/Object",
                null);

        cw.visitSource("Hello.java",null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,null);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/lang/Object",
                    "<init>",
                    "()V");
                    //false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC,
                    "main",
                    "([Ljava/lang/String;)V",
                    null,
                    null);
            mv.visitFieldInsn(GETSTATIC,
                    "java/lang/System",
                    "out",
                    "Ljava/io/PrintStream;");
            mv.visitLdcInsn("hello");
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/io/PrintStream",
                    "println",
                    "(Ljava/lang/String;)V");
                    //false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();


        }

        cw.visitEnd();

        FileOutputStream out = new FileOutputStream("/tmp/test/Hello.class");
        out.write(cw.toByteArray());

    }

}
