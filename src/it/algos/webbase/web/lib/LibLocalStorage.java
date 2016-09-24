package it.algos.webbase.web.lib;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import elemental.json.JsonArray;

import java.time.Instant;
import java.util.Date;

/**
 * Access the browser's local storage
 * Created by alex on 24-09-2016.
 */
public class LibLocalStorage {


    /**
     * Check if the browser supports local storage
     */
    public static boolean supportsLocalStorage() {

//        JavaScript.getCurrent().addFunction("com.example.foo.myfunc",
//                new JavaScriptFunction() {
//                    @Override
//                    public void call(JsonArray arguments) {
//                        Notification.show("Received call");
//                    }
//                });
//
//        Link link = new Link("Send Message", new ExternalResource(
//                "javascript:com.example.foo.myfunc()"));

//        JavaScript.getCurrent().execute("alert('Hello')");

//        String jsFunc = "if (typeof(Storage) !== 'undefined') { alert('supported');return true;} else {alert('not supported');return false;} ";
//        JavaScript.getCurrent().execute(jsFunc);

        JavaScriptFunction jsFunc2 = new JavaScriptFunction() {
            @Override
            public void call(JsonArray arguments) {
                Notification.show("Received call");
            }
        };

        JavaScript.getCurrent().addFunction("com.example.foo.myfunc", jsFunc2);

        JavaScript.getCurrent().execute("com.example.foo.myfunc");



        return false;
    }


//    /**
//     * Create or update a local storage entry in the browser.
//     *
//     * @param key       the key
//     * @param value     the value
//     * @param path      the path
//     * @param expirySec the expiration time in seconds,
//     *                  A positive value indicates that the cookie will expire after that many seconds have passed.
//     *                  A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits.
//     *                  A zero value causes the cookie to be deleted.
//     */
//    public static void setCookie(String key, String value, String path, int expirySec) {
//
//        JavaScript js = JavaScript.getCurrent();
//
//        if (js != null) {
//
//            // protects the value
//            value = protect(value);
//
//            if (expirySec == 0) {
//                String cmd = String.format("document.cookie = '%s=; expires=Thu, 01 Jan 1970 00:00:01 GMT';", key);
//                js.execute(cmd);
//                return;
//            }
//
//            if (expirySec > 0) {
//                Instant i = Instant.now().plusSeconds(expirySec);
//                Date d = Date.from(i);
//                String utc = getUTCString(d);
//                String cmd = String.format("document.cookie = '%s=%s; path=%s; expires=%s';", key, value, path, utc);
//                js.execute(cmd);
//                return;
//            }
//
//            if (expirySec < 0) {
//                String cmd = String.format("document.cookie = '%s=%s; path=%s';", key, value, path);
//                js.execute(cmd);
//                return;
//            }
//        }
//
//    }

}
