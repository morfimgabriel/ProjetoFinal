package gabriel.br.com.projetofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import java.sql.SQLException;
import java.util.ArrayList;

import gabriel.br.com.projetofinal.DAO.MyORMLiteHelper;
import gabriel.br.com.projetofinal.Entity.Shopping;

public class MainActivity extends AppCompatActivity {

    MyORMLiteHelper banco;
    ArrayList<Shopping> listaShopping;
    ArrayAdapter<Shopping> adapterShoppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            listaShopping = (ArrayList<Shopping>) banco.getShoppingDAO().queryForAll();
            if (listaShopping.size() == 0) {
                listaShopping = (ArrayList<Shopping>) banco.getShoppingDAO().queryForAll();
                popularShopping();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        banco = MyORMLiteHelper.getInstance(this);

        adapterShoppings = new ArrayAdapter<Shopping>(this,
                android.R.layout.simple_dropdown_item_1line, listaShopping);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoComplete);
        textView.setAdapter(adapterShoppings);
    }


    public void popularShopping() throws SQLException {
        Shopping shop1 = new Shopping("Shopping itaguaçu");
        banco.getShoppingDAO().create(shop1);
    }
}




