package com.example.android.coffeehouse;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;
    private boolean hasCaramel = false;
    private int totalPrice = 0;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheck =  findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheck.isChecked();

        CheckBox chocolateCheck = findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheck.isChecked();

        CheckBox caramelCheck = findViewById(R.id.caramel_checkbox);
        hasCaramel = caramelCheck.isChecked();

        EditText inputName = findViewById(R.id.name_input_view);
        name = inputName.getText().toString();

        calculatePrice(5);
        String message = createOrderSummary();


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee House order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view)
    {
        if(quantity == 100)
        {
            Context context = getApplicationContext();
            CharSequence text = "Can not order more then 100 coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view)
    {
        if(quantity <= 1)
        {
            Context context = getApplicationContext();
            CharSequence text = "Can not order less";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }
        quantity--;
        displayQuantity(quantity);

    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */

    private String createOrderSummary()
    {
        return "Name: " + name +
                " \nAdd whipped cream? " + ((hasWhippedCream) ? "Yes" : "No " )+
                "\nAdd chocolate? " + ((hasChocolate) ? "Yes" : "No " ) +
                "\nAdd caramel? " + ((hasCaramel) ? "Yes" : "No " ) +
                "\nQuantity:  " + quantity +
                "\nTotal: $" + totalPrice +
                " \nThank You!";
    }

    /**
     * Calculates the total price of the order based on the current quantity
     * and any extras.
     */
    private void calculatePrice(int priceOfOneCup)
    {

        priceOfOneCup += (hasWhippedCream) ? 2 : 0;
        priceOfOneCup += (hasChocolate) ? 1 : 0;
        priceOfOneCup += (hasCaramel) ? 1 : 0;

        totalPrice = quantity * priceOfOneCup;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
