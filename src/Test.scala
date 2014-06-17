
object Test extends App{
  
  /******** Test ********/
	 val moduloExterno = new moduloExternoTransporte
		  
    //Creacion transportes
	 val e1 = new Estacion(1,"e1")
     val combinaciones1 = Map("E" -> 7)  
	 val colectivo23 = new Colectivo("23",e1,combinaciones1)
	 
	 val e2 = new Estacion(10,"e1")
     val combinaciones2 = Map("23" -> 5)
	 val subteE = new Subte("E",e2,combinaciones2)
	 
	 val e3 = new Estacion(1,"e3")
	 val combinaciones3 = Map("E" -> 7)  
	 val colectivo15 = new Colectivo("15",e3,combinaciones1)
     
     //Creacion direcciones
     val origen1 =  new Direccion("origen1", List(colectivo23,colectivo15))
     val destino1 =  new Direccion("destino1", List(colectivo23,subteE,colectivo15))
     
     //Get transportes cercanos de direcciones
    
    var transportesCercanosOrigen = moduloExterno.getTransportesCercanos(origen1)
    var transportesCercanosDestino = moduloExterno.getTransportesCercanos(destino1)
    
    for (t <- transportesCercanosDestino) println(t.getClass()+" Linea: "+t.linea+" Estacion: "+t.estacion.numero)
    
    println(moduloExterno.puedeCombinarse(colectivo23 , subteE ))
    
    val busqueda = new Busqueda
    println("Test tramo un solo transporte")
	busqueda.calcularTramos(transportesCercanosOrigen, transportesCercanosDestino)

}