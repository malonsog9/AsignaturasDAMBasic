package es.workinprocess.asignaturasdam

/**
 * Clase para el tratamiento de la información de las clases.
 */
class Clases {

    private var curso: String = "" //Nombre del Curso del que cargamos el calendario.
    private var calendario: ArrayList<Clase>? = null //Calendario de clases.

    /**
     * Busca la clase correspondiente a la fecha que recibe como parametro.
     * @param fecha Fecha en la que buscamos una clase.
     * @return La clase encontrada, NULL si no la encuentra
     */
    fun getClase(fecha: String): Clase? {

        var posicion: Int = 0; //Posicion inicial para inicial el bucle while.
        var encontrado: Boolean = false //Indica si se ha entrado el item, false por defecto.

        //Recorremos el Arraylist con un while en busca del item.
        while (posicion < this.calendario!!.size.minus(1) && !encontrado){

            encontrado = (this.calendario?.get(posicion))?.fecha == fecha

            posicion++;

        }

        //Si lo hemos encontrado, devolvemos la clase correspondiente, en caso contrario null.
        if(encontrado){
            return this.calendario?.get(--posicion)
        }
        else{
            return null
        }
    }

    /**
     * Metodo para desarrollo en la que se pinta por consola la inforamcion
     * de la estructura de datos.
     */
    fun printClases(): Unit{

        println("Las Clases de $curso son:")
        //println(this.calendario)

        for (clase in this.calendario!!){

            println("DIA: ${clase.fecha}")
            println("MODULO: ${clase.modulo}")
            println("DESDE: ${clase.desde}")
            println("HASTA: ${clase.hasta}")
            println("HORAS: ${clase.horas()}")
            println("---------------------")

        }
    }
}