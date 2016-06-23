package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


object Friend {
 def findAllFriends(userId: Int)(implicit ec :ExecutionContext): Future[Set[Friend]] =
   FriendDAO.index(userId).map(_.toSet)

}

case class Friend(friendId: Int)
