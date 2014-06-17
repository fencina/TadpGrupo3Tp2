
object Test extends App{
  
  /******** Test ********/
	 val moduloExterno = new moduloExternoTransporte
		  
    //Creacion transportes
     val combinaciones1 = Map("E" -> 7)  
	 val colectivo23 = new Colectivo("23",1,combinaciones1)
     
     val combinaciones2 = Map("23" -> 5)
	 val subteE = new Subte("E",10,combinaciones2)
     
     //Creacion direcciones
     val origen1 =  new Direccion("origen1", List(colectivo23))
     val destino1 =  new Direccion("destino1", List(colectivo23,subteE))
     
     //Get transportes cercanos de direcciones
    
    var transportesCercanosOrigen = moduloExterno.getTransportesCercanos(origen1)
    var transportesCercanosDestino = moduloExterno.getTransportesCercanos(destino1)
    
    for (t <- transportesCercanosOrigen) println(t.getClass()+" Linea: "+t.linea+" Estacion: "+t.estacion)
    
    println(moduloExterno.puedeCombinarse(colectivo23 , subteE ))

}