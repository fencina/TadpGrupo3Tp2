package comoViajo

import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import tadp.buscador._
import tadp.transportes._
import tadp.dependencias.moduloExternoTransporte
import tadp.dependencias.moduloExternoTransporte


class TestDeEjemplo {

  val busqueda = new Busqueda

  val ParqueChacabuco: ZonaTrait = new Zona()
  val Caballito: ZonaTrait = new Zona()
  val Barracas: ZonaTrait = new Zona()
  val Flores: ZonaTrait = new Zona()
  val Palermo: ZonaTrait = new Zona()
  val SanTelmo: ZonaTrait = new Zona()
  val Centro: ZonaTrait = new ZonaTrabajo()

  //Creacion direcciones
  val Puan1228: Direccion = new Direccion(ParqueChacabuco)
  val Florida100: Direccion = new Direccion( Centro)
  val Florida200: Direccion = new Direccion( Centro)
  val Florida300: Direccion = new Direccion( Centro)
  val MontesDeOca700: Direccion = new Direccion( Barracas)
  val Yerbal434: Direccion = new Direccion( Flores)
  val Araoz2424: Direccion = new Direccion(Palermo)
  val EscalabriniOrtiz1500: Direccion = new Direccion( Palermo)
  val Asamblea564: Direccion = new Direccion(ParqueChacabuco)
  val JoseMariaMoreno1500: Direccion = new Direccion(ParqueChacabuco)
  val Azara567: Direccion = new Direccion(Barracas)
  val Nazca596: Direccion = new Direccion( Flores)
  val Nazca500: Direccion = new Direccion( Flores)
  val Florida900: Direccion = new Direccion(Centro)
  val Callao242: Direccion = new Direccion(Centro)
  val Piedras485: Direccion = new Direccion(SanTelmo)

  //Creacion Estaciones
  val Estacion01SubteE = new Estacion( "e1", Puan1228)
  val Estacion02SubteE = new Estacion("e2", Florida100)
  val Estacion03SubteE = new Estacion("e3", MontesDeOca700)
  val Estacion04SubteE = new Estacion("e4", Nazca596)

  val Estacion01SubteA = new Estacion( "e1", Florida200)
  val Estacion02SubteA = new Estacion("e2", Asamblea564 )
  val Estacion03SubteA = new Estacion("e3", Piedras485 )
  val Estacion04SubteA = new Estacion("e4", Callao242)

  
  val Estacion01Colectivo4 = new Estacion("estacion 1", Piedras485);
  val Estacion02Colectivo4 = new Estacion( "estacion 2", Florida300);
  val Estacion03Colectivo4 = new Estacion( "estacion 3", Florida900);
  val Estacion04Colectivo4 = new Estacion( "estacion 4", Azara567);
  val Estacion05Colectivo4 = new Estacion("estacion 5", Yerbal434);
  val Estacion06Colectivo4 = new Estacion("estacion 6", Nazca596 );

  val Estacion01Tren = new Estacion( "e1", Puan1228)
  val Estacion02Tren = new Estacion("e2", Nazca596)

 
  //Creacion transportes
  val subteE = new Subte("E", List(Estacion01SubteE, Estacion02SubteE, Estacion03SubteE, Estacion04SubteE))
  val subteA = new Subte("A", List(Estacion01SubteA, Estacion02SubteA, Estacion03SubteA, Estacion04SubteA))
 
  val colectivo04 = new Colectivo("04", List(Estacion01Colectivo4, Estacion02Colectivo4, Estacion03Colectivo4, Estacion04Colectivo4, Estacion05Colectivo4,Estacion06Colectivo4 ))

  val tren = new Tren("Tren", List(Estacion01Tren, Estacion02Tren), List(new PrecioTren(5, 3), new PrecioTren(10, 6)))
  

  @Test
  def viajoDePuanAMontesDeOcaYMeDaUnViajeConElE = {
    
    val moduloExternoMock = new moduloExternoTransporte(){
   
         
  def getTransportesCercanos(direccion: Direccion): List[Transporte] = List(subteE)

  def obtenerEstacionCombinacion(t1: Transporte, t2: Transporte): Option[Estacion] = None 
  def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Boolean = false
 
  def distanciaEntre(estacionInicio: Estacion, estacionFin: Estacion): Int = 400
  
   def estanCerca(direccion1: Direccion, direccion2: Direccion): Boolean = direccion1==direccion2;
    }

    val viajes = busqueda.comoViajo(Puan1228, MontesDeOca700 ,None,PorTiempo(),moduloExternoMock)

    assertEquals(1, viajes.length)
    assertEquals(1, viajes.head.tramos.length)
    assertEquals(subteE, viajes.head.tramos.head.transporte)

    //Chequeo que el costo sea 4,50
    assertEquals(4.50, viajes.head.costo, 0)

    //Chequeo que el viaje dure 4 minutos (recorre 2 estaciones E01->E02->E03)
    assertEquals(4, viajes.head.duracion, 0)
    
    
    //Realizo ahora el mismo viaje pero con un descuento de Discapacitados (El precio deberia ser 0)
    val viajesDiscapacitados = busqueda.comoViajo(Puan1228, MontesDeOca700 ,Option(Discapacitados()),PorTiempo(),moduloExternoMock)
    assertEquals(0, viajesDiscapacitados.head.costo,0)
    
    
    //Realizo ahora el mismo viaje pero con un descuento de Turismo(El precio deberia ser 4.5*0.9 = 4.05)
    val viajesTurismo = busqueda.comoViajo(Puan1228, MontesDeOca700 ,Option(Turismo(Barracas)),PorTiempo(),moduloExternoMock)
    assertEquals(4.05, viajesTurismo.head.costo,0)
    
    //Realizo ahora el mismo viaje pero con un descuento de Turismo(El precio deberia ser 4.5-1.5 = 3)
    val viajesTrabajo= busqueda.comoViajo(Puan1228, Florida100,Option(Trabajo()),PorTiempo(),moduloExternoMock)
    assertEquals(3, viajesTrabajo.head.costo,0)
    
  }

  
  
