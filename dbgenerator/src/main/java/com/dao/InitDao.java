package com.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class InitDao {
    private final static String PATH = "C:\\Users\\ukevgen\\ukevgen\\Agilie\\1\\BizareChat\\app\\src\\main\\java";
    private final static String PACKAGE_NAME = "com.internship.pbt.bizarechat.db";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, PACKAGE_NAME);
        createEntities(schema);
        new DaoGenerator().generateAll(schema, PATH);
    }

    private static void createEntities(Schema schema) {
        Entity chats = schema.addEntity("Chats");

    }
}
