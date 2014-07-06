package tadp.dependencias

import tadp.transportes.Transporte
import tadp.transportes.Estacion
import tadp.buscador.Direccion



trait moduloExternoTransporte {


  def getTransportesCercanos(direccion: Direccion): List[Transporte] 

  def obtenerEstacionCombinacion(t1: Transporte, t2: Transporte): Option[Estacion]

  def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Boolean
 
  def distanciaEntre(estacionInicio: Estacion, estacionFin: Estacion): Int 
  
    def estanCerca(direccion1: Direccion, direccion2: Direccion): Boolean 

}

