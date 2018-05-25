package cn.com.futurelottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.com.futurelottery.R;
import cn.com.futurelottery.utils.ToastUtils;

/**
 *
 * @author hiwhitley
 * @date 2016/7/4
 */
public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String TAG = "AmountView";
    private int amount = 1;
    private int goodsStorage = 10;
    private OnAmountChangeListener mListener;

    private EditText etAmount;
    private RelativeLayout btnDecrease;
    private RelativeLayout btnIncrease;

    private ImageView imageView;
    private int currentValue=1;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        etAmount = findViewById(R.id.etAmount);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease =  findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, 35);
        int btnHight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnHight, 35);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        obtainStyledAttributes.recycle();
        LayoutParams btnParams = new LayoutParams(btnWidth, btnHight);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
      /*  if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }*/

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoodsStorage(int goodsStorage) {
        this.goodsStorage = goodsStorage;
    }

    public int getCurrentValue() {
        return Integer.parseInt(etAmount.getText().toString());
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        etAmount.setText(String.valueOf(currentValue));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > 1) {
                amount--;
                etAmount.setText(amount + "");
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < goodsStorage) {
                amount++;
                etAmount.setText(amount + "");
            }
        }

        etAmount.clearFocus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty()) {
            return;
        }
        amount = Integer.valueOf(s.toString());
        if (amount > goodsStorage) {
            ToastUtils.showToast("最多输入"+ goodsStorage );
            etAmount.setText(goodsStorage + "");
            return;
        }
        if (amount<1){
            ToastUtils.showToast("最少输入"+ 1 );
            etAmount.setText("1");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    public int getValue(){

        return amount;
    }

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

}
