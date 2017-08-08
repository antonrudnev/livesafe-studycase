package slick

import api.{CommentsDAO, TipsDAO}
import domain.{Comment, Tip}
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.Future

class DataContext(connection: String) {

  private val db = Database.forConfig(connection)

  def close(): Future[Unit] = {
    Future.successful(db.close())
  }

  object Tips extends TipsDAO {
    def lookup(id: Int): Future[Option[Tip]] = {
      db.run(tips.filter(_.id === id).result.headOption)
    }

    def all: Future[Seq[Tip]] = {
      db.run(tips.result)
    }

    def update(tip: Tip): Future[Int] = {
      db.run(tips.filter(t => t.id === tip.id && t._version === tip._version)
        .update(tip.copy(_version = tip._version + 1)))
    }

    def delete(id: Int): Future[Int] = {
      db.run(tips.filter(_.id === id).delete)
    }

    def create(tip: Tip): Future[Int] = {
      db.run(tips += tip.copy(createdAt = DateTime.now(), _version = 0))
    }
  }

  object Comments extends CommentsDAO {
    def lookup(id: Int): Future[Option[Comment]] = {
      db.run(comments.filter(_.id === id).result.headOption)
    }

    def all(tipId: Int): Future[Seq[Comment]] = {
      db.run(comments.filter(_.tipId === tipId).result)
    }

    def delete(id: Int): Future[Int] = {
      db.run(comments.filter(_.id === id).delete)
    }

    def create(comment: Comment): Future[Int] = {
      db.run(((comments += comment) andThen
        tips.filter(_.id === comment.tipId)
          .map(t => t.updatedAt)
          .update(Some(DateTime.now()))).transactionally)
    }
  }

  private class TipsTable(tag: Tag) extends Table[Tip](tag, "tip") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def message = column[String]("message")
    def submittedBy = column[String]("submitted_by", O.Length(50))
    def createdAt = column[DateTime]("created_at")
    def updatedAt = column[Option[DateTime]]("updated_at")
    def _version = column[Int]("_version")
    def * = (id, message, submittedBy, createdAt, updatedAt, _version) <> (Tip.tupled, Tip.unapply)
  }

  private class CommentsTable(tag: Tag) extends Table[Comment](tag, "comment") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def tipId = column[Int]("tip_id")
    def message = column[String]("message")
    def * = (id, tipId, message) <> (Comment.tupled, Comment.unapply)
    def tip = foreignKey("TIP_FK", tipId, tips)(_.id)
  }

  private val tips = TableQuery[TipsTable]

  private val comments = TableQuery[CommentsTable]
}