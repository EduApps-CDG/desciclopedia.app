package org.desciclopedia;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Linux {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String curl(@Nullable String[] params, String url) {
        try {
            if (params == null) {
                params = new String[] {
                        ""
                };
            }
            String args = String.join(" ", params);
            String fullcommand = "";
            fullcommand = "curl " + args + " " + url + "";
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(fullcommand);
            System.out.println("Run: " + fullcommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String response = null;
            String output = "";
            for (int x = 0; (response = stdInput.readLine()) != null; x++) {
                output += response + "\n";
            }
            while (p.isAlive()) {
                // NOTHING!
            }
            if (!p.isAlive()) {
                return String.join("\n", output);
            } else {
                return "this will never appear";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String wget(@Nullable String[] params, String url) {
        try {
            String args = String.join(" ", params);
            String fullcommand = "";
            fullcommand = "wget " + args + " \"" + url + "\"";
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(fullcommand);
            System.out.println("Run: " + fullcommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String response = null;
            String output = "";
            for (int x = 0; (response = stdInput.readLine()) != null; x++) {
                output += response + "\n";
            }
            while (p.isAlive()) {
                // NOTHING!
            }
            if (!p.isAlive()) {
                return String.join("\n", output);
            } else {
                return "this will never appear";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
