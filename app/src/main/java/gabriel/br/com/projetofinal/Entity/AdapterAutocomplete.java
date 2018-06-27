package gabriel.br.com.projetofinal.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import gabriel.br.com.projetofinal.MainActivity;
import gabriel.br.com.projetofinal.R;

/**
 * Created by Aluno on 27/06/2018.
 */

public class AdapterAutocomplete extends ArrayAdapter<Shopping> {

    ArrayList<Shopping> listaShopping;
    LayoutInflater inflate;
    Shopping s;
    Context ctx;

    public AdapterAutocomplete(Context ctx, ArrayList<Shopping> lista){
        super(ctx, R.layout.layout_autocomplete, lista);
        listaShopping = lista;
        inflate = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }


    @Override
    public int getCount() {
        return listaShopping.size();
    }

    @Override
    public Shopping getItem(int position) {
        return listaShopping.get(position);
    }


    public void remove(Shopping c){
        listaShopping.remove(c);
        notifyDataSetChanged();
    }

    public void add(Shopping c){
        listaShopping.add(c);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       s = listaShopping.get(position);

        convertView = inflate.inflate(R.layout.layout_autocomplete, null);
        TextView tv = convertView.findViewById(R.id.editNomeShopping);
        tv.setText(s.getNome());
        tv.setTag(s.getNome());
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ctx, ""+s.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return convertView;
    }

}
