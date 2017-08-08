import domain.{Comment, Tip}
import slick.DataContext
import scala.concurrent.ExecutionContext.Implicits.global

object ScalaApp {
  def main(args: Array[String]) {
    val data = new DataContext("livesafedb")
    //      data.Comments.create(Comment(tipId=9,message="created tipytip")).map(println)
    data.Tips.all.map(_.foreach(t => println(t.id + " supplied by " + t.submittedBy)))
    //    data.Comments.all(1).map(_.foreach(t =>println("lookup  " + t.id + " supplied by " + t.message)))
    data.close()
  }
}