package general.me.edu.dgtmovil.dgtmovil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.objetos.Formulario;

/**
 * Created by ADMIN on 17/06/2016.
 */

public class AdaptadorFormulario extends ArrayAdapter<ItemFormulario> {
    Context context;

    public AdaptadorFormulario(Context context, int textViewResourceId,
                        ArrayList<ItemFormulario> objects) {

        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;

        TextView tV;

        if(v==null){
            v = View.inflate(context, R.layout.item_formulario, null);
        }

        ItemFormulario item = getItem(position);
        tV = (TextView)v.findViewById(R.id.titulo);
        tV.setText(item.getTitulo());


        return v;
    }

}
