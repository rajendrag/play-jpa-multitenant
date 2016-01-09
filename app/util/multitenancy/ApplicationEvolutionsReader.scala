package util.multitenancy

import java.io.FileInputStream

import com.google.inject.{Singleton, Inject}
import play.api.Environment
import play.api.db.evolutions.{Evolutions, ResourceEvolutionsReader}

/**
  * Reads the evolution scripts from the files
  * Created by RP on 1/8/16.
  */
@Singleton
class ApplicationEvolutionsReader (environment: Environment) extends ResourceEvolutionsReader {
  val resource = "application"
  def loadResource(db: String, revision: Int) = {
    environment.getExistingFile(Evolutions.fileName(resource, revision)).map(new FileInputStream(_)).orElse {
      environment.resourceAsStream(Evolutions.resourceName(resource, revision))
    }
  }
}
