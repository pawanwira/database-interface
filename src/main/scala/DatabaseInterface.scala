import slick.basic.DatabasePublisher
import slick.jdbc.H2Profile.api._

import scala.concurrent.{Await, Future}
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class DatabaseInterface() {
  val db = Database.forConfig("h2mem1")

  // The query interface for the Suppliers table
  val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

  // the query interface for the Coffees table
  val coffees: TableQuery[Coffees] = TableQuery[Coffees]

  def insertCoffee(name: String, supID: Int, price: Double, sales: Int, total: Int): Future[Int] = {
    val insertAction = coffees += (name, supID, price, sales, total)
    val future = db.run(insertAction)
    val sql = insertAction.statements.head
    future.onComplete {
      case Success(idx) => println("SQL (simplified) compiled: " + sql + "\nSuccess: Inserted " + idx + " row to Coffees table \n")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  def insertSupplier(id: Int, name: String, street: String, city: String, state: String, zip: String): Future[Int] = {
    val insertAction = suppliers += (id, name, street, city, state, zip)
    val future = db.run(insertAction)
    val sql = insertAction.statements.head
    future.onComplete {
      case Success(idx) => println("SQL (simplified) compiled: " + sql + "\nSuccess: Inserted " + idx + " row to Suppliers table \n")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  def updateCoffeeSales(name: String, newSales: Int): Future[Int] = {
    val updateAction = coffees.filter(_.name === name).map(_.sales).update(newSales)
    val future = db.run(updateAction)
    val sql = updateAction.statements.head
    future.onComplete {
      case Success(idx) => println("SQL (simplified) compiled: " + sql + "\nSuccess: Updated " + idx + " row from Coffees table \n")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  def deleteCoffee(name: String): Future[Int] = {
    val deleteAction = coffees.filter(_.name === name).delete
    val future = db.run(deleteAction)
    val sql = deleteAction.statements.head
    future.onComplete {
      case Success(idx) => println("SQL (simplified) compiled: " + sql + "\nSuccess: Deleted " + idx + " row from Coffees table \n")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }

  def deleteSupplier(id: Int): Future[Int] = {
    val deleteAction = suppliers.filter(_.id == id).delete
    val future = db.run(deleteAction)
    val sql = deleteAction.statements.head
    future.onComplete {
      case Success(idx) => println("SQL (simplified) compiled: " + sql + "\nSuccess: Deleted " + idx + " row from Coffees table \n")
      case Failure(t) => println("Failure: " + t.getMessage)
    }
    return future
  }
  def printCoffeesTable(): Future[Unit] = {
    Thread.sleep(200)
    println("Coffees table:")
    val future = db.run(coffees.result).map(_.foreach {
      case (name, supID, price, sales, total) =>
        println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
    })
    return future
  }

  def printSuppliersTable(): Future[Unit] = {
    Thread.sleep(200)
    println("Suppliers table:")
    val future = db.run(suppliers.result).map(_.foreach {
      case (id, name, street, city, state, zip) =>
        println("  " + id + "\t" + name + "\t" + street + "\t" + city + "\t" + state + "\t" + zip)
    })
    return future
  }
}
