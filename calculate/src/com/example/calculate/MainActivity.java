package com.example.calculate;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static String num = "0";
	private static String tempNum = "0";
	private static String countNum = "";
	private String operator = "";
	private EditText input_num;
	private TextView input_formula;
	//！获取数字按钮ID
	private int num_btnId[] = {R.id.btn_zero,R.id.btn_one,R.id.btn_two,R.id.btn_three,R.id.btn_four,
			R.id.btn_five,R.id.btn_six,R.id.btn_seven,R.id.btn_eight,R.id.btn_night,R.id.btn_point};
	private Button num_btn[] = new Button[num_btnId.length];
	//获取数字按钮ID
	//！获取运算符按钮
	private int operator_btnId[] = {R.id.btn_divisor,R.id.btn_product,R.id.btn_minus,R.id.btn_add,R.id.btn_equal};
	private Button operator_btn[] = new Button[operator_btnId.length];
	//获取运算符按钮
	//！数据运算
	public enum Counts {
		ADD, MINUS, MULTIPLY, DIVIDE;
		public String Values(String num1, String num2) {
			BigDecimal number1 = new BigDecimal(num1);
			BigDecimal number2 = new BigDecimal(num2);
			BigDecimal number = BigDecimal.valueOf(0);
			switch (this) {
			case ADD:
				number = number1.add(number2);
				break;
			case MINUS:
				number = number1.subtract(number2);
				break;
			case MULTIPLY:
				number = number1.multiply(number2);
				break;
			case DIVIDE:
				number = number1.divide(number2,20,BigDecimal.ROUND_UP);
				break;

			}
			return number.stripTrailingZeros().toString();

		}
	}
	//数据运算
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		input_formula = (TextView)findViewById(R.id.formula);
		//！数字显示
		class showNum implements OnClickListener
		{
			@Override
			public void onClick(View v) 
			{
				if(num.equals("0"))
				{
					num = "";
					num = v.getId() == R.id.btn_point ? "0" : "";
				}
				String btn_num = ((Button)v).getText().toString();
				Boolean b = Pattern.matches("-*(\\d+).?(\\d)*", num + btn_num);
				num = b ?(num+btn_num) : num;
				input_num.setText(num);
			}
		}
		//数字显示
		//！算式显示与运算结果
		class showFormula implements OnClickListener
		{
			@Override
			public void onClick(View v)
			{
				//！显示
				String screen = "";
				String btn_symbol = ((Button)v).getText().toString();
				Boolean b = Pattern.matches("-*(\\d+).{1}(\\d)*[1-9]+",num);
				Pattern p = Pattern.compile("-*(\\d+)");
				Matcher m = p.matcher(num);
				if(m.find())
				{
					screen = m.group(0);
				}
				operator = b ?(operator+num+btn_symbol) : (operator+screen+btn_symbol);
				input_formula.setText(operator);
				//显示
				
				if(b)
				{
					tempNum = num;
				}
				else
				{
					tempNum = screen;
				}
				//！运算结果
				switch (v.getId()) {  
	            case R.id.btn_add: 
	                countNum = Counts.ADD.Values(countNum, tempNum);
	                break;  
	            case R.id.btn_minus:  
	            	countNum = Counts.MINUS.Values(countNum, tempNum);
	                break;  
	            case R.id.btn_product:  
	            	countNum = Counts.MULTIPLY.Values(countNum, tempNum); 
	                break;  
	            case R.id.btn_divisor:  
	            	countNum = Counts.DIVIDE.Values(countNum, tempNum);
	                break;  
	            }
				//运算结果
				if(v.getId() == R.id.btn_equal)
				{
					input_num.setText(countNum);
				}
				else
				{
					input_num.setText("0");
				}
			}
		}
		//算式显示与运算
		//！初始化输入框
		input_num = (EditText) findViewById(R.id.input_num);
		input_num.setText(num);
		input_num.setEnabled(false);
		//初始化输入框
		//！数字监听绑定
		showNum sn = new showNum();
		for(int i=0;i<num_btnId.length;i++)
		{
			num_btn[i] = (Button)findViewById(num_btnId[i]);
			num_btn[i].setOnClickListener(sn);
		}
		//数字监听绑定
		//！运算符监听绑定
		showFormula sf = new showFormula();
		for(int i=0;i<operator_btnId.length;i++)
		{
			operator_btn[i] = (Button)findViewById(operator_btnId[i]);
			operator_btn[i].setOnClickListener(sf);
		}
		//！运算符监听绑定
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
