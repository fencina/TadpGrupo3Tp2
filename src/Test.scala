
object Test extends App{
  
  /******** Test ********/
	 val moduloExterno = new moduloExternoTransporte
	 
      //Creacion direcciones
     val origen1: Direccion =  new Direccion("origen1")  
     val destino1: Direccion =  new Direccion("destino1")  
	 
  	 //Creacion Estaciones
	 val e1 = new Estacion(1,"e1",origen1)
	 val e2 = new Estacion(10,"e2",destino1)
	 
    //Creacion transportes
	 
	 val colectivo23 = new Colectivo("23",List(e1,e2))
	 
	 val subteE = new Subte("E",List(e2))
	 
	 val colectivo15 = new Colectivo("15",List(e1,e2))
	 
	 //Asignar transportes cercanos a direcciones
	 origen1.transportesCercanos = List(colectivo23,colectivo15)
	 destino1.transportesCercanos = List(colectivo23,subteE,colectivo15)
	 
     //Get transportes cercanos de direcciones
    
    var transportesCercanosOrigen = moduloExterno.getTransportesCercanos(origen1)
    var transportesCercanosDestino = moduloExterno.getTransportesCercanos(destino1)
    
    //for (t <- transportesCercanosDestino) println(t.getClass()+" Linea: "+t.linea+" Estacion: "+t.estacion.numero)
    
    println(moduloExterno.obtenerCombinaciones(colectivo23 , subteE ))
    
    val busqueda = new Busqueda
    println("Test tramo un solo transporte")
	 
	var viajes = busqueda.comoViajo(origen1, destino1)
	
	for (v <- viajes) for (t <- v.tramos) println(t.transporte.linea  )

}