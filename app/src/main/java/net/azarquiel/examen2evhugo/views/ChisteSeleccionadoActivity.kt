package net.azarquiel.examen2evhugo.views

import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.examen2evhugo.R
import net.azarquiel.examen2evhugo.databinding.ActivityChisteSeleccionadoBinding
import net.azarquiel.examen2evhugo.model.Categorias
import net.azarquiel.examen2evhugo.model.Chiste
import net.azarquiel.examen2evhugo.model.Punto
import net.azarquiel.examen2evhugo.model.Usuario
import net.azarquiel.examen2evhugo.viewmodel.MainViewModel


class ChisteSeleccionadoActivity : AppCompatActivity() {


    private lateinit var categorias: Categorias
    private lateinit var binding: ActivityChisteSeleccionadoBinding
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario? = null
    private lateinit var chiste: Chiste
    private lateinit var rbstardetail: RatingBar
    private lateinit var rbavgdetail: RatingBar
    private lateinit var ivdetail: ImageView
    private lateinit var tvnamedetail: TextView
    private lateinit var tvdescriptiondetail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChisteSeleccionadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        tvdescriptiondetail =binding.ccs.tvdescriptiondetail
        ivdetail = binding.ccs.ivdetail
        rbavgdetail = binding.ccs.rbavgdetail
        tvnamedetail = binding.ccs.tvnamedetail
        rbstardetail = binding.ccs.rbstardetail
        categorias = intent.getSerializableExtra("categorias") as Categorias
        chiste = intent.getSerializableExtra("chiste") as Chiste
        usuario = intent.getSerializableExtra("usuario") as Usuario?
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        showChiste()
        getAvg()
        rbstardetail.setOnRatingBarChangeListener { ratingBar, _, _ ->
            ratingonclick(ratingBar.rating)
        }


    }
    private fun ratingonclick(rating: Float) {
        if (usuario==null) {
            msg("No login No star")
            rbstardetail.rating = 0f
            return
        }
        var estrellas = rating.toInt()
        viewModel.savePuntoChiste(chiste.id, Punto(0, chiste.id, estrellas)).observe(this, Observer { it ->
            it?.let{
                msg("Anotadas $estrellas star a ${chiste.id}")
                getAvg()
            }
        })
    }

    private fun getAvg() {
        viewModel.getAvgChiste(chiste.id).observe(this, Observer { it ->
            it?.let{
                rbavgdetail.rating = it.toFloat()
            }
        })
    }

    private fun showChiste() {
        Picasso.get().load("http://www.ies-azarquiel.es/paco/apichistes/img/${chiste.idcategoria}.png").into(ivdetail)
        tvnamedetail.text = categorias.nombre
        tvdescriptiondetail.text = Html.fromHtml(chiste.contenido)
    }
    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}