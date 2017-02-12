package com.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class InitDao {
    private final static String PROJECT_DIR = System.getProperty("user.dir");
    private final static String PACKAGE_NAME = "com.internship.pbt.bizarechat.db";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, PACKAGE_NAME);
        createEntities(schema);
        new DaoGenerator().generateAll(schema, PROJECT_DIR + "\\app");
    }

    private static void createEntities(Schema schema) {
        addDialog(schema);
        addMessage(schema);
        addUser(schema);
    }

    private static Entity addDialog(Schema schema){
        Entity dialog = schema.addEntity("Dialog");
        dialog.addIdProperty().primaryKey().autoincrement();
        dialog.addStringProperty("dialog_id");
        dialog.addStringProperty("created_at");
        dialog.addStringProperty("updated_at");
        dialog.addStringProperty("last_message");
        dialog.addLongProperty("last_message_date_sent");
        dialog.addIntProperty("last_message_user_id");
        dialog.addStringProperty("name");
        dialog.addStringProperty("photo");
        dialog.addStringProperty("occupants_ids");
        dialog.addIntProperty("type");
        dialog.addIntProperty("unread_messages_count");
        dialog.addStringProperty("xmpp_room_jid");
        return dialog;
    }

    private static Entity addMessage(Schema schema){
        Entity message = schema.addEntity("Message");
        message.addIdProperty().primaryKey().autoincrement();
        message.addStringProperty("message_id");
        message.addStringProperty("created_at");
        message.addStringProperty("updated_at");
        message.addStringProperty("attachments");
        message.addStringProperty("read_ids");
        message.addStringProperty("delivered_ids");
        message.addStringProperty("chat_dialog_id");
        message.addLongProperty("date_sent");
        message.addStringProperty("message");
        message.addStringProperty("recipient_id");
        message.addIntProperty("sender_id");
        message.addIntProperty("read");
        return message;
    }

    private static Entity addUser(Schema schema){
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addIntProperty("user_id");
        user.addStringProperty("full_name");
        user.addStringProperty("email");
        user.addStringProperty("login");
        user.addStringProperty("phone");
        user.addStringProperty("website");
        user.addStringProperty("created_at");
        user.addStringProperty("updated_at");
        user.addStringProperty("last_request_at");
        user.addStringProperty("external_user_id");
        user.addLongProperty("facebook_id");
        user.addStringProperty("twitter_id");
        user.addIntProperty("twitter_digits_id");
        user.addIntProperty("blob_id");
        user.addStringProperty("custom_data");
        user.addStringProperty("user_tags");
        return user;
    }
}
