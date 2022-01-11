package io.financialfuture.util.recaptcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReCaptchaService {
  public StringBuffer validate(String response, String remoteAddress) throws IOException {
    String urlString =  "https://www.google.com/recaptcha/api/siteverify?secret="+ System.getenv("RECAPTCHA_SECRET_KEY") + "&response=" + response + "&remoteip=" + remoteAddress;
    URL url = new URL(urlString);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setFixedLengthStreamingMode(0);
    con.setDoOutput(true);
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer content = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    con.disconnect();
    return content;
  }
}
