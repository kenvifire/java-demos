package com.itluobo.demos.javassist;

import javassist.*;

/**
 * Created by kenvi on 16/4/28.
 */
public class NewClassDemo {
    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        //create class Point
        CtClass point = pool.makeClass("Point");

        //create field Point,init with 0
        CtField x = new CtField(CtClass.intType,"x", point);
        point.addField(x, "0");

        //create method getX
        CtMethod getX = CtNewMethod.make("public int getX(){return this.x;}",point);
        point.addMethod(getX);

        //create method setX
        CtMethod setX = CtNewMethod.make("public void setX(int x) { this.x = x;}", point);
        point.addMethod(setX);

        String dir = Tester.class.getResource("/").getPath();
        point.writeFile(dir);

        Class pointClass = Class.forName("Point");
        Object pointInstance = pointClass.newInstance();
    }
}
