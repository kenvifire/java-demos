package com.itluobo.demos.asm;

import jdk.internal.org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

/**
 * Created by hannahzhang on 16/4/23.
 */
public class AddFieldAdapter extends ClassVisitor{
    private int fAcc;
    private String fName;
    private String fDesc;
    private boolean isFieldPresent;

    public AddFieldAdapter(ClassVisitor cv, int fAcc, String fName,
                           String fDesc) {
        super(Opcodes.ASM4, cv);
        this.fAcc = fAcc;
        this.fName = fName;
        this.fDesc = fDesc;
    }
    @Override
    public void visitEnd() {
        if(!isFieldPresent) {
            FieldVisitor fv = cv.visitField(fAcc, fName, fDesc, null, null);
            if(fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if(name.equals(fName)) {
            isFieldPresent = true;
        }
        return cv.visitField(access, name, desc, signature, value);
    }

    public static void main(String[] args) {
        //AddFieldAdapter addFieldAdapter = new AddFieldAdapter()
    }
}
