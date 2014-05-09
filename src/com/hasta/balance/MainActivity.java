package com.hasta.balance;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private float spesa, s, rimanente, new_balance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button add=(Button)findViewById(R.id.add);
		Button nuovo_b=(Button)findViewById(R.id.nuovo_bilancio);
 	    final TextView tv=(TextView)findViewById(R.id.tv);
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		tv.setText("BILANCIO CORRENTE: "+String.valueOf(prefs.getFloat("balance_value", 0)+" €\n"+"SOLDI SPESI: "+String.valueOf(prefs.getFloat("somma_totale_spese", 0))));
		
		if(prefs.getBoolean("first_time", true)) {
			final LayoutInflater inflater = this.getLayoutInflater();
		    
		    final View v=inflater.inflate(R.layout.dialog, null);
			 new AlertDialog.Builder 
					(this)
		           .setView(v)
		           .setTitle("Imposta il tuo bilancio iniziale")
		           .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
		               @Override
		               
		               public void onClick(DialogInterface dialog, int id) { 
		            	    EditText edit=(EditText)v.findViewById(R.id.balance_value);
		                   SharedPreferences.Editor editor2 = prefs.edit();
		                   editor2.putFloat("balance_value", Float.parseFloat(edit.getText().toString()));
		                   editor2.commit();
		                   tv.setText("BILANCIO CORRENTE: "+String.valueOf(prefs.getFloat("balance_value", 0)+" €\n"+"SOLDI SPESI: "+String.valueOf(prefs.getFloat("somma_totale_spese", 0))));
		                   dialog.dismiss();
		                   SharedPreferences.Editor editor = prefs.edit();
		       			editor.putBoolean("first_time", false);
		       			editor.commit();
		       			
		               }
		               
		           }).show();
			
		}
		s=prefs.getFloat("somma_totale_spese", 0);
		add.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	 LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    
    		    final View v2=inflater.inflate(R.layout.dialog, null);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle("Inserisci un importo.")
	           .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	               @Override
	               
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   EditText edit=(EditText)v2.findViewById(R.id.balance_value);

	                   SharedPreferences.Editor editor = prefs.edit();
	                   spesa=Float.parseFloat(edit.getText().toString());
	                   s=s+spesa;
	                   editor.putFloat("somma_totale_spese", s);
	                   rimanente=prefs.getFloat("balance_value", 0)-spesa;
	                   editor.putFloat("balance_value", rimanente);
	                   editor.commit();
	                   
	                   tv.setText("BILANCIO CORRENTE: "+String.valueOf(prefs.getFloat("balance_value", 0)+" €\n"+"SOLDI SPESI: "+String.valueOf(prefs.getFloat("somma_totale_spese", 0))));
	                   
	                   dialog.dismiss();
	                   if(prefs.getFloat("balance_value", 0)==0){
	        	     	   Utils.showToast(MainActivity.this, "Sei al verde.");
	        	        }
	               }
	               
	           }).show();
                
            }
        });
		
		nuovo_b.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	 LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    
    		    final View v2=inflater.inflate(R.layout.dialog, null);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle("Imposta nuovo bilancio")
	           .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	               @Override
	               
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   EditText edit=(EditText)v2.findViewById(R.id.balance_value);
	            	   new_balance=Float.parseFloat(edit.getText().toString());
	            	   s=0;
	                   SharedPreferences.Editor editor = prefs.edit();
	                   
	                   editor.putFloat("balance_value", new_balance);
	                   editor.putFloat("somma_totale_spese", 0);
	                   editor.commit();
	                   tv.setText("BILANCIO CORRENTE: "+String.valueOf(prefs.getFloat("balance_value", 0)+" €\n"+"SOLDI SPESI: "+String.valueOf(prefs.getFloat("somma_totale_spese", 0))));
	                   dialog.dismiss();
	                   if(prefs.getFloat("balance_value", 0)==0){
	        	     	   Utils.showToast(MainActivity.this, "Sei al verde.");
	        	        }
	               }
	               
	           }).show();
                
            }
        });
		
		
			}
	
	


}
