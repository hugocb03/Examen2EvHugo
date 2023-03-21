package net.azarquiel.examen2evhugo.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import net.azarquiel.examen2evhugo.R
import net.azarquiel.examen2evhugo.adapter.CategoriasAdapter
import net.azarquiel.examen2evhugo.adapter.ChistesAdapter
import net.azarquiel.examen2evhugo.databinding.ActivityChistesDetailBinding
import net.azarquiel.examen2evhugo.model.Categorias
import net.azarquiel.examen2evhugo.model.Chiste
import net.azarquiel.examen2evhugo.model.Usuario
import net.azarquiel.examen2evhugo.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChistesDetail : AppCompatActivity() {


    private lateinit var adapter: ChistesAdapter
    private lateinit var binding: ActivityChistesDetailBinding
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario? = null
    private lateinit var categorias: Categorias
    private lateinit  var chiste:List<Chiste>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChistesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        categorias = intent.getSerializableExtra("categorias") as Categorias
        usuario = intent.getSerializableExtra("usuario") as Usuario?
        title = "ChistesDetail - ${categorias.nombre}"
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getChistes()
        binding.fab.setOnClickListener { view ->
            if (usuario!=null) {
                dialogoChiste()
            }else{
                msg("no login, no comment")
            }

        }
    }
    
    private fun getChistes() {
        viewModel.getChistes(categorias.id).observe(this, Observer { it ->
            it?.let{
                chiste=it

                adapter.setChistes(chiste)
            }
        })
    }

    private fun initRV() {
        adapter = ChistesAdapter(this, R.layout.rowchistes)
        binding.cc.rvchistes.layoutManager = LinearLayoutManager(this)
        binding.cc.rvchistes.adapter = adapter
    }

    fun onClickChisteSeleccionado(v: View){
        val chiste = v.tag as Chiste
        val intent = Intent(this, ChisteSeleccionadoActivity::class.java)
        intent.putExtra("chiste", chiste)
        intent.putExtra("categorias",categorias)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }

    private fun dialogoChiste() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nuevo chiste")
        val ll = LinearLayout(this)
        ll.setPadding(30, 30, 30, 30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etchiste = EditText(this)
        etchiste.setPadding(0, 80, 0, 80)
        etchiste.textSize = 20.0F
        etchiste.hint = "Chiste"
        textInputLayoutNick.addView(etchiste)


        ll.addView(textInputLayoutNick)


        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->

               saveChiste(etchiste.text.toString())

        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }
        builder.show()
    }
    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun saveChiste(chisteTXT:String){
        if (chisteTXT.isEmpty()) return
       var chistes = Chiste(12, categorias.id,chisteTXT)
        viewModel.saveChiste(chistes).observe(this, Observer { it ->
            it?.let{
                val comentariosanterior = ArrayList(chiste)
                comentariosanterior.add(0, Chiste(12, it.idcategoria, it.contenido))
                chiste = comentariosanterior
                adapter.setChistes(chiste)
            }
        })
    }

}