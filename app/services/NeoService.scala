package services

import play.api.libs.json.{JsValue, JsArray, Json, JsObject}

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws.{Response, WS}

import ExecutionContext.Implicits.global

class NeoService(rootUrl: String) {
  def this() = this("http://localhost:7474/db/data")

  val stdHeaders = Seq( ("Accept", "application/json"), ("Content-Type", "application/json") )

  def executeCypher(query: String, params: JsObject): Future[Response] = {
    println(s"${rootUrl}/cypher")
    WS.url(rootUrl + "/cypher").withHeaders(stdHeaders:_*).post(Json.obj(
      "query" -> query,
      "params" -> params
    ))
  }

  def findNodeIdByKindAndName(kind: String, name: String): Future[Option[Int]] = {
    val cypher =
      """
        START n = node:node_auto_index(kind={theKind})
        WHERE n.name = {theName}
        RETURN id(n) as is
      """.stripMargin
    val params = Json.obj( "theName" -> name, "theKind" -> kind)
    for (r <- executeCypher(cypher, params)) yield {
      val theData = (r.json \ "data").as[JsArray]
      if (theData.value.size == 0)
        None
      else
        Some(theData.value(0).as[JsArray].value(0).as[Int])
    }
  }

  def executeCypher(query: JsValue) = {
    WS.url(rootUrl + "/cypher").withHeaders(stdHeaders:_*).post(Json.obj("query" -> query))
  }
  def createNode(node: JsValue) = {
    val url = rootUrl + "/node"
    WS.url(url).withHeaders(stdHeaders:_*).post(node)
  }

}
