package net.azarquiel.examen2evhugo.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import net.azarquiel.examen2evhugo.R
import net.azarquiel.examen2evhugo.adapter.CategoriasAdapter
import net.azarquiel.examen2evhugo.databinding.ActivityMainBinding
import net.azarquiel.examen2evhugo.model.Categorias
import net.azarquiel.examen2evhugo.model.Usuario
import net.azarquiel.examen2evhugo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var sh: SharedPreferences
    private var usuario: Usuario? = null
    private lateinit var categorias: List<Categorias>
    private lateinit var searchView: SearchView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CategoriasAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        sh = getSharedPreferences("datos", Context.MODE_PRIVATE)
        getUserSH()
        initRV()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        getCategorias()


    }
    private fun getUserSH() {
        val nick = sh.getString("nick", null)
        if (nick!=null) {
            usuario = Usuario(sh.getInt("id", 0), nick, sh.getString("pass", null)!!)
            msg("Bienvenido ${usuario!!.nick}")
            title ="ExamenChistes - " + usuario!!.nick
        }
    }
    fun saveSH(){
        val editor = sh.edit()
        editor.putInt("id", usuario!!.id)
        editor.putString("nick", usuario!!.nick)
        editor.putString("pass", usuario!!.pass)
        editor.commit()
    }

    private fun getCategorias() {
        viewModel.getCategorias().observe(this, Observer { it ->
            it?.let{
                categorias=it

                adapter.setCategorias(categorias)
            }
        })
    }

    private fun initRV() {
        adapter = CategoriasAdapter(this, R.layout.rowcategorias)
        binding.cm.rvcategorias.layoutManager = LinearLayoutManager(this)
        binding.cm.rvcategorias.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************
        return true
    }
    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Categorias>(categorias)
        adapter.setCategorias(original.filter { categorias -> categorias.nombre.contains(query,true) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
    // ************* </Filtro> ************

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {

            R.id.action_login -> {
                onClickLoginRegister()
                true
            }
            R.id.logout -> {
                   logout()
                   true
               }
               else -> super.onOptionsItemSelected(item)
           }
    }


    private fun onClickLoginRegister() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Login/Register")
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
            val etnick = EditText(this)
            etnick.setPadding(0, 80, 0, 80)
            etnick.textSize = 20.0F
            etnick.hint = "Nick"
            textInputLayoutNick.addView(etnick)

            val textInputLayoutPass = TextInputLayout(this)
            textInputLayoutPass.layoutParams = lp
            val etpass = EditText(this)
            etpass.setPadding(0, 80, 0, 80)
            etpass.textSize = 20.0F
            etpass.hint = "Pass"
            textInputLayoutPass.addView(etpass)

            ll.addView(textInputLayoutNick)
            ll.addView(textInputLayoutPass)

            builder.setView(ll)

            builder.setPositiveButton("Aceptar") { dialog, which ->
                login(etnick.text.toString(), etpass.text.toString())
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
            }
            builder.show()
        }

    private fun login(nick: String, pass: String) {
        viewModel.getUserByNickAndPass(nick, pass).observe(this, Observer { it ->
            if(it==null){
                viewModel.saveUsuario(Usuario(0,nick, pass)).observe(this, Observer { it ->
                    it?.let{
                        usuario=it
                        msg("Registrado nuevo usuario...")
                        title ="ExamenChistes - " + usuario!!.nick
                        saveSH()
                    }
                })
            }
            else {
                usuario = it
                msg("login ${usuario!!.nick}")
                title ="ExamenChistes - " + usuario!!.nick
                saveSH()
            }
        })
    }
    private fun logout() {
        removeUsuarioSH()
        msg("Logout ok....")
    }
    private fun removeUsuarioSH() {
        val editor = sh.edit()
        editor.remove("id")
        editor.remove("nick")
        editor.remove("pass")
        editor.commit()
        usuario = null
        title = "ExamenChistes"
    }
    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun onClickChiste(v: View){
        val categorias = v.tag as Categorias
        val intent = Intent(this, ChistesDetail::class.java)
        intent.putExtra("categorias", categorias)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }

}