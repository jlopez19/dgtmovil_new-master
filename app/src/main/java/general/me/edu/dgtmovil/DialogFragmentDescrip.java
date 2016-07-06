package general.me.edu.dgtmovil;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
public class DialogFragmentDescrip extends DialogFragment implements View.OnClickListener {

    Button btn_addDescripcion;
    EditText txt_descripcion;
    String descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dialog_fragment, container, false);
        txt_descripcion = (EditText) view.findViewById(R.id.txt_descripcion);
        btn_addDescripcion = (Button) view.findViewById(R.id.btn_guardarDesc);
        btn_addDescripcion.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        descripcion = txt_descripcion.getText().toString();
        if (id == R.id.btn_guardarDesc) {
            dismiss();
}
}
}
