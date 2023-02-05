import slick.jdbc.H2Profile.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

// The main application
object DatabaseInterfaceDemo extends App {
  val db = Database.forConfig("h2mem1")
  try {

    val dbi = new DatabaseInterface()
    val setupAction = (dbi.suppliers.schema ++ dbi.coffees.schema).create
    val setupFuture = db.run(setupAction)
    val f = setupFuture.flatMap { _ =>

      // Insert suppliers to SUPPLIERS table
      dbi.insertSupplier(101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199")
      dbi.insertSupplier(49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460")
      dbi.insertSupplier(150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")
      dbi.insertSupplier(98, "Palo Alto Coffee", "El Camino Real", "Palo Alto", "CA", "94305")

      // Insert coffees to COFFEES table
      dbi.insertCoffee("Espresso", 98, 9.99, 0, 0)
      dbi.insertCoffee("Colombian", 101, 7.99, 0, 0)
      dbi.insertCoffee("Colombian_Decaf", 101, 8.99, 0, 0)
      dbi.insertCoffee("French_Roast", 150, 8.99, 0, 0)
      dbi.insertCoffee("French_Roast_Decaf", 150, 9.99, 0, 0)

      // Update sales of coffees in COFFEES table
      dbi.updateCoffeeSales("Espresso", 1)
      dbi.updateCoffeeSales("Colombian", 2)

      // Delete coffee from COFFEES table
      dbi.deleteCoffee("French_Roast_Decaf")

      // Delete supplier from SUPPLIERS table
      dbi.deleteSupplier(49)

    }.flatMap { _ =>

      // Print updated COFFEES and SUPPLIERS tables
      dbi.printCoffeesTable()
      dbi.printSuppliersTable()

    }
    Await.result(f, Duration.Inf)

  } finally db.close
}
