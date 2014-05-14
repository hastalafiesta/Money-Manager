package com.hasta.balance;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	static float spesa, s, rimanente, nuovo_bilancio, vestiti, cibo, altro;
	Spinner spn;
    TextView tv;
    EditText edit, edit2;
    Button importo, n_bilancio, incrementa_bilancio, sottrai_spesa, sottrai_bilancio;
    SharedPreferences prefs;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    importo=(Button)findViewById(R.id.importo);
		n_bilancio=(Button)findViewById(R.id.nuovo_bilancio);
		incrementa_bilancio=(Button)findViewById(R.id.incrementa_bilancio);
		sottrai_bilancio=(Button)findViewById(R.id.sottrai_bilancio);
		sottrai_spesa=(Button)findViewById(R.id.sottrai_spesa);
 	    tv=(TextView)findViewById(R.id.tv);
 	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		
		if(prefs.getBoolean("first_time", true)) {
			final LayoutInflater inflater = this.getLayoutInflater();
		    final View v=inflater.inflate(R.layout.dialog_nuovo_bilancio, null);
			 new AlertDialog.Builder 
					(this)
		           .setView(v)
		           .setTitle(R.string.bilancio_iniziale)
		           .setNeutralButton(R.string.Ok, new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialog, int id) { 
		            	EditText edit=(EditText)v.findViewById(R.id.valore_bilancio);
		                SharedPreferences.Editor editor = prefs.edit();
		                editor.putFloat("bilancio", Float.parseFloat(edit.getText().toString()));
		                editor.putFloat("rimanente", Float.parseFloat(edit.getText().toString()));
		       			editor.putBoolean("first_time", false);
		       			editor.commit();
		       			dialog.dismiss();
		       			updateTextView();
		               }
		           }).show();
		}

		updateTextView();
		importo.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    final View v2=inflater.inflate(R.layout.dialog_spesa, null);
    		    spn = (Spinner)v2.findViewById(R.id.spinner);
         	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.tipo_spesa, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle(R.string.nuova_spesa)
	           .setNeutralButton(R.string.Ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   edit=(EditText)v2.findViewById(R.id.spesa);
	                   SharedPreferences.Editor editor = prefs.edit();
	                   spesa=Float.parseFloat(edit.getText().toString());
	                   s=prefs.getFloat("somma_totale_spese", 0)+spesa;
	                   editor.putFloat("somma_totale_spese", s);
	                   rimanente=prefs.getFloat("rimanente", 0)-spesa;
	                   editor.putFloat("rimanente", rimanente);
	                   if(spn.getSelectedItemId()==0){
	                	   cibo=prefs.getFloat("cibo", 0)+spesa;
	                	   editor.putFloat("cibo", cibo);
	                   }else if(spn.getSelectedItemId()==1){
	                	   altro=prefs.getFloat("altro", 0)+spesa;
	                	   editor.putFloat("altro", altro);
	                   }else if(spn.getSelectedItemId()==2){
	                	   vestiti=prefs.getFloat("vestiti", 0)+spesa;
	                	   editor.putFloat("vestiti", vestiti);
	                   }
	                   editor.commit();
	                   updateTextView();	                   
	                   dialog.dismiss();
	                   if(prefs.getFloat("rimanente", 0)==0){
	        	     	   Utils.showToast(MainActivity.this, "Sei al verde.");
	        	        }else if(prefs.getFloat("rimanente",0)<0){
	        	        	Utils.showToast(MainActivity.this, "Debiti...");
	        	        }
	               }
	           }).show();
            }
        });
		
		n_bilancio.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	 LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    final View v2=inflater.inflate(R.layout.dialog_nuovo_bilancio, null);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle(R.string.nuovo_bilancio)
	           .setNeutralButton(R.string.fatto, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {        	   
	            	   edit2=(EditText)v2.findViewById(R.id.valore_bilancio);
	            	   s=0;
	            	   cibo=0;
	            	   vestiti=0;
	            	   altro=0;	   
	            	   nuovo_bilancio=Float.parseFloat(edit.getText().toString());
	                   SharedPreferences.Editor editor = prefs.edit();                
	                   editor.putFloat("bilancio", nuovo_bilancio);	                   
	                   editor.putFloat("somma_totale_spese", 0);
	                   editor.putFloat("cibo", 0);
	                   editor.putFloat("vestiti", 0);
	                   editor.putFloat("altro", 0);	                   	                   
	                   rimanente=Float.parseFloat(edit.getText().toString());
	                   editor.putFloat("rimanente", rimanente);
	                   editor.commit();
	                   updateTextView();
	                   dialog.dismiss();     
	               }
	               
	           }).show();
            }
        });
		
		sottrai_spesa.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	 LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    final View v2=inflater.inflate(R.layout.dialog_spesa, null);
    		    spn = (Spinner)v2.findViewById(R.id.spinner);
         	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.tipo_spesa, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle(R.string.sottrai_spesa)
	           .setNeutralButton(R.string.fatto, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {        	   
	            	   edit=(EditText)v2.findViewById(R.id.spesa);
	            	   s=prefs.getFloat("somma_totale_spese", 0)-Float.parseFloat(edit.getText().toString());
	            	   rimanente=prefs.getFloat("rimanente", 0)+Float.parseFloat(edit.getText().toString());
	            	   SharedPreferences.Editor editor = prefs.edit();  
	            	   if(spn.getSelectedItemId()==0){
	                	   cibo=prefs.getFloat("cibo", 0)-Float.parseFloat(edit.getText().toString());
	                	   editor.putFloat("cibo", cibo);
	                   }else if(spn.getSelectedItemId()==1){
	                	   altro=prefs.getFloat("altro", 0)-Float.parseFloat(edit.getText().toString());
	                	   editor.putFloat("altro", altro);
	                   }else if(spn.getSelectedItemId()==2){
	                	   vestiti=prefs.getFloat("vestiti", 0)-Float.parseFloat(edit.getText().toString());
	                	   editor.putFloat("vestiti", vestiti);
	                   }                  
	                   editor.putFloat("somma_totale_spese", s);	                   
	                   editor.putFloat("rimanente", rimanente);
	                   editor.commit();
	                   updateTextView();
	                   dialog.dismiss();     
	               }
	               
	           }).show();
            }
        });
		
		incrementa_bilancio.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	 LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    final View v2=inflater.inflate(R.layout.dialog_nuovo_bilancio, null);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle(R.string.agg_soldi_bilancio)
	           .setNeutralButton(R.string.fatto, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {        	   
	            	   edit2=(EditText)v2.findViewById(R.id.valore_bilancio);   
	            	   nuovo_bilancio=prefs.getFloat("bilancio", 0)+Float.parseFloat(edit2.getText().toString());
	            	   rimanente=nuovo_bilancio-prefs.getFloat("somma_totale_spese", 0);
	                   SharedPreferences.Editor editor = prefs.edit();                
	                   editor.putFloat("bilancio", nuovo_bilancio);	    
	                   editor.putFloat("rimanente", rimanente);
	                   editor.commit();
	                   updateTextView();
	                   dialog.dismiss();     
	               }
	               
	           }).show();
            }
        });
		
		sottrai_bilancio.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v) {
            	 LayoutInflater inflater = MainActivity.this.getLayoutInflater();
    		    final View v2=inflater.inflate(R.layout.dialog_nuovo_bilancio, null);
            	new AlertDialog.Builder 
				(MainActivity.this)
	           .setView(v2)
	           .setTitle(R.string.sottrai_bilancio)
	           .setNeutralButton(R.string.fatto, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {        	   
	            	   edit2=(EditText)v2.findViewById(R.id.valore_bilancio);   
	            	   nuovo_bilancio=prefs.getFloat("bilancio", 0)-Float.parseFloat(edit2.getText().toString());
	            	   rimanente=nuovo_bilancio-prefs.getFloat("somma_totale_spese", 0);
	                   SharedPreferences.Editor editor = prefs.edit();
	                   editor.putFloat("bilancio", nuovo_bilancio);	    
	                   editor.putFloat("rimanente", rimanente);
	                   editor.commit();
	                   updateTextView();
	                   dialog.dismiss();     
	               }
	               
	           }).show();
            }
        });
	}
	
       public void updateTextView(){   
    	   tv.setText("BILANCIO: "+String.valueOf(prefs.getFloat("bilancio", 0)+" €\n"+"SOLDI SPESI: "+String.valueOf(prefs.getFloat("somma_totale_spese", 0))+" €\n"+"RIMANENTE: "+String.valueOf(prefs.getFloat("rimanente", 0))+" €\n"+"CIBO: "+String.valueOf(prefs.getFloat("cibo", 0))+" €\n"+"VESTITI: "+String.valueOf(prefs.getFloat("vestiti", 0))+" €\n"+"ALTRO: "+String.valueOf((prefs.getFloat("altro", 0))+" €")));
       }
}
