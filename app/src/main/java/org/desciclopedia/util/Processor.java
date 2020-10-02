package org.desciclopedia.util;

import org.desciclopedia.Global;
import org.intellij.lang.annotations.Language;

/**
 * processador wiki
 */
public class Processor {
    /**
     * O primeiro bot da DP que não está na DP:
     */
    public static final @Language("HTML") String js ="" +
            "<script type='text/javascript'>\n" +
            "function internal() {\n" +
              "var body = document.getElementById('body');\n" + //n
              "var text = body.textContent;\n" + //n
              "body.textContent = '';\n" +//n
              "var banana = text.split('');\n" +
              "var isp = '';\n" +
              "var fsp = '';\n" +
              "var newtext = '';\n" +
              "console.log('Inicializando processo de wikificação...');\n" +

              "for (var x = 0; x < banana.length; x++) {\n" + //A1


                "if (banana[x] == '[' && banana[x+1] == '[') {\n" + //A2
                    "x += 0;\n" +
                    "banana[x] = '';\n" +
                    "banana[x+ 1] = '';\n" +
                    "" +
                    "var linktext = ['',''];\n" +
                    "var lt = 0;" +
                    "" +
                    "for (var y = x; y < banana.length; y++) {\n" + //A3
                      "if (banana[y] == ']' && banana[y+1] == ']') {\n" + //A4
                        "x = y + 0;\n" + //n
                        "y = banana.lenght + 1;\n" + //n
                        "" +
                        "if (lt == 0) {" +
                          "linktext[1] = linktext[0];" +
                        "}" +
                        "" +
                        "newtext += '<a href=\"" + Global.DOMAIN + "wiki/' + linktext[0] + '\" id=\"link\">' + linktext[1] + '</a>';\n" + //n
                      "} else if (banana[y] == '|') {" +
                        "lt += 1;" +
                      "} else {\n" + //A4
                        "linktext[lt] += banana[y];\n" + //n
                      "}\n" +
                    "}" +
                "} else if(banana[x] == '\\'' && banana[x+1] == '\\'' && banana[x+2] == '\\'') {" +
                  "x += 0;\n" +
                  "banana[x] = '';\n" +
                  "banana[x+ 1] = '';\n" +
                  "banana[x+2] = '';" +
                  "" +
                  "var baldtext = '';" +
                  "" +
                  "for (var y = x; y < banana.length; y++) {\n" + //A3
                    "if (banana[y] == '\\'' && banana[y+1] == '\\'' && banana[y+2] == '\\'') {\n" + //A4
                      "x = y + 0;\n" + //n
                      "y = banana.lenght + 1;\n" + //n
                      "" +
                      "newtext += '<b>' + baldtext + '</b>';\n" + //n
                    "} else {\n" + //A4
                      "baldtext += banana[y];\n" + //n
                    "}\n" +
                  "}" +
                "} else if(banana[x] == '\\'' && banana[x+1] == '\\'') {" +
                  "x += 0;\n" +
                  "banana[x] = '';\n" +
                  "banana[x+ 1] = '';\n" +
                  "" +
                  "var italactext = '';" +
                  "" +
                  "for (var y = x; y < banana.length; y++) {\n" + //A3
                    "if (banana[y] == '\\'' && banana[y+1] == '\\'') {\n" + //A4
                    "x = y + 0;\n" + //n
                    "y = banana.lenght + 1;\n" + //n
                    "" +
                    "newtext += '<i>' + baldtext + '</i>';\n" + //n
                  "} else {\n" + //A4
                    "italactext += banana[y];\n" + //n
                  "}\n" +
                "}" +
              "} else {" +
                "newtext += banana[x];" +
              "}" +
              "}\n" + //A2

              "console.log('new text: ' + newtext);\n" +
              "body.innerHTML = newtext;\n" +
              "console.log('Processo de wikificação finalizado!');\n" +
            "}\n" +  //A1



            "window.onload = function() {" +
            "console.log('consolo');" +
              "internal();" +
            "}" +
            "</script>";

    public static final @Language("html") String css = "" +
            "<style>" +
            "* {" +
              "font-family: initial;" +
            "}" +

            "body {" +
              "background: " + Global.BG_COLOR + ";" +
            "}" +
            "</style>";
}
