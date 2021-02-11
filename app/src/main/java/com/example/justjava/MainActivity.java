/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity=2;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox= (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=  whippedCreamCheckBox.isChecked();


        CheckBox chocolateCheckBox= (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=  chocolateCheckBox.isChecked();

        EditText name=(findViewById(R.id.nameText));
        String nameText=name.getText().toString();

       int price= calculatePrice(hasWhippedCream,hasChocolate);
       String priceMessage=createOrderSummary(nameText,price,hasWhippedCream,hasChocolate);



        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("mailto:")); // only email apps should handle this

        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for"+nameText);
        sendIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }



    }
    public void increment(View view) {

        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {

        quantity=quantity-1;
        display(quantity);

    }




    private int calculatePrice(boolean whipCream,boolean chocolatte){

        int basePrice=5;
        if (whipCream){
         basePrice=basePrice+1;
        }
        if (chocolatte){
            basePrice=basePrice+2;
        }
        return quantity*basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }



    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate){



        String priceMessage =getString(R.string.order_summary_name,name)+
                "\n Add whipped Cream? "
                +addWhippedCream+
                "\n Add  Chocolate? "
                +addChocolate+
                "\n Quantity: "+quantity +
                "\n Total: $"+(price)+"\n"+getString(R.string.thank_you);

return priceMessage;

    }
}