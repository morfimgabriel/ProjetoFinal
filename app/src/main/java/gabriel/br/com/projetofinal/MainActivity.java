package gabriel.br.com.projetofinal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

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
    ArrayList<Shopping> shoppingFavoritos;
    ListView listShoppingsFavoritos;
    ArrayAdapter<Shopping> adapterShoppingFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banco = MyORMLiteHelper.getInstance(this);
        shoppingFavoritos = new ArrayList<>();

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
        adapterShoppings.setShoppingFavoritos(shoppingFavoritos);
        textView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        textView.setAdapter(adapterShoppings);

        listShoppingsFavoritos = findViewById(R.id.listFavoritos);

        adapterShoppingFavoritos = new ArrayAdapter<Shopping>(this, android.R.layout.simple_list_item_1, shoppingFavoritos);
        listShoppingsFavoritos.setAdapter(adapterShoppingFavoritos);


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




