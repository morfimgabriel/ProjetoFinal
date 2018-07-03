package gabriel.br.com.projetofinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import gabriel.br.com.projetofinal.DAO.MyORMLiteHelper;

import gabriel.br.com.projetofinal.Entity.AdapterAutocomplete;
import gabriel.br.com.projetofinal.Entity.Shopping;
import gabriel.br.com.projetofinal.model.AdapterlistFavoritos;

public class MainActivity extends Activity {

    MyORMLiteHelper banco;
    ArrayList<Shopping> listaShopping;
    AdapterAutocomplete adapterShoppings;
    AutoCompleteTextView textView;
    ArrayList<Shopping> shoppingFavoritos;
    ListView listShoppingsFavoritos;
    AdapterlistFavoritos adapterShoppingFavoritos;
    Shopping s ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banco = MyORMLiteHelper.getInstance(this);
        shoppingFavoritos = new ArrayList<>();
        Shopping s = null;

        try {
            shoppingFavoritos = (ArrayList<Shopping>) banco.getShoppingDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        adapterShoppingFavoritos = new AdapterlistFavoritos(this, shoppingFavoritos);
        listShoppingsFavoritos.setAdapter(adapterShoppingFavoritos);

        listShoppingsFavoritos.setOnLongClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s = adapter
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setTitle("Visualizando Shopping");
                alerta.setMessage(sh.toString());
                alerta.setNeutralButton("fechar", null);
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editNomeProd.setText(produto.getNome());
                        editValor.setText(String.valueOf(produto.getValor()));

                        for (int pos = 0; pos < adapterCategoria.getCount(); pos++) {
                            Categoria c = adapterCategoria.getItem(pos);
                            if (c.getId() == produto.getCategoria().getId()) {
                                spCategoria.setSelection(pos);
                                break;
                            }
                        }

                    }

                });
                alerta.show();

            }
        });
            }
        });


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




