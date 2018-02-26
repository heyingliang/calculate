package com.example.calculate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static String num = "0";
	private static String tempNum = "0";
	private static String countNum = "0";
	private static boolean flag = false;
	private static int tempId= 0;
	private String operator = "";
	private EditText input_num;
	private TextView input_formula;
	private Button btn_clear,btn_del;
	private RadioButton btn_main,btn_more;
	//����ȡ���ְ�ťID
	private int num_btnId[] = {R.id.btn_zero,R.id.btn_one,R.id.btn_two,R.id.btn_three,R.id.btn_four,
			R.id.btn_five,R.id.btn_six,R.id.btn_seven,R.id.btn_eight,R.id.btn_night,R.id.btn_point};
	private Button num_btn[] = new Button[num_btnId.length];
	//��ȡ���ְ�ťID
	//����ȡ�������ť
	private int operator_btnId[] = {R.id.btn_divisor,R.id.btn_product,R.id.btn_minus,R.id.btn_add,R.id.btn_equal};
	private Button operator_btn[] = new Button[operator_btnId.length];
	//��ȡ�������ť
	//����������
	public enum Counts {
		ADD, MINUS, MULTIPLY, DIVIDE;
		public String Values(String num1, String num2) {
			float number1 = Float.parseFloat(num1);
			float number2 = Float.parseFloat(num2);
			float number = 0;
			String count;
			switch (this) {
			case ADD:
				number = number1 + number2;
				break;
			case MINUS:
				number = number1 - number2;
				break;
			case MULTIPLY:
				 number = number1*number2;
				 break;
			case DIVIDE:
				number = number1/number2;
				break;
			}
			count = ""+number;
			return count;
		}
	}
	//��������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.activity_main);
		input_formula = (TextView)findViewById(R.id.formula);
		//�������ɾ��
		class remove implements OnClickListener
		{
			@Override
			public void onClick(View v) {
				switch(v.getId())
				{
				case R.id.btn_ac:
					num = "0";
					countNum=tempNum=num;
					tempId= 0;
					flag = false;
					operator = "";
					input_num.setText(num);
					input_formula.setText(operator);
					btn_clear.setText("AC");
					break;
				case R.id.btn_del:
					if(flag) {break;}
					else
					{
						if(num.length()>1)
						{
							num = num.substring(0, num.length()-1);
						}
						else
						{
							num = "0";
						}
						input_num.setText(num);
					}
					break;
				}
			}
		}
		//�����ɾ��
		//��������ʾ
		class showNum implements OnClickListener
		{
			@Override
			public void onClick(View v) 
			{
				if(flag)
				{
					num = "0";
					operator = "";
					input_formula.setText(operator);
				}
				if(num.equals("0"))
				{
					num = v.getId() == R.id.btn_point ? "0" : "";
				}
				String btn_num = ((Button)v).getText().toString();
				Boolean b = Pattern.matches("-*(\\d+).?(\\d)*", num + btn_num);
				num = b ?(num+btn_num) : num;
				input_num.setText(num);
				btn_clear.setText("C");
				flag = false;
			}
		}
		//������ʾ
		//����ʽ��ʾ��������
		class showFormula implements OnClickListener
		{
			@Override
			public void onClick(View v)
			{
				//����ʾ
				if(flag)
				{
					operator = "";
					input_formula.setText(operator);
				}
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
				flag = false;
				//��ʾ
				if(b)
				{
					tempNum = num;
				}
				else
				{
					tempNum = screen;
				}
				//��������
				if(tempId == 0)
				{
					countNum = tempNum;
				}
				else
				{
					switch (tempId) { 
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
				}
				//������
				//�������ʾ
				if(v.getId() == R.id.btn_equal)
				{
					num = countNum;
					Boolean ce = Pattern.matches("-*(\\d+).{1}0{1}", num);
					if(ce)
					{
						num = num.substring(0, num.length()-2);
					}
					input_num.setText(num);
					tempId = 0;
					flag = true;
				}
				else
				{
					num="0";
					input_num.setText(num);
					tempId = v.getId();
				}
				//�����ʾ
				btn_clear.setText("C");
			}
		}
		//��ʽ��ʾ������
		//��ҳ����л�
		btn_main = (RadioButton)findViewById(R.id.count);
		btn_more = (RadioButton)findViewById(R.id.more);
		//ҳ����л�
		//����ʼ�������
		input_num = (EditText) findViewById(R.id.input_num);
		input_num.setText(num);
		input_num.setEnabled(false);
		//��ʼ�������
		//�������ɾ������
		remove rm = new remove();
		btn_clear = (Button)findViewById(R.id.btn_ac);
		btn_del = (Button)findViewById(R.id.btn_del);
		btn_clear.setOnClickListener(rm);
		btn_del.setOnClickListener(rm);
		//�����ɾ������
		//�����ּ�����
		showNum sn = new showNum();
		for(int i=0;i<num_btnId.length;i++)
		{
			num_btn[i] = (Button)findViewById(num_btnId[i]);
			num_btn[i].setOnClickListener(sn);
		}
		//���ּ�����
		//�������������
		showFormula sf = new showFormula();
		for(int i=0;i<operator_btnId.length;i++)
		{
			operator_btn[i] = (Button)findViewById(operator_btnId[i]);
			operator_btn[i].setOnClickListener(sf);
		}
		//�������������
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
