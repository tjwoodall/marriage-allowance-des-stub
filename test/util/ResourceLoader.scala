package util

import scala.io.Source

trait ResourceLoader {

  def loadResource(path: String): String = {
    val resource = getClass.getResourceAsStream(path)
    try Source.fromInputStream(resource).mkString
    finally resource.close
  }
}

object ResourceLoader extends ResourceLoader
