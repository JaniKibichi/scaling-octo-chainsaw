package controllers

import play.api.mvc.{Controller, Action}

object FavouriteStudios extends Controller{
 private def clearCaches(userId: Int, studioId: Int) =
  List(
      "find_" + userId + "_" + studioId,
      "findAll_" + userId
  ).map { key => 
    Cache.remove(key)
  }


 def add(userId: Int, studioId: Int) = Action {
   var favourite = FavouriteStudio.addFavourite(userId, studioId)

   clearCaches(userId, studioId)

   Ok(Json.obj("result" -> favourite))
 }

 def remove(userId: Int, studioId: Int) = Action {
   FavouriteStudio.delete(userId, studioId)

   clearCaches(userId, studioId)

   Ok(Json.obj("result" -> Json.obj()))
 }

 def find(userId: Int, studioId: Int) = Cached("find_" + userId + "_" + studioId){
  Action {
   var theFavourite = FavouriteStudio.find(userId, studioId)

   theFavourite match{
    case None =>NotFound(Json.obj("error" -> 'NOT FOUND'))
    case Some(favourite) =>Ok(Json.obj("result" -> favourite))
   }
  }  
 }

 def find(userId: Int, studioId: Int) =Cached("findAll_" + userId){
  Action {
   var allFavourites = FavouriteStudio.findAll(userId)

   Ok(Json.obj("result" -> allFavourites))
   }  
  }
 }
