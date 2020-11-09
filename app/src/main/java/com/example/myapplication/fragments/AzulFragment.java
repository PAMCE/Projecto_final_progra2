package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AzulFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AzulFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Activity actividad2;
    View vista2;
    EditText Cantidad, Descripcion;
    Spinner Operacion, Motivo;
    DatePicker Fecha;
    Button Agregar;
    RequestQueue requestQueue;
    String Respuesta_Operativo="";
    String Respuesta_Motivo="";
    String Fecha_Respuesta;
    public AzulFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AzulFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AzulFragment newInstance(String param1, String param2) {
        AzulFragment fragment = new AzulFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista2 = inflater.inflate(R.layout.fragment_azul, container, false);
        Cantidad = vista2.findViewById(R.id.txt_Cantidad_Gasto);
        Descripcion = vista2.findViewById(R.id.descripcion_gasto);
        Operacion = vista2.findViewById(R.id.spinner_1_gasto);
        Motivo = vista2.findViewById(R.id.spinner_Ayuda_gasto);
        Fecha = vista2.findViewById(R.id.fecha_gasto);
        Agregar=vista2.findViewById(R.id.buttongastos);
        Operacion.setOnItemSelectedListener(this);

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://192.168.1.189:80/developeru/insertar_ingreso.php");
                Respuesta_Motivo = Motivo.getItemAtPosition(Motivo.getSelectedItemPosition()).toString();
                Fecha_Respuesta = (String.valueOf(Fecha.getYear()+"-"+String.valueOf(Fecha.getMonth()+1+"-"+String.valueOf(Fecha.getDayOfMonth()))));
            }
        });
        return vista2;
    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(actividad2.getApplicationContext(), "Operacion Exitosa", Toast.LENGTH_LONG).show();
                Cantidad.setText("");
                Descripcion.setText("");
                Operacion.setSelection(0);
                Motivo.setSelection(0);
                Cantidad.requestFocus();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actividad2.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("Cantidad", "0");
                parametros.put("Operacion", Respuesta_Operativo);
                parametros.put("Categoria", Respuesta_Motivo);
                parametros.put("Descripcion",Descripcion.getText().toString());
                parametros.put("Fecha", Fecha_Respuesta);
                parametros.put("Tipo", "Salida");
                parametros.put("Cantidad_Salida",Cantidad.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(actividad2);
        requestQueue.add(stringRequest);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad2= (Activity) context;

        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> Operacion, View view, int position, long id) {
        String Choice = Operacion.getItemAtPosition(position).toString();
        Respuesta_Operativo= Choice;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
