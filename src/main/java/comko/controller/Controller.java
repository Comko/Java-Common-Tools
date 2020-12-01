package comko.controller;

import comko.utils.GoogleTranslateUtils;
import comko.utils.JsonFormatUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import comko.utils.AESUtils;
import comko.utils.Base64Utils;
import comko.utils.JasyptUtils;
import comko.utils.TimeUtils;
import comko.utils.UrlUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    @FXML
    private TextArea jasyptResult;

    @FXML
    private TextArea jasyptInput;

    @FXML
    private TextField jasyptSalt;

    @FXML
    private TextArea base64Result;

    @FXML
    private TextArea base64Original;

    @FXML
    private TextArea regularInput;

    @FXML
    private TextField regularExpression;

    @FXML
    private Text regularResult;

    @FXML
    private TextField aesKey;

    @FXML
    private TextArea aesInput;

    @FXML
    private TextArea aesResult;

    @FXML
    private TextArea jsonView;

    @FXML
    private TextField timestampInput;

    @FXML
    private TextField timestampResult;

    @FXML
    private TextField timeInput;

    @FXML
    private TextField timeResult;

    @FXML
    private TextArea urlInput;

    @FXML
    private TextArea urlResult;

    @FXML
    private TextArea googleTranslateInput;

    @FXML
    private TextArea googleTranslateResult;

    public void jasyptEncrypt() {
        String salt = jasyptSalt.getText();
        if (StringUtils.isEmpty(salt)) {
            return;
        }
        String input = jasyptInput.getText();
        try {
            String outPut = JasyptUtils.encryptPwd(salt, input);
            jasyptResult.setText(outPut);
        } catch (Exception e) {
            jasyptResult.setText("Encrypt error");
        }
    }

    public void jasyptDecrypt() {
        String salt = jasyptSalt.getText();
        if (StringUtils.isEmpty(salt)) {
            return;
        }
        String input = jasyptInput.getText();
        try {
            String outPut = JasyptUtils.decyptPwd(salt, input);
            jasyptResult.setText(outPut);
        } catch (Exception e) {
            jasyptResult.setText("Decrypt error");
        }
    }

    public void jasypt32BitSaltRandom() {
        int len = 32;
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < len; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        jasyptSalt.setText(sb.toString());
    }

    public void jasypt64BitSaltRandom() {
        int len = 64;
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < len; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        jasyptSalt.setText(sb.toString());
    }

    public void base64Encrypt() {
        try {
            String value = Base64Utils.encryptBASE64(base64Original.getText());
            base64Result.setText(value);
        } catch (Exception e) {
            base64Result.setText("ERROR");
        }
    }

    public void base64Decrypt() {
        try {
            String value = new String(Base64Utils.decryptBASE64(base64Original.getText()));
            base64Result.setText(value);
        } catch (Exception e) {
            base64Result.setText("ERROR");
        }
    }

    public void regularCheck() {
        String v = regularExpression.getText();
        if (StringUtils.isEmpty(v)) {
            regularResult.setText("Blank");
            regularResult.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
            regularResult.setFill(Color.RED);
        } else {
            try {
                String v1 = regularInput.getText();
                Pattern pattern = Pattern.compile(v);
                boolean flag = pattern.matcher(v1).matches();
                if (flag) {
                    regularResult.setText("Passed");
                    regularResult.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                    regularResult.setFill(Color.GREEN);
                } else {
                    regularResult.setText("Fail");
                    regularResult.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                    regularResult.setFill(Color.RED);
                }
            } catch (Exception e) {
                regularResult.setText("Fail");
                regularResult.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                regularResult.setFill(Color.RED);
            }
        }
    }

    public void aesEncrypt() {
        String key = aesKey.getText();
        if (StringUtils.isEmpty(key)) {
            return;
        }
        String input = aesInput.getText();
        try {
            String outPut = AESUtils.encrypt(input, key);
            aesResult.setText(outPut);
        } catch (Exception e) {
            aesResult.setText("ERROR");
        }
    }

    public void aesDecrypt() {
        String key = aesKey.getText();
        if (StringUtils.isEmpty(key)) {
            return;
        }
        String input = aesInput.getText();
        try {
            String outPut = AESUtils.decrypt(input, key);
            aesResult.setText(outPut);
        } catch (Exception e) {
            aesResult.setText("ERROR");
        }
    }

    public void jsonView() {
        String value = jsonView.getText();
        try {
            if (StringUtils.isEmpty(value)) {
                return;
            }
            String result = JsonFormatUtils.JsonFormat(value);
            jsonView.setText(result);
        } catch (Exception e) {
            jsonView.setText("ERROR");
        }
    }

    public void timestampTransfer() {
        try {
            String input = timestampInput.getText();
            String result = TimeUtils.stampToTime(input);
            timestampResult.setText(result);
        } catch (Exception e) {
            timestampResult.setText("ERROR");
        }
    }

    public void timesTransfer() {
        try {
            String input = timeInput.getText();
            String result = TimeUtils.dateToStamp(input);
            timeResult.setText(result);
        } catch (Exception e) {
            timeResult.setText("ERROR");
        }
    }

    public void urlEncode() {
        try {
            String input = urlInput.getText();
            String result = UrlUtils.getURLEncoderString(input);
            urlResult.setText(result);
        } catch (Exception e) {
            urlResult.setText("ERROR");
        }
    }

    public void urlDecode() {
        try {
            String input = urlInput.getText();
            String result = UrlUtils.URLDecoderString(input);
            urlResult.setText(result);
        } catch (Exception e) {
            urlResult.setText("ERROR");
        }
    }

    public void translateToChinese() {
        String input = googleTranslateInput.getText();
        if (StringUtils.isNotEmpty(input)) {
            try {
                String result = GoogleTranslateUtils.translate(input, "auto", "zh_cn");
                googleTranslateResult.setText(result);
            } catch (Exception e) {
                googleTranslateResult.setText("ERROR");
            }
        }
    }

    public void translateToEnglish() {
        String input = googleTranslateInput.getText();
        if (StringUtils.isNotEmpty(input)) {
            try {
                String result = GoogleTranslateUtils.translate(input, "auto", "en");
                googleTranslateResult.setText(result);
            } catch (Exception e) {
                googleTranslateResult.setText("ERROR");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
