package config

import com.google.inject.{Inject, Singleton}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

import scala.concurrent.ExecutionContext

/**
  * Created by caeus on 24/06/17.
  */

class VeontestModule extends Module {
  override def bindings(environment: Environment,
                        configuration: Configuration): Seq[Binding[_]] = {
    Seq(bind[Init].toSelf.eagerly())
  }
}

@Singleton
class Init @Inject()()(implicit executionContext: ExecutionContext) {

}
