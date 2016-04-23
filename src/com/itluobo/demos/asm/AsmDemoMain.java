package com.itluobo.demos.asm;

import jdk.internal.org.objectweb.asm.ClassReader;

/**
 * Created by hannahzhang on 16/4/23.
 */
public class AsmDemoMain {
    public static void main(String[] args) throws Exception {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(cp, 0);
    }
}
