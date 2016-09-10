package com.louisgeek.numberpickerviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louisgeek.numberpickerviewlib.tools.SizeTool;
import com.louisgeek.numberpickerviewlib.tools.StringTool;


/**
 * Created by louisgeek on 2016/8/25.
 */
public class NumberPickerView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "NumberPickerView";
    private Context mContext;
    private final int DEFAULT_VIEW_SIZE=28;
    private int mViewSize=0;
    private boolean isInt=true;

    public void setMinAndMaxAndIncrement(double minValue,double maxValue,double increment) {
        mMinValue = minValue;
        mMaxValue = maxValue;
        mIncrement = increment;
        isInt=false;
        //
        initValue();
    }
    public void setMinAndMaxAndIncrement(int minValue,int maxValue,int increment) {
        mMinValue = minValue;
        mMaxValue = maxValue;
        mIncrement = increment;
        isInt=true;
        //
        initValue();
    }

    /**
     * 不设置默认  true
     * @param isInt
     */
    public void setIsInt(boolean isInt) {
        if (isInt){
            idedvalue.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            idedvalue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        this.isInt = isInt;
        //
        initValue();
    }

    private void initValue() {
        mNowValue=getNumberValue();
        setNumberValue();
    }

   // private boolean mHasFocus=false;
    private double mMinValue=0.0;
    private double mMaxValue=Double.MAX_VALUE;

    public String getNowValue() {
        double tempD= getNumberValue();
        if (isInt){
            int temp= (int) tempD;
          return String.valueOf(temp);
        }else{
        return String.valueOf(tempD);
        }

    }

    public void setNowValue(double nowValue) {
        mNowValue = nowValue;
        setNumberValue();
    }
    public void setNowValue(String nowValueStr) {
        if (nowValueStr!=null&&!nowValueStr.equals("")&&!nowValueStr.equals("null")){
            try
            {
                mNowValue = Double.valueOf(nowValueStr);
                setNumberValue();
            }
            catch (Exception e)
            {
                //
            }

        }
    }

    private double mNowValue=0.0;
    private double mIncrement=1.0;//增量

    EditText idedvalue;
    public NumberPickerView(Context context) {
        this(context, null);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.NumberPickerView);
        boolean isIntFromXml=typedArray.getBoolean(R.styleable.NumberPickerView_isInt,true);
        float incrementFromXml=typedArray.getFloat(R.styleable.NumberPickerView_increment,1);
        mViewSize=typedArray.getDimensionPixelOffset(R.styleable.NumberPickerView_viewSize, SizeTool.dp2px(mContext,DEFAULT_VIEW_SIZE));
        isInt=isIntFromXml;
        mIncrement=Double.valueOf(String.valueOf(incrementFromXml));

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_number_picker, this);
        TextView idtvadd = (TextView) view.findViewById(R.id.id_tv_add);
        idedvalue = (EditText) view.findViewById(R.id.id_ed_value);
        TextView idtvremove = (TextView) view.findViewById(R.id.id_tv_remove);
        Log.d(TAG, "initView: mViewSize:"+mViewSize);
       // idtvadd.setWidth((int) mViewSize);
        ViewGroup.LayoutParams  vlp=idtvadd.getLayoutParams();
        vlp.width= mViewSize;
        vlp.height=mViewSize;
        idtvadd.setLayoutParams(vlp);
       // idtvadd.setHeight((int) mViewSize);
        /*idtvremove.setWidth((int) mViewSize);
        idtvremove.setHeight((int) mViewSize);*/
        ViewGroup.LayoutParams  vlp2=idtvremove.getLayoutParams();
        vlp2.width=mViewSize;
        vlp2.height= mViewSize;
        idtvremove.setLayoutParams(vlp2);


        ViewGroup.LayoutParams  vlp3=idedvalue.getLayoutParams();
        vlp3.height= mViewSize;
        //idedvalue.setHeight((int) mViewSize);
        idedvalue.setLayoutParams(vlp3);

        idtvadd.setOnClickListener(this);
       // idedvalue.setOnClickListener(this);
        idtvremove.setOnClickListener(this);
      /*  idedvalue.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mHasFocus=hasFocus;
                Log.d(TAG, "onFocusChange: hasFocus:"+hasFocus);
                   // initValue();
               // }
            }
        });*/
        if (StringTool.isNullOrNullStrOrBlankStr(idedvalue.getText().toString())){
            Log.d(TAG, "initView: setNowValue(\"0\")");
            setNowValue("0");
        }

        //idedvalue.setText("111");
        idedvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged:");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged:");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged:");
              /* if (s.toString().trim().equals("")){
                   if (isInt){
                    idedvalue.setText("0");
                   }else{
                    idedvalue.setText("0.0");
                   }
               }*/
               //
            }
        });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.id_tv_add) {
            Log.d(TAG, "onClick: id_tv_add");
            mNowValue=getNumberValue()+mIncrement;

            setNumberValue();
            if (onNumberChangeListener!=null){
            onNumberChangeListener.onNumberAdd(v);}
        }else if (i == R.id.id_tv_remove){
            Log.d(TAG, "onClick: id_tv_remove");
            mNowValue=getNumberValue()-mIncrement;

            setNumberValue();
            if (onNumberChangeListener!=null){
            onNumberChangeListener.onNumberRemove(v);}
        }
    }

    private void  setNumberValue(){
        mNowValue=mNowValue>mMaxValue?mMaxValue:mNowValue;
        mNowValue=mNowValue<mMinValue?mMinValue:mNowValue;
    if (isInt){
        setNumberIntValue();
    }else{
        setNumberDoubleValue();
    }

    }
    private void  setNumberDoubleValue(){
        if (idedvalue!=null){
            if (!idedvalue.getText().equals(mNowValue)) {
            idedvalue.setText(String.valueOf(mNowValue));
            idedvalue.setSelection(String.valueOf(mNowValue).length());
            }
        }
    }
    private void  setNumberIntValue(){
        if (idedvalue!=null){
            int nowIntValue= (int) mNowValue;
            if (!idedvalue.getText().equals(nowIntValue)) {
                idedvalue.setText(String.valueOf(nowIntValue));
                idedvalue.setSelection(String.valueOf(nowIntValue).length());
            }
        }
    }
    private double  getNumberValue(){
        double temp=0;
        if (idedvalue!=null){
           String valueStr= idedvalue.getText().toString();
            if (valueStr!=null&&!valueStr.equals("")){
                temp=Double.valueOf(valueStr);
                if (isInt){
                    int tempINT= (int) temp;
                    temp=tempINT;
                }

            }
        }
        return temp;
    }


    public interface  OnNumberChangeListener{
       void onNumberAdd(View v);
       void onNumberRemove(View v);
    }

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public  OnNumberChangeListener onNumberChangeListener;
}
