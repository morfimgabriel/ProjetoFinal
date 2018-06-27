package gabriel.br.com.projetofinal;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import gabriel.br.com.projetofinal.DAO.MyORMLiteHelper;
import gabriel.br.com.projetofinal.Entity.AdapterAutocomplete;
import gabriel.br.com.projetofinal.Entity.Shopping;

public class MainActivity extends Activity {

    MyORMLiteHelper banco;
    ArrayList<Shopping> listaShopping;
    AdapterAutocomplete adapterShoppings;
    AutoCompleteTextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banco = MyORMLiteHelper.getInstance(this);

        try {
            popularShopping();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            listaShopping = (ArrayList<Shopping>) banco.getShoppingDAO().queryForAll();
            if (listaShopping == null) {
                listaShopping = (ArrayList<Shopping>) banco.getShoppingDAO().queryForAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapterShoppings = new AdapterAutocomplete(this, listaShopping);

        textView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        textView.setAdapter(adapterShoppings);


    }



    public void teste(View v){
        textView.setText(String.valueOf(v.getTag()));
        textView.dismissDropDown();
    }

    public void popularShopping() throws SQLException {
        Shopping shop1 = new Shopping("Shopping itaguaçu");
        Shopping shop2 = new Shopping("Shopping Beiramar");
        banco.getShoppingDAO().create(shop1);
        banco.getShoppingDAO().create(shop2);
    }


}




