/*
 * Copyright 2017 Max Meldrum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package agent

import com.typesafe.scalalogging.LazyLogging
import zookeeper.{Agent, ZkClient, ZkSetup}

import scala.util.{Failure, Success}

/** Main Starting Point of Program
  *
  * Starts the agent and joins the cluster,
  * by creating a session to ZooKeeper
  */
object Main extends App with LazyLogging {
  implicit val zk = ZkClient.zkCuratorFrameWork

  def zookeeperSetup(): Unit = {
    ZkClient.connect() match {
      case true => {
        val agent = Agent("test")
        ZkSetup.run()
        ZkClient.joinCluster(agent) match {
          case Success(_) => {
            logger.info("ZooKeeper session is now active")
            ZkClient.registerAgent(agent)
            while (true) {
              Thread.sleep(1000)
            }
          }
          case Failure(e) => logger.error("Error occurred, " + e.toString)
        }
      }
      case false => {
        logger.error("Failed to establish initial connection to ZooKeeper, shutting down")
      }
    }
    // Close CuratorFramework at end
    if (ZkClient.isConnected())
      zk.close()
  }

}
