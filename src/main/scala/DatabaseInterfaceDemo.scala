import slick.basic.DatabasePublisher
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

      dbi.insertSupplier(101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199")
      dbi.insertSupplier(49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460")
      dbi.insertSupplier(150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")

      dbi.insertCoffee("Espresso", 150, 9.99, 0, 0)
      dbi.insertCoffee("Colombian", 101, 7.99, 0, 0)
      dbi.insertCoffee("Colombian_Decaf", 101, 8.99, 0, 0)
      dbi.insertCoffee("French_Roast", 150, 8.99, 0, 0)
      dbi.insertCoffee("French_Roast_Decaf", 101, 9.99, 0, 0)

      dbi.updateCoffeeSales("Espresso", 1)
      dbi.updateCoffeeSales("Colombian", 1)

      dbi.deleteCoffee("French_Roast_Decaf")
      dbi.deleteSupplier(49)

    }.flatMap { _ =>

      dbi.printCoffeesTable()
      dbi.printSuppliersTable()

    }
    Await.result(f, Duration.Inf)

  } finally db.close
}
