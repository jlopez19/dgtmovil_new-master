package general.me.edu.dgtmovil.adapterspinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.objetos.Asistentes;
import general.me.edu.dgtmovil.objetos.ViewHolder;

public class SpinerAdpter extends BaseAdapter {

    private Context context;

    List<Asistentes> asistentes;
    SimpleDateFormat format;


    public SpinerAdpter(Context context, List<Asistentes> comentario) {
        this.context= context;
        this.asistentes=comentario;
    }

    public SpinerAdpter() {
    }

    @Override
    public int getCount() {
        return asistentes.size();
    }

    @Override
    public Object getItem(int position) {
        return asistentes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent){
        View item = convertView;
        ViewHolder holder;
        if(item == null){
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            item= inflater.inflate(R.layout.activity_spiner_adpter,  null);
            holder = new ViewHolder();
            holder.v = item;
            holder.nombres = (TextView) item.findViewById(R.id.tvNombres);
            holder.cargo= (TextView) item.findViewById(R.id.tvCargo);
            holder.firma = (TextView) item.findViewById(R.id.tvFirma);

            item.setTag(holder);

        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.nombres.setText(asistentes.get(posicion).getNombres());
        holder.cargo.setText(asistentes.get(posicion).getCargo());
        holder.firma.setText(asistentes.get(posicion).getFirma());


        return item;

    }
}
