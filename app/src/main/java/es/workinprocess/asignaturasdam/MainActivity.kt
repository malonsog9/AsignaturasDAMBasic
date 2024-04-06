package es.workinprocess.asignaturasdam

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import es.workinprocess.asignaturasdam.ui.theme.AsignaturasDAMTheme
import java.io.IOException
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = getColor(R.color.primary)
            window.navigationBarColor = getColor(R.color.primary)

            AsignaturasDAMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                    //App("param1")
                }
            }
        }
    }
}

/**
 * Es una prueba para coprobar la sobrecarga de metodos.
 */
@Composable
fun App(param1: String, modifier: Modifier = Modifier) {
    Text(text = param1)
}

@Composable
fun App(modifier: Modifier = Modifier) {

    val jsonData: String = loadJSOM(LocalContext.current)
    val clases: Clases = Gson().fromJson(jsonData, Clases::class.java)

    //Creo que la m la puse de mutable. :)
    var mfecha by remember { mutableStateOf(LocalDate.now()) } //LocalDate
    //Buscamos la clase correspondiente al dia.
    var mclase by remember { mutableStateOf(clases.getClase(mfecha.toString())) } //Clase

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    )
    {
        Row( //Row superior
            modifier = Modifier.weight(1f)
        ){
            Column( //Column para el contenido de la información.
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ){
                if(mclase != null){
                    TarjetaDeClase(mclase!!)
                }
                else{
                    TarjetaNoClase(mfecha)
                }
            }//End Column
        }//End Row superior

        Row(//Row Inferior
            horizontalArrangement = Arrangement.SpaceBetween, //Cada elemento distribuira su ancho hasta ocupar el 100% lueago alineamos los elementos(Columns) cada uno aun extremo
            modifier = modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.primary))
        ){
            Column(
                horizontalAlignment = Alignment.Start
            ){
                TextButton(
                    onClick = {
                        //Decrementamos la fecha y buscamos la clase.
                        mfecha = mfecha.minusDays(1)
                        mclase = clases.getClase(mfecha.toString())
                        //Pintamos en la consola
                        println("Left Click ${mfecha}")
                        println(mclase)
                    },
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .fillMaxHeight()
                ){
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Anterior",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
            ){
                TextButton(
                    onClick = {
                        //Incrementamos la fecha y buscamos la clase
                        mfecha = mfecha.plusDays(1)
                        mclase = clases.getClase(mfecha.toString())
                        //Pintamos en la consola
                        println("Right Click ${mfecha}")
                        println(mclase)
                    },
                    content = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Siguiente",
                            tint = colorResource(id = R.color.white),
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .fillMaxHeight()
                )
            }
        }//End Row Inferior
    }

}

/**
 * Información de la clase No usamos Modifier.
 * @param clase Clase que pintamos.
 */
@Composable
fun TarjetaDeClase(clase: Clase){
    //Pintamos la caja de la fecha
    val fecha = LocalDate.parse(clase.fecha)
    DateBox(fecha.dayOfMonth, monthName(fecha.monthValue))
    Text(
        text = clase.modulo,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.black),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .alpha(0.5f)
    )
    Row(){
        Icon(
            Icons.Rounded.Info,
            contentDescription = "info",
            tint = colorResource(id = R.color.primary),
            modifier = Modifier
                .size(24.dp)
                .alpha(0.75f)
        )
        Text(
            text = clase.getInfoMessage(),
            fontSize = 16.sp,
            modifier = Modifier.padding(start=4.dp, top=0.dp)
        )
    }
}

@Composable
fun TarjetaNoClase(fecha:LocalDate){

    //Pintamos la caja de la fecha
    DateBox(fecha.dayOfMonth, monthName(fecha.monthValue))

    Text(
        text = "No hay clase",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.black),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .alpha(0.5f)
    )

}

@Composable
fun DateBox(day: Int, month: String){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(color = colorResource(id = R.color.primary))
            .height(150.dp)
            .width(150.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(bottom = 16.dp)
        ){
            Text(
                text = day.toString(),
                textAlign = TextAlign.Center,
                fontSize = 72.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.white),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxHeight(1f)
        ){
            Text(
                text = month,
                fontSize = 24.sp,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AsignaturasDAMTheme {
        //App("HOLA")
        App()
    }
}

/**
 * Metodo para cargar el JSON de las clases.
 * @return String
 */
private fun loadJSOM(context: Context): String {

    var tContents = ""

    try{
        val stream = context.assets.open("clases.json")
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        tContents = String(buffer)
    }catch (e: IOException){
        //Gestionamos el error
    }

    return tContents
}

/**
 * Devuelve el nombre del mes recibido como entero.
 * @param monthIntValue Numero del mes recibido como un entero
 * @return String con el nombre del mes.
 */
private fun monthName(monthIntValue: Int): String = arrayOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre", "Noviembre","Diciembre")[monthIntValue-1]