package com.example.er_ja.exchangeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtAmount;
    private Button btnExchange;
    private RadioGroup rgFrom,rgTo;
    private RadioButton rbFromDollar,rbFromEuro,rbFromPound,rbToDollar,rbToEuro,rbToPound,rbFromDisabled,rbToDisabled;
    private ImageView imgFrom,imgTo;
    private final double EUR_USD=1.17;
    private final double EUR_GBP=0.89;
    private final double GBP_USD=1.3;
    private final double USD_EUR=1/EUR_USD;
    private final double GBP_EUR=1/EUR_GBP;
    private final double USD_GBP=1/GBP_USD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        txtAmount.selectAll();
        rgFrom.clearCheck();
        rgTo.clearCheck();
        rgFrom.check(rbFromEuro.getId());
        rbToEuro.setEnabled(false);
        rbToDisabled = rbToEuro;
        rgTo.check(rbToDollar.getId());
        rbFromDollar.setEnabled(false);
        rbFromDisabled = rbFromDollar;

        txtAmount.setOnClickListener(v -> txtAmount.selectAll());
        rgFrom.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId==rbFromEuro.getId()){
                rbToEuro.setEnabled(false);
                rbToDisabled.setEnabled(true);
                rbToDisabled=rbToEuro;
                imgFrom.setImageResource(R.drawable.ic_euro);
            }else if (checkedId==rbFromDollar.getId()){
                rbToDollar.setEnabled(false);
                rbToDisabled.setEnabled(true);
                rbToDisabled=rbToDollar;
                imgFrom.setImageResource(R.drawable.ic_dollar);
            }else{
                rbToPound.setEnabled(false);
                rbToDisabled.setEnabled(true);
                rbToDisabled=rbToPound;
                imgFrom.setImageResource(R.drawable.ic_pound);
            }

        });
        rgTo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==rbToEuro.getId()){
                    rbFromEuro.setEnabled(false);
                    rbFromDisabled.setEnabled(true);
                    rbFromDisabled=rbFromEuro;
                    imgTo.setImageResource(R.drawable.ic_euro);
                }else if (checkedId==rbToDollar.getId()){
                    rbFromDollar.setEnabled(false);
                    rbFromDisabled.setEnabled(true);
                    rbFromDisabled=rbFromDollar;
                    imgTo.setImageResource(R.drawable.ic_dollar);
                }else{
                    rbFromPound.setEnabled(false);
                    rbFromDisabled.setEnabled(true);
                    rbFromDisabled=rbFromPound;
                    imgTo.setImageResource(R.drawable.ic_pound);
                }
            }
        });
        btnExchange.setOnClickListener(v -> exchange());
    }
    private void initViews(){
        txtAmount = findViewById(R.id.txtAmount);
        btnExchange = findViewById(R.id.btnExchange);
        rgFrom = findViewById(R.id.rgFrom);
        rgTo = findViewById(R.id.rgTo);
        rbFromDollar = findViewById(R.id.rbFromDollar);
        rbFromEuro = findViewById(R.id.rbFromEuro);
        rbFromPound = findViewById(R.id.rbFromPound);
        rbToDollar = findViewById(R.id.rbToDollar);
        rbToEuro = findViewById(R.id.rbToEuro);
        rbToPound = findViewById(R.id.rbToPound);
        imgFrom = findViewById(R.id.imgFrom);
        imgTo = findViewById(R.id.imgTo);
    }

    private void exchange() {
        try{
            double num = Double.parseDouble(String.valueOf(txtAmount.getText()));
            double res;
            String monedaOrigen,monedaDestino;
            if(rbFromEuro.isChecked()){
                monedaOrigen="€";
                if(rbToDollar.isChecked()){
                    monedaDestino="$";
                    res = num*EUR_USD;
                }else{
                    monedaDestino="£";
                    res = num*EUR_GBP;
                }
            }else if(rbFromDollar.isChecked()){
                monedaOrigen="$";
                if(rbToEuro.isChecked()){
                    monedaDestino="€";
                    res = num*USD_EUR;
                }else{
                    monedaDestino="£";
                    res = num*USD_GBP;
                }
            }else{
                monedaOrigen="£";
                if(rbToEuro.isChecked()){
                    monedaDestino="€";
                    res = num*GBP_EUR;
                }else{
                    monedaDestino="$";
                    res = num*GBP_USD;
                }
            }
            Toast.makeText(this,String.format("%.2f %s = %.2f %s",num,monedaOrigen,res,monedaDestino),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            txtAmount.setText("0.00");
        }
    }
}
