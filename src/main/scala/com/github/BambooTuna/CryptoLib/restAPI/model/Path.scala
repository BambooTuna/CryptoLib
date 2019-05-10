package com.github.BambooTuna.CryptoLib.restAPI.model

case class Path(path: String) {

  def addQueryParameters(queryParameters: Map[String, String]): String = {

    var tmp = queryParameters
    val r = "/:([a-z]+)".r
    val replaced = r.findAllIn(path).matchData.foldLeft(path)((l, r) => {
      val key = r.group(1)
      tmp.get(key).filterNot(_ == "").map(value => {
        tmp = tmp - key
        l.replaceFirst(":" + key, value)
      }).getOrElse(l)
    })
    replaced + (if (tmp.nonEmpty) tmp.foldLeft("?")((l, r) => if (r._2 == "") l else l + s"&${r._1}=${r._2}").replaceFirst("&", "") else "")

  }

}
