package api

import domain.Comment
import scala.concurrent.Future

trait CommentsDAO {
  def lookup(id: Int): Future[Option[Comment]]
  def all(tipId: Int): Future[Seq[Comment]]
  def delete(id: Int): Future[Int]
  def create(comment: Comment): Future[Int]
}
