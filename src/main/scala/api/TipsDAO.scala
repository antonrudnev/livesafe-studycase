package api

import domain.Tip
import scala.concurrent.Future

trait TipsDAO {
  def lookup(id: Int): Future[Option[Tip]]
  def all: Future[Seq[Tip]]
  def update(tip: Tip): Future[Int]
  def delete(id: Int): Future[Int]
  def create(tip: Tip): Future[Int]
}