  @Test
  def combino2SubtesYElPrecioSeMantieneEn4con50 = {
    
    val moduloExternoMock = new moduloExternoTransporte(){

        def getTransportesCercanos(direccion: Direccion): List[Transporte] = {
    if (direccion==Puan1228)
          List(subteE);
    else
      List(subteA)
  }

  def obtenerEstacionCombinacion(t1: Transporte, t2: Transporte): Option[Estacion] ={
    if (t1==subteE && t2==subteA)
      return Some(Estacion02SubteE )
      
    if (t1==subteA && t2==subteE)
      return Some(Estacion01SubteA)
      
      return None
  }
   
  def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Boolean = true
 
  def distanciaEntre(estacionInicio: Estacion, estacionFin: Estacion): Int = 100
  
     def estanCerca(direccion1: Direccion, direccion2: Direccion): Boolean = direccion1==direccion2;

  
    }

    val viajes = busqueda.comoViajo(Puan1228, Callao242 ,None,PorTiempo(),moduloExternoMock)

    
    	
    assertEquals(1, viajes.length)
    assertEquals(2, viajes.head.tramos.length)
    assertEquals(subteE, viajes.head.tramos.head.transporte)
    assertEquals(subteA, viajes.head.tramos.tail.head.transporte)
    
    //Chequeo que el costo sea 4,50 (no se tiene en cuenta el cambio de linea)
    assertEquals(4.50, viajes.head.costo, 0)

    //Chequeo que el viaje dure 12 minutos (recorre 4 estaciones E01->E02->->A02->A03->A04, 8 MIN y se le agregan 4 por cambio de subte) 
    assertEquals(12, viajes.head.duracion, 0)
  }

  
  
  @Test
  def obtendo2ViajesYPrueboLosCriterios= {
    
    val moduloExternoMock = new moduloExternoTransporte(){

         def estanCerca(direccion1: Direccion, direccion2: Direccion): Boolean = {
           direccion1==direccion2 || (direccion1==Florida200 && direccion2==Florida100 )}

      
        def getTransportesCercanos(direccion: Direccion): List[Transporte] = {
    if (direccion==Florida100)
          return List(subteE,subteA);
    else
    	if (direccion==Nazca596)
    		return List(colectivo04,subteE);
    	
    	
    	return List()	
    }

  def obtenerEstacionCombinacion(t1: Transporte, t2: Transporte): Option[Estacion] ={
    if (t1==subteA && t2==colectivo04)
      return Some(Estacion03SubteA )
      
    if (t1==colectivo04 && t2==subteA)
      return Some(Estacion04Colectivo4 )
      
      return None
  }
   
  def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Boolean = {
   (t1==subteA && t2==colectivo04)
  
  }
 
  def distanciaEntre(estacionInicio: Estacion, estacionFin: Estacion): Int = 500
  
    }

    val viajes = busqueda.comoViajo(Florida100 , Nazca596 ,None,PorTiempo(),moduloExternoMock)

    
    	//Me da 2 viajes
    assertEquals(2, viajes.length)
    

    //Ordenó correctamente por tiempo
    
    //Chequeo que el primer viaje dure 4 minutos (recorre 2 estaciones en el subteE E02->E03->E04
    assertEquals(4, viajes.head.duracion, 0)
    //Chequeo que el primer viaje dure 18.5 minutos (recorre 2 estaciones en el subteA y 2 estaciones en colectivo 04
    assertEquals(18.5, viajes.tail.head.duracion, 0)

    
    //Chequeo el mismo viaje pero ahora por Precio como criterio
    
    val viajesPorPrecio = busqueda.comoViajo(Florida100 , Nazca596 ,None,PorPrecio(),moduloExternoMock)

    
    	//Me da 2 viajes
    assertEquals(2, viajesPorPrecio.length)
    
   
 
    //Ordenó correctamente por tiempo
    
    assertEquals(4.5, viajesPorPrecio.head.costo, 0)
    assertEquals(7, viajesPorPrecio.tail.head.costo, 0)
  
    
  }

 
}