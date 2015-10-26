package com.Instio;

import com.github.mustachejava.Mustache;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList();
        Spark.staticFileLocation("public");
        Spark.init();
        Spark.post(
                "/create-account",
                ((request, response) -> {
                    User user = new User();
                    user.name = request.queryParams("username"); //Requesting the username
                    user.password = request.queryParams("password");
                    users.add(user);
                    response.redirect("/"); // "/" represents the top level. return homepage
                    return "";
                })
        );
        Spark.get( //just returning info
                "/accounts",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    m.put("count", users.size());
                    m.put("accounts", users);
                    return new ModelAndView(m, "accounts.html");
                }),
                new MustacheTemplateEngine()
        );
    }
}
