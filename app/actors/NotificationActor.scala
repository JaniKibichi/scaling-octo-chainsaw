package actors

object NotificationActor{
 def props: Props = Props(new NotificationActor)
}

class NotificationActor extends Actor{
 def receive = {
  case favourite: FavouriteStudio =>
    notifyFriends(favourite)
 }

 private def notifyFriends( favourite: FavouriteStudio): Unit = {
  //Lookup list
  val fFriends = Friend.findAllFriends(favourite.userId)

  //send a push notification to each friend
  for {
    friends <- fFriends;
    friend <- friends;
    notification = FavouriteNotification(friend, favourite)
  }{
   notification.send()
   }
 }

}
