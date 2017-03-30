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

package se.meldrum.spaceturtle.utils

import com.typesafe.scalalogging.LazyLogging
import org.apache.curator.framework.CuratorFramework


object ZkUtils extends LazyLogging {

  /** Check if Znode path exists
    *
    * @param path znode path
    * @return True if it exist, otherwise false
    */
  def pathExists(path: String)(implicit zkClient: CuratorFramework): Boolean = {
    val stat = Option(zkClient.checkExists().forPath(path))
    stat match {
      case None => false
      case Some(_) => true
    }
  }

  /** Creates znode if not exists
    *
    * @param path target path
    * @param zkClient Allows us to easily call this function with the TestingServer as well
    */
  def createPath(path: String)(implicit zkClient: CuratorFramework) : Unit = {
    pathExists(path) match {
      case true => logger.info("Path already exists: " + path)
      case false => zkClient.create().forPath(path)
    }
  }

  /** Deletes ZooKeeper znode
    *
    * @param path target path
    * @param zkClient Allows us to easily call this function with the TestingServer as well
    */
  def deleteZNode(path: String)(implicit zkClient: CuratorFramework): Unit = {
    pathExists(path) match {
      case true => zkClient.delete().deletingChildrenIfNeeded().forPath(path)
      case false => logger.info("Tried deleting a non existing path: " + path)
    }
  }
}