package vn.nganht.calculatoredittext;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculatorTextWatcher implements TextWatcher {

    private EditText mEtCalc;

    public CalculatorTextWatcher(EditText etCalc) {
        mEtCalc = etCalc;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        String text = s.toString()
                .replaceAll(Constant.WHITE_SPACE, "")
                .replaceAll(Constant.DOT, "")
                .replaceAll(Constant.COMMA, "");
        s.clear();
        if (!text.matches(Constant.PATTERN_NUMBER)) {
            return;
        }
        String[] inputs = text.split(Constant.PATTERN_SPLIT_NUMBER_OPERATOR);
        StringBuilder sb = new StringBuilder();
        for (String input : inputs) {
            if (TextUtils.isEmpty(input)) {
                continue;
            }
            if (input.length() > Constant.MAX_LENGTH_NUMBER) {
                input = input.substring(0, Constant.MAX_LENGTH_NUMBER);
            }
            if (TextUtils.isDigitsOnly(input)) {
                sb.append(NumberFormat.getNumberInstance(Locale.getDefault()).format(new BigInteger(input)));
                continue;
            }
            sb.append(Constant.WHITE_SPACE).append(input.charAt(input.length() - 1)).append(Constant.WHITE_SPACE);
        }
        mEtCalc.removeTextChangedListener(this);
        mEtCalc.append(sb.toString());
        mEtCalc.addTextChangedListener(this);
    }
}
