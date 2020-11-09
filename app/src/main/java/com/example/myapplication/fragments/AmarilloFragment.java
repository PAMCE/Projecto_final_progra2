package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.Date;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AmarilloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmarilloFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    View vista3;
    Activity actividad3;
    EditText Efectivo2,Deposito2,Fecha,NumeroMes,NumeroAño,Gastos;


    public AmarilloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AmarilloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AmarilloFragment newInstance(String param1, String param2) {
        AmarilloFragment fragment = new AmarilloFragment();
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
        vista3=inflater.inflate(R.layout.fragment_amarillo, container, false);
        Date todayDate = new Date();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        String thisDate = currentDate.format(todayDate);

        String[] parts = thisDate.split("-");
        String Parte1 = parts[0]; // 004
        String Parte2 = parts[1]; // 034556

        Efectivo2 = vista3.findViewById(R.id.txt_Efectivo);
        Deposito2 = vista3.findViewById(R.id.txt_Bancario);
        Gastos = vista3.findViewById(R.id.txt_Gasto);
        Gastos_Mes("http://192.168.1.189:80/developeru/gastos_mes.php?Año="+Parte1+"&"+"Mes="+Parte2+"");
        Consultar_Efectivo("http://192.168.1.189:80/developeru/consultar_efectivo.php");
        Consultar_Deposito("http://192.168.1.189:80/developeru/consultar_deposito.php");

        return vista3;
    }



        private void Consultar_Efectivo(String URL) {


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i=0; i< response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            String Saldo = (jsonObject.getString("Saldo"));
                            float saldo = Float.parseFloat(Saldo);
                            if(Saldo.equals("")){
                                Efectivo2.setText("0");
                            }else {
                                Efectivo2.setText("" + saldo);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(actividad3,"Error JSON OBJECT", Toast.LENGTH_LONG).show();
                            Efectivo2.setText("0");

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(actividad3.getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    Efectivo2.setText("Sin Registro");

                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(actividad3);
            requestQueue.add(jsonArrayRequest);
        }
    private void Consultar_Deposito(String URL) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i=0; i< response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String Saldo = (jsonObject.getString("Saldo"));
                        float saldo = Float.parseFloat(Saldo);
                        if(Saldo.equals("")){
                            Deposito2.setText("0");
                        }else {
                            Deposito2.setText("" + saldo);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(actividad3,"Error JSON OBJECT", Toast.LENGTH_LONG).show();
                        Deposito2.setText("0");

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actividad3.getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                Deposito2.setText("Sin Registro");

            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(actividad3);
        requestQueue.add(jsonArrayRequest);
    }
    private void Gastos_Mes(String URL) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i=0; i< response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String Saldo = (jsonObject.getString("Total"));
                        float saldo = Float.parseFloat(Saldo);
                        if(Saldo.equals("")){
                            Gastos.setText("0");
                        }else {
                            Gastos.setText("" + saldo);
                        }

                    } catch (JSONException e) {
                        Toast.makeText(actividad3,"Error JSON OBJECT", Toast.LENGTH_LONG).show();
                        Gastos.setText("0");

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actividad3.getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                Gastos.setText("Sin Registro");

            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(actividad3);
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
            this.actividad3= (Activity) context;

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
