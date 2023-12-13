package com.example.crudsec3.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codigoelectrizante.Models.Producto
import com.example.codigoelectrizante.R
import com.google.firebase.database.FirebaseDatabase

class AdapterProducto(private val productos: ArrayList<Producto>) :
    RecyclerView.Adapter<AdapterProducto.ViewHolder>() {

    // Interfaz para manejar clics del botón de eliminar
    interface OnEliminarClickListener {
        fun onEliminarClick(position: Int, productoId: String?)
    }

    // Propiedad para almacenar la interfaz
    var onEliminarClickListener: OnEliminarClickListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val precio: TextView = itemView.findViewById(R.id.tvPrecio)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminaritem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]

        holder.nombre.text = producto.nombre
        holder.precio.text = producto.precio
        holder.descripcion.text = producto.descripcion

        // Configurar clic de botón de eliminar
        holder.btnEliminar.setOnClickListener {
            onEliminarClickListener?.onEliminarClick(position, producto.id)
        }
    }

    // Método para eliminar un producto
    fun eliminarProducto(position: Int, productoId: String?) {
        // Eliminar de Firebase
        val database = FirebaseDatabase.getInstance().getReference("Productos")
        database.child(productoId!!).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Éxito al eliminar de Firebase
                    // Puedes mostrar un mensaje de éxito si lo deseas
                    Log.d("EliminarProducto", "Producto eliminado con éxito")

                    // Eliminar de la lista local
                    productos.removeAt(position)
                    // Notificar al adaptador que se ha eliminado un elemento en la posición dada
                    notifyItemRemoved(position)
                    // Notificar al adaptador que los datos han cambiado
                    notifyDataSetChanged()
                } else {
                    // Error al eliminar de Firebase
                    // Puedes mostrar un mensaje de error si lo deseas
                    Log.e("EliminarProducto", "Error al eliminar producto", task.exception)
                }
            }
    }
}