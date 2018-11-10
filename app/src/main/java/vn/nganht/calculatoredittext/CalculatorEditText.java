package vn.nganht.calculatoredittext;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

public class CalculatorEditText extends android.support.v7.widget.AppCompatEditText {
    public static final String ALLOW_DIGITS = "0123456789+-*/. ";

    public CalculatorEditText(Context context) {
        super(context);
        init();
    }

    public CalculatorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalculatorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence input, int start, int end, Spanned dst, int dstart, int dend) {
                        if (input.length() > 0 && !ALLOW_DIGITS.contains(String.valueOf(input.charAt(0)))) {
                            // if not alphanumeric, disregard the latest input
                            // by returning an empty string
                            return "";
                        }
                        return null;
                    }
                }
        });
        addTextChangedListener(new CalculatorTextWatcher(this));
    }
}
