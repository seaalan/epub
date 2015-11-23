package controllers.utility;

import play.Play;

import java.io.File;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/20/2015
 */
public final class Constant {
    // get the root path of the Play project
	public static File ROOT_PATH = Play.application().path();

    public static String EMAIL_NAME = "123@163.com";
    public static String EMAIL_PASSWORD = "123";
}
