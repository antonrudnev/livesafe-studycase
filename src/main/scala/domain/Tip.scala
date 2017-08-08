package domain

import org.joda.time.DateTime

case class Tip(id: Int = -1,
               message: String,
               submittedBy: String,
               createdAt: DateTime = DateTime.now(),
               updatedAt: Option[DateTime] = None,
               _version: Int = 0)
