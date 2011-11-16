package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.LinearLayout;


public class NutritionMenuActivity extends Activity implements OnClickListener {

//    private TextView messageTextView;    
    private Button menuButton;
    private Button searchDBButton;
    private Button scanButton;
    private Button recipeButton;
    private Button shoppingButton;
    private Button locatorButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_menu);

        
        // wire the UI to the code	
		searchDBButton = (Button) findViewById(R.id.searchDB_button);
		searchDBButton.setOnClickListener(this);
		
		scanButton = (Button) findViewById(R.id.scan_button);
		scanButton.setOnClickListener(this);

		recipeButton = (Button) findViewById(R.id.recipe_button);
		recipeButton.setOnClickListener(this);
		
		shoppingButton = (Button) findViewById(R.id.shopping_button);
		shoppingButton.setOnClickListener(this);
		
	    locatorButton = (Button) findViewById(R.id.locator_button);
	    locatorButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button);
	    menuButton.setOnClickListener(this);
		
    }

    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (searchDBButton.getId() == ((Button) v).getId()){
		Intent toDBSearch = new Intent(this,SearchNutritionDBActivity.class);
		this.startActivity(toDBSearch);
		}
		else if (scanButton.getId() == ((Button) v).getId()){
			Intent toScan = new Intent(this,ScanActivity.class); 
			this.startActivity(toScan);
		}
		else if (recipeButton.getId() == ((Button) v).getId()){
			Intent toRecipe = new Intent(this,RecipeActivity.class); 
			this.startActivity(toRecipe);		
		}	
		else if (shoppingButton.getId() == ((Button) v).getId()){
			Intent toShopping = new Intent(this,ShoppingActivity.class); 
			this.startActivity(toShopping);
		}
		else if (locatorButton.getId() == ((Button) v).getId()){
			Intent toLocator = new Intent(this,LocatorActivity.class); 
			this.startActivity(toLocator);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this,MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}


