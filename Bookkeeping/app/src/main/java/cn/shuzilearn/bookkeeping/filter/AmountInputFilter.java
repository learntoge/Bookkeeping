package cn.shuzilearn.bookkeeping.filter;
import android.text.InputFilter;
import android.text.Spanned;
public class AmountInputFilter implements InputFilter{
    private final int digitsBeforeDecimal;
    private final int digitsAfterDecimal;
    public AmountInputFilter(int digitsBeforeDecimal, int digitsAfterDecimal) {
        this.digitsBeforeDecimal = digitsBeforeDecimal;
        this.digitsAfterDecimal = digitsAfterDecimal;
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String destText = dest.toString();
        String resultingText = destText.substring(0, dstart) + source + destText.substring(dend);

        if (resultingText.equals(".")) {
            return "0."; // 防止输入只有一个点
        }
        if (!resultingText.matches("\\d*(\\.\\d*)?")) {
            return ""; // 阻止不符合规则的输入
        }
        String[] parts = resultingText.split("\\.");
        if (parts.length > 1) {
            // 小数点后的位数限制
            if (parts[1].length() > digitsAfterDecimal) {
                return "";
            }
        }
        // 小数点前的位数限制
        if (parts[0].length() > digitsBeforeDecimal) {
            return "";
        }
        return null;
    }
}
