package es.workinprocess.asignaturasdam

/**
 * Estructura de "Clase"
 */
class Clase {

    var fecha = ""; //Fecha de la clase
    var modulo = ""; //Nombre del módulo
    var desde = ""; //Hora de comienzo
    var hasta = ""; //Hora de final.

    /**
     * Funcion temporal y de estar por casa para calcular las horas de la clase.
     * Simplemente resta la hora de final menos la de comienzo obviando los minutos.
     *
     * WIP: Trabajar con Fechas y Horas.
     */
    fun horas(): Int{
        val ini = this.desde.split(":")
        val end = this.hasta.split(":")

        return end[0].toInt() - ini[0].toInt();
    }

    /**
     * Obtiene un String con la informacion de la clase resumida.
     * @return String
     */
    fun getInfoMessage(): String = "De $desde a $hasta (${horas()} hora${if(horas()>1) "s" else ""})"

    /**
     * Metodo con la información del estado de la clase.
     */
    override fun toString(): String = "Clase(fecha='$fecha', modulo='$modulo', desde='$desde', hasta='$hasta', horas=${this.horas()})"
}