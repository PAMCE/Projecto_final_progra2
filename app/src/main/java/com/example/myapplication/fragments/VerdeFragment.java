package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerdeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerdeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listViewPersonas;
    EditText EdtNombre, EdtDiscripcion;
    Button BtnBuscar;
    RequestQueue requestQueue;
    ArrayList<String> listainformacion = new ArrayList<String>();
    ArrayAdapter adaptadores;
    View vista4,vista5;
    Activity actividad4;
    String Respuesta_Mes;
    String Respuesta_Operacion;
    Spinner operacion_movimientos,mes_movimientos;

    public VerdeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerdeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerdeFragment newInstance(String param1, String param2) {
        VerdeFragment fragment = new VerdeFragment();
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
        vista4 = inflater.inflate(R.layout.fragment_verde, container, false);
        vista5 = inflater.inflate(R.layout.elemento,container,false);
        listViewPersonas = vista4.findViewById(R.id.listViewPersonas);
        BtnBuscar = vista4.findViewById(R.id.btn_Movimiento);
        mes_movimientos=vista4.findViewById(R.id.spinner_Mes);
        operacion_movimientos=vista4.findViewById(R.id.spinner_Movimiento);
        listainformacion.clear();
        Date todayDate = new Date();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        String thisDate = currentDate.format(todayDate);

        String[] parts = thisDate.split("-");
        String Parte1 = parts[0]; // 004
        String Parte2 = parts[1]; // 034556
        Respuesta_Mes= mes_movimientos.getItemAtPosition(mes_movimientos.getSelectedItemPosition()).toString();
        Respuesta_Operacion=operacion_movimientos.getItemAtPosition(operacion_movimientos.getSelectedItemPosition()).toString();
        BtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Respuesta_Operacion=operacion_movimientos.getItemAtPosition(operacion_movimientos.getSelectedItemPosition()).toString();
                Respuesta_Mes= mes_movimientos.getItemAtPosition(mes_movimientos.getSelectedItemPosition()).toString();
                if(Respuesta_Operacion.equals("Todo")){
                    buscarProducto("http://192.168.1.189:80/developeru/todo_movimientos.php?Mes=" + Respuesta_Mes + "&Año=" + Parte1 + "");
                }else {
                    buscarProducto("http://192.168.1.189:80/developeru/movimientos_categoria.php?Año=" + Parte1 + "&Mes=" + Respuesta_Mes + "&validar=" + Respuesta_Operacion + "");
                }
            }
        });
        return vista4;
    }


    private void buscarProducto(String URL) {


        listainformacion.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String Cantidad = (jsonObject.getString("Cantidad"));
                        String Operacion = (jsonObject.getString("Operacion"));
                        String Categoria = (jsonObject.getString("Categoria"));
                        String Descripcion = (jsonObject.getString("Descripcion"));
                        String Tipo = (jsonObject.getString("Tipo"));
                        String Cantidad_Salidad = (jsonObject.getString("Cantidad_Salidad"));

                        listainformacion.add("Cantidad: " + Cantidad + " Operacion: " + Operacion+" "  + " Descripcion: "  + Descripcion + " Tipo: "+ Tipo +" Cantidad_Salida: " +Cantidad_Salidad);
                    } catch (JSONException e) {
                        Toast.makeText(actividad4.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                adaptadores = new ArrayAdapter (getActivity(), R.layout.elemento,listainformacion);
                listViewPersonas.setAdapter(adaptadores);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actividad4.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        adaptadores = new ArrayAdapter (getActivity(), R.layout.elemento,listainformacion);
        listViewPersonas.setAdapter(adaptadores);
        requestQueue = Volley.newRequestQueue(actividad4);
        requestQueue.add(jsonArrayRequest);

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
            this.actividad4= (Activity) context;

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
