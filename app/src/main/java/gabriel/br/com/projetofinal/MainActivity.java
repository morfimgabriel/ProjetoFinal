package gabriel.br.com.projetofinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
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
    Shopping s = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banco = MyORMLiteHelper.getInstance(this);
        shoppingFavoritos = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.25.6:8080/mobileapi/v1/api/shopping/list", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    JSONArray json = new JSONArray(new String(response));
                    Type listType =
                            new TypeToken<ArrayList<Shopping>>(){}.getType();
                    listaShopping = (ArrayList<Shopping>) new Gson().fromJson(String.valueOf(json), listType);

                    adapterShoppings = new AdapterAutocomplete(MainActivity.this, listaShopping);
                    adapterShoppings.setShoppingFavoritos(shoppingFavoritos);
                    textView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
                    textView.setAdapter(adapterShoppings);
                    Toast.makeText(MainActivity.this, "Sucesso!!! " + listaShopping.size(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

        });

        // list view dos shoppings favoritos populando
        try {
            shoppingFavoritos = (ArrayList<Shopping>) banco.getShoppingDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        listShoppingsFavoritos = findViewById(R.id.listFavoritos);

        adapterShoppingFavoritos = new AdapterlistFavoritos(this, shoppingFavoritos);
        listShoppingsFavoritos.setAdapter(adapterShoppingFavoritos);

        listShoppingsFavoritos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                s = adapterShoppingFavoritos.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setTitle("Visualizando Shopping");
                alerta.setMessage(s.toString());
                alerta.setNegativeButton("fechar", null);
                alerta.setPositiveButton("remover favorito", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            banco.getShoppingDAO().delete(s);
                            adapterShoppingFavoritos.remove(s);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alerta.show();
                return true;
            }
        });

        listShoppingsFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                s = adapterShoppingFavoritos.getItem(i);
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setTitle("Visualizando Shopping");
                alerta.setMessage(s.toString());
                alerta.setNegativeButton("fechar", null);
                alerta.setPositiveButton("Visitar Shopping", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, NavegacaoActivity.class);
                        startActivity(i);
                    }
            });
                alerta.show();
        }
        });

        }


    public void teste(View v){
        textView.setText(String.valueOf(v.getTag()));
        textView.dismissDropDown();
    }

//    public void popularShopping() throws SQLException {
//        Shopping shop1 = new Shopping("Shopping itaguaçu");
//        Shopping shop2 = new Shopping("Shopping Beiramar");
//        banco.getShoppingDAO().create(shop1);
//        banco.getShoppingDAO().create(shop2);
//    }


    public void buscarShopping(View view) {
        textView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        if (textView.getText().toString().length()== 0) {
            Toast.makeText(this, "Campo vazio selecione um shopping", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(MainActivity.this, NavegacaoActivity.class);
            startActivity(i);
        }
    }
}




