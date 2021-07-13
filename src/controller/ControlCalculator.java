package controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ControlCalculator {

    private Double firstNumber;
    private Double secondNumber;
    private JTextField view;
    private boolean isCalculating = false;
    private boolean isReset = false;
    private String operator = "";
    private Double memory = 0.0;
    DecimalFormat dc = new DecimalFormat("#.########");

    public void setOperator(String operator) {
        this.operator = operator;

    }

    public ControlCalculator(JTextField view) {
        this.view = view;
        operator = "";
        view.setText("0");
    }

    public void Number(JButton btn) {
        if (isCalculating || isReset) {
            // nếu đang tính thì set text = 0 để số sau không đè lên số trước
            view.setText("0");
            // set lại để bấm lại được nhiều lần cho 1 button
            isCalculating = false;
            isReset = false;
        }
        String temp;
        if (view.getText().equalsIgnoreCase("0")) {
            view.setText(btn.getText() + "");
        } else {
            temp = view.getText() + btn.getText();
            view.setText(temp + "");
        }

    }

    public void Sqrt() {
        if (view.getText().equalsIgnoreCase("ERROR")) {
            return;
        }
        double result = new BigDecimal(view.getText()).doubleValue();
        if (result < 0) {
            view.setText("Error");
        } else {
            String display = dc.format(Math.sqrt(result));
            view.setText(display);
        }
        isReset = true;
    }

    public void Dot() {
        if (view.getText().equalsIgnoreCase("ERROR")) {
            return;
        }
//        cho chấm vào kết quả hoặc khi đã bấm phép tính
//        các số sau sẽ đc thêm vào sau dấu chấm
        if (isCalculating) {
//            return;
            view.setText("0.");
            isCalculating = false;
        } else {
            Double result = Double.parseDouble(view.getText());
            // nếu có chấm rồi thì không chấm nữa
            if (!view.getText().contains(".")) {
                view.setText(dc.format(result) + ".");
            }
        }
        //cho chấm kết quả
        isReset = false;
    }

    public void Negative() {
        if (isCalculating) {
            return;
        }
        if (view.getText().equalsIgnoreCase("ERROR")) {
            return;
        }
        StringBuilder temp = new StringBuilder(view.getText());
        if (!view.getText().equalsIgnoreCase("0")) {
            if (temp.charAt(0) != '-') {
                temp.insert(0, "-");
            } else {
                temp.deleteCharAt(0);
            }
        }
        view.setText(temp + "");
        isCalculating = false;
        isReset = false;
    }

    public void Persent() {
        if (view.getText().equalsIgnoreCase("ERROR")) {
            return;
        }
        double result = new BigDecimal(view.getText()).doubleValue() / 100;
        String display = dc.format(result);
        view.setText(display);
        isReset = true;
    }

    public void Invert() {
        if (view.getText().equalsIgnoreCase("ERROR")) {
            return;
        }
        double result = new BigDecimal(view.getText()).doubleValue();
        if (result != 0) {
            String display = String.valueOf(1 / result);
            view.setText(display);
        } else {
            view.setText("Erorr");
        }
        isReset = true;
    }

    public void Madd() {
        if (!view.getText().equalsIgnoreCase("Error")) {
            memory = memory + Double.parseDouble(view.getText());
//            isCalculating = false;
        }
        isReset = true;
    }

    public void Msub() {
        if (!view.getText().equalsIgnoreCase("Error")) {
            memory = memory - Double.parseDouble(view.getText());
//            isCalculating = false;

        }
        isReset = true;
    }

    public void MC() {
        memory = 0.0;
    }

    public void Clear() {
        view.setText("0");
        isCalculating = true;
        isReset = false;
        operator = "";
    }

    public void MR() {
        view.setText(dc.format(memory) + "");
    }

    public void Result() {
        if (view.getText().equalsIgnoreCase("Error")) {
            view.setText("0");
        } else {
            calculate();
            // set phép toán không có gì tránh lưu phép toán 
            // khi người dùng nhập 1 chữ số và ấn bằng thì phép toán sẽ lưu
            operator = "";
        }
        isCalculating = false;
        isReset = true;
    }

    public void calculate() {
        // kiểm tra xem có tính toán chưa, nếu có tính toán
        // nhưng không nhập số thứ 2 thì sẽ bỏ qua bước này
        if (!isCalculating) {
            if ("".equals(operator)) {
                firstNumber = Double.parseDouble(view.getText());
            } // khi mà có phép toán
            else {
                // lấy số thứ 2 
                secondNumber = Double.parseDouble(view.getText());
                switch (operator) {
                    case "+":
                        firstNumber = firstNumber + secondNumber;
                        break;
                    case "-":
                        firstNumber = firstNumber - secondNumber;
                        break;
                    case "*":
                        firstNumber = firstNumber * secondNumber;
                        break;
                    case "/":
                        // to divide value
                        if (secondNumber == 0) {
                            view.setText("ERROR");
                            return;
                        } else {
                            firstNumber = firstNumber / secondNumber;
                            break;
                        }
                }
            }

        }
        // đã có phép tính -> xóa số thứ nhất và nhập số thứ 2 ở pressButton
        isCalculating = true;

        String result = dc.format(firstNumber);
        view.setText(result);
    }

}
