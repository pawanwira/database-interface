import slick.jdbc.H2Profile.api._
import scala.concurrent.{Await, Future}
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

// The database interface class
class DatabaseInterface() {
  val db = Database.forConfig("h2mem1")

  // The query interface for the Suppliers table
  val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

  // The query interface for the Coffees table
  val coffees: TableQuery[Coffees] = TableQuery[Coffees]

  /**
   * Equivalent SQL code:
   * INSERT INTO COFFEES (COF_NAME, SUP_ID, PRICE, SALES, TOTAL)
   * VALUES (name, supID, price, sales, total)
   *  */
  def insertCoffee(name: String, supID: Int, price: Double, sales: Int, total: Int): Future[Int] = {
    val insertAction = coffees += (name, supID, price, sales, total)
    val future = db.run(insertAction)
    future.onComplete {
      case Success(idx) => println("Success: Inserted " + idx + " row (name: " + name + ") to Coffees table")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  /**
   * Equivalent SQL code:
   * INSERT INTO SUPPLIERS (SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP)
   * VALUES (id, name, street, city, street, zip)
   * */
  def insertSupplier(id: Int, name: String, street: String, city: String, state: String, zip: String): Future[Int] = {
    val insertAction = suppliers += (id, name, street, city, state, zip)
    val future = db.run(insertAction)
    future.onComplete {
      case Success(idx) => println("Success: Inserted " + idx + " row (name: " + name + ") to Suppliers table")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  /**
   * Equivalent SQL code:
   * UPDATE COFFEES
   * SET SALES = sales
   * WHERE COF_NAME = name
   * */
  def updateCoffeeSales(name: String, newSales: Int): Future[Int] = {
    val updateAction = coffees.filter(_.name === name).map(_.sales).update(newSales)
    val future = db.run(updateAction)
    future.onComplete {
      case Success(idx) => println("Success: Updated " + idx + " row from Coffees table (updated sales of " + name + " to " + newSales + ")")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  /**
   * Equivalent SQL code:
   * DELETE FROM COFFEES
   * WHERE COF_NAME = name
   * */
  def deleteCoffee(name: String): Future[Int] = {
    val deleteAction = coffees.filter(_.name === name).delete
    val future = db.run(deleteAction)
    future.onComplete {
      case Success(idx) => println("Success: Deleted " + idx + " row (name: " + name + ") from Coffees table")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  /**
   * Equivalent SQL code:
   * DELETE FROM SUPPLIERS
   * WHERE SUP_ID = id
   * */
  def deleteSupplier(id: Int): Future[Int] = {
    val deleteAction = suppliers.filter(_.id === id).delete
    val future = db.run(deleteAction)
    future.onComplete {
      case Success(idx) => println("Success: Deleted " + idx + " row (id: " + id + ") from Suppliers table")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  /**
   * Prints Coffees table
   */
  def printCoffeesTable(): Future[Unit] = {
    Thread.sleep(200)
    println("COFFEES table (COF_NAME, SUP_ID, PRICE, SALES, TOTAL):")
    val future = db.run(coffees.result).map(_.foreach {
      case (name, supID, price, sales, total) =>
        println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
    })
    return future
  }

  /**
   * Prints Suppliers table
   */
  def printSuppliersTable(): Future[Unit] = {
    Thread.sleep(200)
    println("SUPPLIERS table (SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP):")
    val future = db.run(suppliers.result).map(_.foreach {
      case (id, name, street, city, state, zip) =>
        println("  " + id + "\t" + name + "\t" + street + "\t" + city + "\t" + state + "\t" + zip)
    })
    return future
  }
}
