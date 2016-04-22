package ai

import tyckiting._
import com.marmar.JAI
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

object MyScalaAi extends Tyckiting {
  
  def teamName = "MarMar"
  def ai = new JAI()
    
  def makeDecisions(
    roundId: Int,
    events: List[Event],
    bots: List[Bot],
    config: GameConfig) =
  {
    ai.makeDecisions(roundId, events.asJava, bots.asJava, config).toList
  }
  
  def init(config: GameConfig) {
    ai.init(config)
  }
}