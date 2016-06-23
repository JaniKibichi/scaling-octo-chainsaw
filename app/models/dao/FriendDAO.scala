package models.dao
import models.Friend
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._

import.scala.concurrent._

object FriendDAO{
 val friendServiceUrl = Play.current.configuration.getString("service.backend.url").get + "/friends"

 /**
  *Fetches a list of friends associated with the given user ID
  *@param userId the ID of the user to find the friends of
  *@return A list of friends associated with the speecified user
  */

def index(userId: Int): Future[List[Friend]] = {
 val holder: WSRequestHolder = WS.url(friendServiceUrl)
   .withQueryString("userId" -> userId.toString)

 //Execute the web service request and get a future of a response
 val fResponse = holder.get()

 fResponse.map { response =>
  //The response will be JSON so parse out the list of friends Ids
  val friendIds: List[Int] = (response.json \ "result").as[List[Int]]

  //Map the list of IDs to a list of friends
  friendIds.map(Friend.apply)

  }
 }
}
