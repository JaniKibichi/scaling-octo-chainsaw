object FavouriteStudioDAO {
 def create(favourite: FavouriteStudio) =
  DB.withConnection { implicit c =>
   SQL(
    """
      | INSERT IGNORE INTO `favouriteStudio` (`userId`, `studioId`)
      | VALUES
      | ({userId}, {studioId};
    """.stripMargin).on(
      "userId" -> favourite.userId,
      "studioId" -> favourtite.studioId
     ).executeInsert()
 }

 def delete(favourite: FavouriteStudio) =
  DB.withConnection { implicit c =>
   SQL(
    """
      | DELETE FROM `favouriteStudio` 
      | WHERE `userId` = {userId} AND `studioId` = {studioId}
      | LIMIT 1;
    """.stripMargin).on(
      "userId" -> favourite.userId,
      "studioId" -> favourtite.studioId
     ).executeUpdate()
   }
 }

 def exists(favourite: FavouriteStudio): Boolean = {
  DB.withConnection { implicit c =>
   val result = SQL(
    """
      | SELECT COUNT(*) as numMatches 
      | FROM `favouriteStudio` (`userId`, `studioId`)
      | WHERE `userId` = {userId} AND `studioId` = {studioId};
    """.stripMargin).on(
      "userId" -> favourite.userId,
      "studioId" -> favourtite.studioId
     ).apply().head
    result[Int]("numMatches") != 0
   }
 }


 def index(userId: Int): List[FavouriteStudio] = {
  DB.withConnection { implicit c =>
   val results = SQL(
    """
      | SELECT `userId`, `studioId` 
      | FROM `favouriteStudio`
      | WHERE `userId` = {userId} AND `studioId` = {studioId};
    """.stripMargin).on(
      "userId" -> favourite.userId
     ).apply()
    results.map { row =>
      FavouriteStudio(row[Int]("userId"),(row[Int]("studioId"))
    }.force.toList
   }
 }
}

