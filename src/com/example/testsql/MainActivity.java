package com.example.testsql;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
	
	private Button selectButton;
	private Button insertButton;
	private TextView textView;
	private Handler handler;
	private String SelectResult[] =new String[4];
	private boolean InsertResult = false;
	private String ipString;
	private String keyString;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 		
		//ip”ÎMSSQL sa”√ªßµ«¬Ω√‹¬Î
		ipString = "localhost";
		keyString = "*******";
		textView = (TextView) findViewById(R.id.text_View);
		selectButton = (Button) findViewById(R.id.selectbt);
		selectButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				testSelect();		        
			}
		});
		insertButton = (Button) findViewById(R.id.insertbt);
		insertButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				testInsert();
			}
		});
		handler = new Handler();
	}	
	private void testSelect() {
		Runnable run = new Runnable() {
			public void run() {
				SQLConnect dbTest = new SQLConnect(ipString, keyString);
				dbTest.CreateLink();
				SelectResult = dbTest.select();
				dbTest.close();
				handler.post(new Runnable(){
					public void run() {
						textView.setText("id:"+SelectResult[0]+
						"\nname:"+SelectResult[1]+"\npassword:"+
						SelectResult[2]+"\nmail:"+SelectResult[3]);
					}					
				});
			}
		};
		new Thread(run).start();
	}
	private void testInsert() {
		Runnable run = new Runnable() {
			public void run() {
				SQLConnect dbTest = new SQLConnect(ipString, keyString);
				dbTest.CreateLink();
				InsertResult = dbTest.ins_upd_del();
				dbTest.close();
				handler.post(new Runnable(){
					public void run() {
						if(InsertResult){
							Toast.makeText(MainActivity.this, "Insert Successful",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MainActivity.this, "Insert Failed£°",
									Toast.LENGTH_SHORT).show();
						}						
					}					
				});
			}
		};
		new Thread(run).start();
	}
}
