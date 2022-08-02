package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descrip, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_codigo=(EditText) findViewById(R.id.edit_codigo);
        et_descrip=(EditText) findViewById(R.id.edit_descrip);
        et_precio=(EditText) findViewById(R.id.edit_precio);
    }
    //Metodo para dar de alta los productos
    public void Registrar(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        //Inicio de la declaracion de las variables para los datos que ingrese el usuario
        String codigo = et_codigo.getText().toString();
        String descrip= et_descrip.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descrip.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descrip);
            registro.put("precio", precio);

            BasedeDatos.insert("articulos",null, registro);
            BasedeDatos.close();
            et_codigo.setText("");
            et_descrip.setText("");
            et_precio.setText("");
            Toast.makeText(this,"Registro exitoso",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Los campos no pueden quedar vacios",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para consultar un articulo o producto con codigo
    public void ConsultaporCodigo(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        //Inicio de la declaracion de las variables para los datos que ingrese el usuario
        String codigo = et_codigo.getText().toString();
        if (!codigo.isEmpty()){
            Cursor fila = BasedeDatos.rawQuery("select descripcion, precio from articulos where codigo ="+codigo, null);
            if (fila.moveToFirst()){
                et_descrip.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                BasedeDatos.close();
            }else {
                Toast.makeText(this,"El articulo no existe con ese codigo ",Toast.LENGTH_LONG).show();
                BasedeDatos.close();
            }
        }else{
            Toast.makeText(this,"El campo de codigo no puede que dar vacio",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para consultar un articulo o producto con descripcion
    public void ConsultaporDescrip(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        //Inicio de la declaracion de las variables para los datos que ingrese el usuario
        String descrip = et_descrip.getText().toString();
        if (!descrip.isEmpty()){
            Cursor fila = BasedeDatos.rawQuery("select codigo, precio from articulos where descripcion ="+descrip, null);
            if (fila.moveToFirst()){
                et_codigo.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                BasedeDatos.close();
            }else {
                Toast.makeText(this,"El articulo no existe con ese descripcion",Toast.LENGTH_LONG).show();
                BasedeDatos.close();
            }
        }else{
            Toast.makeText(this,"El campo de codigo no puede que dar vacio",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para elimar producto o articulo con codigo
    public void Eliminar(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        //Inicio de la declaracion de las variables para los datos que ingrese el usuario
        String codigo = et_codigo.getText().toString();
        if (!codigo.isEmpty()){

            int cantidad = BasedeDatos.delete("articulos","codigo="+codigo,null);

        }else {
            Toast.makeText(this,"El campo de codigo no puede que dar vacio",Toast.LENGTH_LONG).show();
        }
    }
}