package tadp.dependencias

import tadp.transportes.Transporte
import tadp.transportes.Estacion
import tadp.buscador.Direccion

trait moduloExternoTransporte {

  def getTransportesCercanos(direccion: Direccion): List[Transporte]

  // Podemos permitir que retorne Option[(Estacion, Estacion)]
  def obtenerEstacionCombinacion(t1: Transporte, t2: Transporte): Option[Estacion]

  // Por qu� necesita direcci�n de origen y destino?
  // El m�dulo tiene que determinar donde hacer la combinaci�n, es decir,
  // de dos transportes me dice si puedo combinar y en donde / en que direcci�n hacerlo
  // Luego tendr� que caminar de la direcci�n donde bajo para hacer la combinaci�n hasta donde tengo 
  // la parada del otro transporte
  def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Boolean

  def distanciaEntre(estacionInicio: Estacion, estacionFin: Estacion): Int

  // Esta funcionalidad no estaba definida para el m�dulo...
  def estanCerca(direccion1: Direccion, direccion2: Direccion): Boolean

}

